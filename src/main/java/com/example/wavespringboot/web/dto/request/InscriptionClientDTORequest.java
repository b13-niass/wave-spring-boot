package com.example.wavespringboot.web.dto.request;

import com.example.wavespringboot.validator.annotation.EmailValidUniqueAnnotation;
import com.example.wavespringboot.validator.annotation.TelValidUniqueAnnotation;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder(toBuilder = true)
public record InscriptionClientDTORequest(
        @EmailValidUniqueAnnotation
        @NotBlank(message = "l'email est obligatoire")
        String email,
        @TelValidUniqueAnnotation
        @NotBlank(message = "le telephone est obligatoire")
        String telephone,
        @NotBlank(message = "le mot de passe est obligatoire")
        String password
) {
}
