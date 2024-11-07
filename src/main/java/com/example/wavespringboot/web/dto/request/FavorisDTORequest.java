package com.example.wavespringboot.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder(toBuilder = true)
public record FavorisDTORequest(
        @NotBlank(message = "Nom obligatoire")
        String nom,
        @NotBlank(message = "Prenom obligatoire")
        String prenom,
        @NotBlank(message = "Telephone obligatoire")
        String telephone
) {
}
