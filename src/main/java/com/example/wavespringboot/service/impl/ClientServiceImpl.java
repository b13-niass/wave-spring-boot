package com.example.wavespringboot.service.impl;

import com.example.wavespringboot.data.entity.*;
import com.example.wavespringboot.data.repository.*;
import com.example.wavespringboot.enums.EtatTransactionEnum;
import com.example.wavespringboot.enums.TypeTransactionEnum;
import com.example.wavespringboot.exception.client.ClientInternalServerException;
import com.example.wavespringboot.exception.client.ClientNotFoundException;
import com.example.wavespringboot.exception.client.ClientSoldeInsuffisantException;
import com.example.wavespringboot.service.AuthService;
import com.example.wavespringboot.service.ClientService;
import com.example.wavespringboot.service.QRCodeGenerator;
import com.example.wavespringboot.web.dto.request.*;
import com.example.wavespringboot.web.dto.request.mapper.TransfertDTOMapper;
import com.example.wavespringboot.web.dto.response.*;
import com.example.wavespringboot.web.dto.response.mapper.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@RequiredArgsConstructor
@Service
public class ClientServiceImpl implements ClientService {
    private final UserRepository userRepository;
    private final AuthService authService;
    private final UserResponseMapper userResponseMapper;
    private final WalletResponseMapper walletResponseMapper;
    private final FavorisResponseMapper favorisResponseMapper;
    private final TransactionResponseMapper transactionResponseMapper;
    private final QRCodeGenerator qrCodeGenerator;
    private final TransfertDTOMapper transfertDTOMapper;
    private final TransactionRepository transactionRepository;
    private final FraisRepository fraisRepository;
    private final FavorisRepository favorisRepository;
    private final WalletRepository walletRepository;
    private final PlanificationRepository planificationRepository;
    private final PlanificationResponseMapper planificationResponseMapper;


    @Override
    public AccueilDTOResponse accueil() {
       User user = authService.getAuthenticatedUser();
        UserDTOResponse userDTOResponse = userResponseMapper.toDTO(user);
        userDTOResponse.setTransactions(
                Stream.concat(
                        transactionResponseMapper.toDTOList(user.getSentTransactions()).stream(),
                        transactionResponseMapper.toDTOList(user.getReceivedTransactions()).stream()
                        ).sorted((t1, t2) -> t2.getCreatedAt().compareTo(t1.getCreatedAt()))  // Compare in reverse order
                        .collect(Collectors.toList())
        );
        String qrCodeBase64 = this.qrCodeGenerator.getQRCodeImageBase64(user.getTelephone(), 250, 250);
        return AccueilDTOResponse.builder()
                .user(userDTOResponse)
                .qrCode(qrCodeBase64)
                .build();
    }

    @Override
    public Collection<FavorisDTOResponse> getFavoris() {
        User user = authService.getAuthenticatedUser();
        return favorisResponseMapper.toDTOList(user.getFavoris());
    }

    @Override
    public FavorisDTOResponse addFavoris(FavorisDTORequest favorisR) {
        User user = authService.getAuthenticatedUser();

        Favoris favoris = Favoris.builder()
                .nom(favorisR.nom())
                .prenom(favorisR.prenom())
                .telephone(favorisR.telephone())
                .user(user).build();

        return favorisResponseMapper.toDTO(favorisRepository.save(favoris));
    }

