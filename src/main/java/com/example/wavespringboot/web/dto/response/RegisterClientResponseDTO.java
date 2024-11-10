package com.example.wavespringboot.web.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder(toBuilder = true)
@Data
public class RegisterClientResponseDTO {
    private String telephone;
    private String email;
    private String nom;
    private String prenom;
}
