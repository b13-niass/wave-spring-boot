package com.example.wavespringboot.web.dto.response;

import com.example.wavespringboot.enums.EtatCompteEnum;
import com.example.wavespringboot.enums.RoleEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class UserDTOResponse {
    private Long id;
    private String nom;
    private String prenom;
    private String adresse;
    private String telephone;
    private String email;
    private EtatCompteEnum etatCompte;
    private RoleEnum role;
    private String paysLibelle;
    private WalletDTOResponse wallet;
    private int nbrConnection;
}
