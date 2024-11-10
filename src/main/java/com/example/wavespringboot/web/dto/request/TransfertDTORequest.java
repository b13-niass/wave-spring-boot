package com.example.wavespringboot.web.dto.request;

import com.example.wavespringboot.validator.annotation.IfExistUserAnnotation;

import com.example.wavespringboot.validator.annotation.ValidMontantAnnotation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder(toBuilder = true)
@ValidMontantAnnotation
public record TransfertDTORequest(
        @IfExistUserAnnotation
        String telephone,
        @Positive(message = "Nom obligatoire")
        Double montantEnvoye,
        @Positive(message = "Nom obligatoire")
        Double montantRecus
) {
}