    @Transactional
    @Override
    public Collection<TransactionDTOResponse> transfert(MultipleTransfertDTORequest transferts) {
        User sender = authService.getAuthenticatedUser();
        Frais frais = fraisRepository.findById(1L).orElseThrow(() -> new ClientNotFoundException("Frais introuvable"));

        if (transferts.transferts().size() == 1) {
            try {
                Transaction transaction = transfertDTOMapper.toEntity(transferts.transferts().getFirst());
                transaction.setSender(sender);
                transaction.setFrais(frais);
                transaction.setEtatTransaction(EtatTransactionEnum.EFFECTUER);
                transaction.setTypeTransaction(TypeTransactionEnum.TRANSFERT);

                if (sender.getWallet().getSolde() < transaction.getMontantEnvoye()) {
                    throw new ClientSoldeInsuffisantException("Solde insuffisant");
                }

                User receiver = userRepository.findByTelephone(transaction.getReceiver().getTelephone())
                        .orElseThrow(() -> new ClientNotFoundException("Destinataire introuvable"));
                transaction.setReceiver(receiver);

                sender.getWallet().setSolde(sender.getWallet().getSolde() - transaction.getMontantEnvoye());
                receiver.getWallet().setSolde(receiver.getWallet().getSolde() + transaction.getMontantRecus());

                walletRepository.save(sender.getWallet());
                walletRepository.save(receiver.getWallet());

                transaction = transactionRepository.save(transaction);

                return Collections.singletonList(transactionResponseMapper.toDTO(transaction));

            } catch (ClientSoldeInsuffisantException | ClientNotFoundException e) {
                throw e;
            } catch (Exception e) {
                throw new RuntimeException("Une erreur inattendue s'est produite", e);
            }

        } else if (transferts.transferts().size() > 1) {
            try {
                List<Transaction> transactions = transferts.transferts().stream()
                        .map(transfertDTOMapper::toEntity)
                        .peek(t -> {
                            t.setSender(sender);
                            t.setFrais(frais);
                            t.setEtatTransaction(EtatTransactionEnum.EFFECTUER);
                            t.setTypeTransaction(TypeTransactionEnum.TRANSFERT);
                        })
                        .collect(Collectors.toList());

                double totalMontant = transactions.stream()
                        .mapToDouble(Transaction::getMontantEnvoye)
                        .sum();

                if (sender.getWallet().getSolde() < totalMontant) {
                    throw new ClientSoldeInsuffisantException("Solde insuffisant");
                }

                for (Transaction transaction : transactions) {
                    // Fetch the receiver from the database
                    User receiver = userRepository.findByTelephone(transaction.getReceiver().getTelephone())
                            .orElseThrow(() -> new ClientNotFoundException("Destinataire introuvable"));

                    // Set the fully managed receiver instance to the transaction
                    transaction.setReceiver(receiver);

                    // Update sender and receiver wallet balances
                    sender.getWallet().setSolde(sender.getWallet().getSolde() - transaction.getMontantEnvoye());
                    receiver.getWallet().setSolde(receiver.getWallet().getSolde() + transaction.getMontantRecus());

                    walletRepository.save(receiver.getWallet());  // Save the updated receiver wallet
                }

                // Save all transactions at once
                transactions = transactionRepository.saveAll(transactions);
                walletRepository.save(sender.getWallet());  // Save the updated sender wallet

                return transactionResponseMapper.toDTOList(transactions);

            } catch (ClientSoldeInsuffisantException | ClientNotFoundException e) {
                throw e;
            } catch (Exception e) {
                throw new ClientInternalServerException("Erreur lors du transfert");
            }

        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public TransactionDTOResponse paiement(TransfertDTORequest transfert) {
        User sender = authService.getAuthenticatedUser();
        Frais frais = fraisRepository.findById(1L).orElseThrow(() -> new ClientNotFoundException("Frais introuvable"));

        try {
            Transaction transaction = transfertDTOMapper.toEntity(transfert);
            transaction.setSender(sender);
            transaction.setFrais(frais);
            transaction.setEtatTransaction(EtatTransactionEnum.EFFECTUER);
            transaction.setTypeTransaction(TypeTransactionEnum.PAIEMENT);

            if (sender.getWallet().getSolde() < transaction.getMontantEnvoye()) {
                throw new ClientSoldeInsuffisantException("Solde insuffisant");
            }

            User receiver = userRepository.findById(transaction.getReceiver().getId())
                    .orElseThrow(() -> new ClientNotFoundException("Destinataire introuvable"));

            sender.getWallet().setSolde(sender.getWallet().getSolde() - transaction.getMontantEnvoye());

            receiver.getWallet().setSolde(receiver.getWallet().getSolde() + transaction.getMontantRecus());

            transaction = transactionRepository.save(transaction);
            System.out.println("Transfert simple");

            walletRepository.save(sender.getWallet());
            walletRepository.save(receiver.getWallet());

            return transactionResponseMapper.toDTO(transaction);

        } catch (ClientSoldeInsuffisantException | ClientNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Une erreur inattendue s'est produite", e);
        }
    }

    @Transactional
    @Override
    public TransactionDTOResponse annulerTransaction(AnnulerTransDTORequest annulerTransDTORequest) {
        User user = authService.getAuthenticatedUser();

        Transaction transaction = transactionRepository.findById(annulerTransDTORequest.idTransaction())
                .orElseThrow(() -> new ClientNotFoundException("Transaction introuvable"));

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime transactionTime = transaction.getCreatedAt();
        long minutesSinceCreation = java.time.Duration.between(transactionTime, now).toMinutes();

        if (minutesSinceCreation > 30) {
            throw new IllegalStateException("La transaction ne peut pas être annulée car elle a été créée il y a plus de 30 minutes.");
        }

        if (transaction.getEtatTransaction() != EtatTransactionEnum.EFFECTUER) {
            throw new IllegalStateException("Seules les transactions effectuées peuvent être annulées.");
        }

        transaction.setEtatTransaction(EtatTransactionEnum.ANNULER);

        User sender = transaction.getSender();
        User receiver = transaction.getReceiver();

        if (sender.getWallet() == null || receiver.getWallet() == null) {
            throw new IllegalStateException("Les portefeuilles des utilisateurs doivent exister pour annuler la transaction.");
        }

        receiver.getWallet().setSolde(receiver.getWallet().getSolde() - transaction.getMontantRecus());
        sender.getWallet().setSolde(sender.getWallet().getSolde() + transaction.getMontantEnvoye());

        walletRepository.save(receiver.getWallet());
        walletRepository.save(sender.getWallet());

        Transaction updatedTransaction = transactionRepository.save(transaction);

        return transactionResponseMapper.toDTO(updatedTransaction);
    }

    @Override
    public PlanificationDTOResponse planification(PlanificationDTORequest planificationDTORequest) {
        User user = authService.getAuthenticatedUser();

        User receiver = userRepository.findByTelephone(planificationDTORequest.telephone()).orElseThrow(() -> new ClientNotFoundException("Destinataire introuvable"));
        Planification planification = Planification.builder()
                .recurrenceType(planificationDTORequest.recurrenceType())
                .montant(planificationDTORequest.montant())
                .dayOfMonth(planificationDTORequest.dayOfMonth())
                .daysOfWeek(planificationDTORequest.daysOfWeek())
                .sender(user)
                .receiver(receiver)
                .build();
        if (planificationDTORequest.timeOfDay() != null) {
            System.out.println(planificationDTORequest.timeOfDay());
            try {
                String timeOfDay = planificationDTORequest.timeOfDay();
                if (timeOfDay.length() == 5) { // Format "HH:mm"
                    timeOfDay += ":00"; // Pad with ":00" to match "HH:mm:ss"
                }
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                Date date = sdf.parse(timeOfDay);
                Time time = new Time(date.getTime());
                planification.setTimeOfDay(time);
            } catch (ParseException e) {
                throw new RuntimeException("Format de la date et de l'heure incorrect", e);
            }
        }
        planification = planificationRepository.save(planification);

        return planificationResponseMapper.toDTO(planification);
    }

    @Override
    public boolean annulerPlanification(AnnulerPlanifDTORequest annulerPlanifDTORequest) {
        Planification planification = planificationRepository.findById(annulerPlanifDTORequest.id()).orElseThrow(
                () -> new ClientNotFoundException("Planification introuvable")
        );
        planificationRepository.delete(planification);
        return true;
    }

    @Override
    public List<PlanificationDTOResponse> getPlanifications() {
        User user = authService.getAuthenticatedUser();
        List<Planification> planifications = planificationRepository.findBySenderId(user.getId());
       return planificationResponseMapper.toDTOList(planifications);
    }


}
