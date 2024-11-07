package com.example.wavespringboot.web.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlanificationDTOResponse {
    private Long id;
    private double montantEnvoye;
    private double montantRecus;
    private String periode;
    private Long senderId;  // Assuming only sender ID is needed
    private Long receiverId;  // Assuming only receiver ID is needed
}
