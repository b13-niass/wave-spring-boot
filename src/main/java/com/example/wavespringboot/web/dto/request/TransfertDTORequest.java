package com.example.wavespringboot.web.dto.request;

import com.example.wavespringboot.validator.annotation.IfExistUserAnnotation;

import com.example.wavespringboot.validator.annotation.ValidMontantAnnotation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@ValidMontantAnnotation
public record TransfertDTORequest(
        @Positive(message = "Receiver obligatoire")
        @IfExistUserAnnotation
        Long receiverId,
        @Positive(message = "Nom obligatoire")
        Double montantEnvoye,
        @Positive(message = "Nom obligatoire")
        Double montantRecus
) {
}
