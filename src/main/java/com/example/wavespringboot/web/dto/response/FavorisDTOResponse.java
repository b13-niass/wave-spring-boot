package com.example.wavespringboot.web.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FavorisDTOResponse {
    private Long id;
    private String nom;
    private String prenom;
    private String telephone;
    private Long userId; // Assuming you just want the user's ID, not the full user details
}
