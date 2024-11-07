package com.example.wavespringboot.web.dto.request;

import com.example.wavespringboot.validator.annotation.IfExistUserAnnotation;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder(toBuilder = true)
public record AnnulerTransDTORequest(
       @IfExistUserAnnotation
       @Positive(message = "L'id doit etre un nombre positif")
        Long idTransaction
) {
}
