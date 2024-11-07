package com.example.wavespringboot.web.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class WalletDTOResponse {
    private Long id;
    private double solde;
    private double plafond;
    private Long userId;  // User ID to associate the wallet with a user
    private String userNom;  // User's name for reference
}
