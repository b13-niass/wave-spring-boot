package com.example.wavespringboot.data.entity;

import com.example.wavespringboot.enums.ChannelEnum;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "planifications")
public class Planification extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double montantEnvoye;
    private double montantRecus;
    private String periode;

    @Enumerated(EnumType.STRING)
    private ChannelEnum channel;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;
}

