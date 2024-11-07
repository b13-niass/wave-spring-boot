package com.example.wavespringboot.data.entity;

import com.example.wavespringboot.enums.EtatTransactionEnum;
import com.example.wavespringboot.enums.TypeTransactionEnum;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double montantEnvoye;
    private double montantRecus;

    @Enumerated(EnumType.STRING)
    private EtatTransactionEnum etatTransaction = EtatTransactionEnum.EFFECTUER;

    @Enumerated(EnumType.STRING)
    private TypeTransactionEnum typeTransaction = TypeTransactionEnum.TRANSFERT;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @ManyToOne
    @JoinColumn(name = "frais_id", nullable = true)
    private Frais frais;
}
