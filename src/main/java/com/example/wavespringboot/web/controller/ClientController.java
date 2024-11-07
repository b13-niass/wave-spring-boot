package com.example.wavespringboot.web.controller;

import com.example.wavespringboot.web.dto.request.AnnulerTransDTORequest;
import com.example.wavespringboot.web.dto.request.FavorisDTORequest;
import com.example.wavespringboot.web.dto.request.MultipleTransfertDTORequest;
import com.example.wavespringboot.web.dto.request.TransfertDTORequest;
import com.example.wavespringboot.web.dto.response.FavorisDTOResponse;
import com.example.wavespringboot.web.dto.response.TransactionDTOResponse;
import com.example.wavespringboot.web.dto.response.UserDTOResponse;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

public interface ClientController {
    ResponseEntity<UserDTOResponse> accueil();
    ResponseEntity<Collection<FavorisDTOResponse>> getFavoris();
    ResponseEntity<FavorisDTOResponse> addFavoris(FavorisDTORequest favorisDTORequest);
    ResponseEntity<Collection<TransactionDTOResponse>> transfert(MultipleTransfertDTORequest transferts);
    ResponseEntity<TransactionDTOResponse> paiement(TransfertDTORequest transfert);
    ResponseEntity<TransactionDTOResponse> annulerTransaction(AnnulerTransDTORequest annulerTransDTORequest);
}
