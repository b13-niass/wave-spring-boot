package com.example.wavespringboot.service.impl;

import com.example.wavespringboot.data.entity.Planification;
import com.example.wavespringboot.data.entity.Transaction;
import com.example.wavespringboot.data.entity.User;
import com.example.wavespringboot.data.entity.Wallet;
import com.example.wavespringboot.data.repository.PlanificationRepository;
import com.example.wavespringboot.data.repository.TransactionRepository;
import com.example.wavespringboot.data.repository.WalletRepository;
import com.example.wavespringboot.enums.EtatTransactionEnum;
import com.example.wavespringboot.enums.TypeTransactionEnum;
import com.example.wavespringboot.service.PlanificationJob;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@EnableScheduling
@Service
@RequiredArgsConstructor
public class  PlanificationJobImpl implements PlanificationJob {

    private final PlanificationRepository planificationRepository;
    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;


    public List<Planification> findPlanificationsToExecute(LocalDateTime currentDateTime) {
        List<Planification> allPlanifications = planificationRepository.findAllRecurringPlanifications();

        return allPlanifications.stream()
                .filter(planification -> shouldExecutePlanification(planification, currentDateTime))
                .collect(Collectors.toList());
    }

    private boolean shouldExecutePlanification(Planification planification, LocalDateTime currentDateTime) {
        LocalTime currentTime = currentDateTime.toLocalTime();
        DayOfWeek currentDayOfWeek = currentDateTime.getDayOfWeek();
        int currentDayOfMonth = currentDateTime.getDayOfMonth();

        LocalTime localTime = planification.getTimeOfDay().toLocalTime();
        LocalDateTime localDateTime = LocalDate.now().atTime(localTime);

        switch (planification.getRecurrenceType()) {
            case DAILY:
                return currentTime.isAfter(planification.getTimeOfDay().toLocalTime());
            case WEEKLY:
                return currentDayOfWeek.equals(localDateTime.getDayOfWeek()) &&
                        currentTime.isAfter(planification.getTimeOfDay().toLocalTime());
            case MONTHLY:
                return currentDayOfMonth == planification.getDayOfMonth() &&
                        currentTime.isAfter(planification.getTimeOfDay().toLocalTime());
            default:
                return false;
        }
    }


//        @Scheduled(cron = "0 0 * * * *") // This cXron runs every hour
//    @Scheduled(cron = "0 */2 * * * *")
    @Transactional
    public void processPlanifications() {
        LocalDateTime now = LocalDateTime.now();

        // Get all planifications that should execute at this time
        List<Planification> planifications = this.findPlanificationsToExecute(now);

        for (Planification planification : planifications) {
            User sender = planification.getSender();
            User receiver = planification.getReceiver();
            double amount = planification.getMontant();

            Wallet senderWallet = sender.getWallet();
            Wallet receiverWallet = receiver.getWallet();

            // Check if sender has enough balance
            if (senderWallet.getSolde() >= amount) {
                // Deduct from sender and add to receiver
                senderWallet.setSolde(senderWallet.getSolde() - amount);
                receiverWallet.setSolde(receiverWallet.getSolde() + amount);

                // Save the updated wallets
                walletRepository.save(senderWallet);
                walletRepository.save(receiverWallet);

                // Create a new transaction
                Transaction transaction = Transaction.builder()
                        .montantEnvoye(amount)
                        .montantRecus(amount) // Assuming same for simplicity
                        .etatTransaction(EtatTransactionEnum.EFFECTUER)
                        .typeTransaction(TypeTransactionEnum.TRANSFERT)
                        .sender(sender)
                        .receiver(receiver)
                        .build();

                // Save the transaction
                transactionRepository.save(transaction);
                planificationRepository.delete(planification);
            }
        }
    }

}
