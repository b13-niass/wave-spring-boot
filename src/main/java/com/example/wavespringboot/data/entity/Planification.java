package com.example.wavespringboot.data.entity;

import com.example.wavespringboot.enums.ChannelEnum;
import com.example.wavespringboot.enums.RecurrenceType;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
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

    @Column(name = "montant", nullable = false)
    private double montant;

    @Enumerated(EnumType.STRING)
    @Column(name = "recurrence_type", length = 10)
    private RecurrenceType recurrenceType;

    @Column(name = "time_of_day")
    private Time timeOfDay;

    @Column(name = "days_of_week", length = 20)
    private String daysOfWeek;

    @Column(name = "day_of_month")
    private Integer dayOfMonth;
    @ManyToOne

    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;
}

