package com.example.wavespringboot.web.dto.response;


import com.example.wavespringboot.enums.RecurrenceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlanificationDTOResponse {
    private Long id;
    private double montant;
    private RecurrenceType recurrenceType;
    private Time timeOfDay;
    private String daysOfWeek;
    private Integer dayOfMonth;
    private Long senderId;
    private Long receiverId;
    private String senderName;
    private String receiverName;
    private String receiverTelephone;
    private String senderTelephone;
}