package com.example.wavespringboot.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder(toBuilder = true)
public record LoginUserDTORequest(
         @NotBlank(message = "Telephone est obligatoire")
         String telephone,
         @NotBlank(message = "Mot de passe est obligatoire")
         String password
) {
}
