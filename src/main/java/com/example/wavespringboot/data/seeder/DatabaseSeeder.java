package com.example.wavespringboot.data.seeder;

import com.example.wavespringboot.data.entity.*;
import com.example.wavespringboot.enums.*;
import com.example.wavespringboot.data.repository.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Component
public class DatabaseSeeder {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PaysRepository paysRepository;
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private FavorisRepository favorisRepository;
    @Autowired
    private FraisRepository fraisRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private PlanificationRepository planificationRepository;

    @Value("${seeder.enabled}")
    private boolean seederEnabled;

    @PostConstruct
    public void seedDatabase() {
        if (seederEnabled) {
            seedPays();
            seedUsersAndWallets();
            seedFavoris();
            seedFrais();
            seedTransactions();
            seedPlanifications();
        }
    }

    private void seedPays() {
        Pays pays = new Pays();
        pays.setLibelle("Senegal");
        pays.setIndicatif("+221");
        paysRepository.save(pays);
    }

    private void seedUsersAndWallets() {
        // Création de 2 utilisateurs
        Pays pays = paysRepository.findByLibelle("Senegal");

        User user1 = User.builder()
                .nom("Diallo")
                .prenom("Mamadou")
                .telephone("+221771234567")
                .email("mamadou@example.com")
                .password("$2b$10$MQp7u4V1QTsdxptGYASRUuN3ZV11BzBXbUR9.sIxFkltAT9Xzk55q")
                .role(RoleEnum.CLIENT)
                .etatCompte(EtatCompteEnum.ACTIF)
                .dateNaissance(LocalDate.of(1990, 1, 1))
                .pays(pays)
                .channel(ChannelEnum.SMS)
                .build();
        userRepository.save(user1);

        User user2 = User.builder()
                .nom("Diop")
                .prenom("Fatou")
                .telephone("+221781234567")
                .email("fatou@example.com")
                .password("$2b$10$MQp7u4V1QTsdxptGYASRUuN3ZV11BzBXbUR9.sIxFkltAT9Xzk55q")
                .role(RoleEnum.CLIENT)
                .etatCompte(EtatCompteEnum.ACTIF)
                .dateNaissance(LocalDate.of(1995, 5, 15))
                .pays(pays)
                .build();
        userRepository.save(user2);

        // Création de Wallets associés aux utilisateurs
        Wallet wallet1 = Wallet.builder()
                .user(user1)
                .solde(1000.00)
                .plafond(5000.00)
                .build();
        walletRepository.save(wallet1);

        Wallet wallet2 = Wallet.builder()
                .user(user2)
                .solde(2000.00)
                .plafond(6000.00)
                .build();
        walletRepository.save(wallet2);
    }

    private void seedFavoris() {
        Optional<User> user = userRepository.findByTelephone("+221771234567");

        Favoris favoris = Favoris.builder()
                .nom("Ndour")
                .prenom("Cheikh")
                .telephone("+221771112233")
                .user(user.get())
                .build();
        favorisRepository.save(favoris);
    }

    private void seedFrais() {
        Frais frais = Frais.builder()
                .valeur(0.01) // 1% de frais
                .build();
        fraisRepository.save(frais);
    }

    private void seedTransactions() {
        Optional<User> sender = userRepository.findByTelephone("+221771234567");
        Optional<User> receiver = userRepository.findByTelephone("+221781234567");
        Frais frais = fraisRepository.findAll().get(0); // Récupère le premier frais

        Transaction transaction = Transaction.builder()
                .montantEnvoye(500)
                .montantRecus(495)
                .etatTransaction(EtatTransactionEnum.EFFECTUER)
                .typeTransaction(TypeTransactionEnum.TRANSFERT)
                .sender(sender.get())
                .receiver(receiver.get())
                .frais(frais)
                .build();
        transactionRepository.save(transaction);
    }

    private void seedPlanifications() {
        Optional<User> sender = userRepository.findByTelephone("+221771234567");
        Optional<User> receiver = userRepository.findByTelephone("+221781234567");

        LocalDate today = LocalDate.now();
        LocalDate plannedDate = today.plusDays(30); // Example: Planned for 30 days from today
        long periodInSeconds = ChronoUnit.SECONDS.between(today.atStartOfDay(), plannedDate.atStartOfDay());

        Planification planification = Planification.builder()
                .montantEnvoye(1000)
                .montantRecus(990)
                .periode(String.valueOf(periodInSeconds))
                .sender(sender.get())
                .receiver(receiver.get())
                .build();
        planificationRepository.save(planification);
    }
}
