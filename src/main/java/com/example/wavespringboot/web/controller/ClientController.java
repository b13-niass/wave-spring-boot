package com.example.wavespringboot.web.controller;

import com.example.wavespringboot.web.dto.request.*;
import com.example.wavespringboot.web.dto.response.*;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.List;

public interface ClientController {
    ResponseEntity<AccueilDTOResponse> accueil();
    ResponseEntity<Collection<FavorisDTOResponse>> getFavoris();
    ResponseEntity<FavorisDTOResponse> addFavoris(FavorisDTORequest favorisDTORequest);
    ResponseEntity<Collection<TransactionDTOResponse>> transfert(MultipleTransfertDTORequest transferts);
    ResponseEntity<TransactionDTOResponse> paiement(TransfertDTORequest transfert);
    ResponseEntity<TransactionDTOResponse> annulerTransaction(AnnulerTransDTORequest annulerTransDTORequest);
    ResponseEntity<PlanificationDTOResponse> planification(PlanificationDTORequest planificationDTORequest);
    ResponseEntity<Boolean> annulerPlanification(AnnulerPlanifDTORequest annulerPlanifDTORequest);
    ResponseEntity<List<PlanificationDTOResponse>> getPlanifications();
}
