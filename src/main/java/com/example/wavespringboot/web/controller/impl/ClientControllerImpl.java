package com.example.wavespringboot.web.controller.impl;

import com.example.wavespringboot.service.ClientService;
import com.example.wavespringboot.web.controller.ClientController;
import com.example.wavespringboot.web.dto.request.*;
import com.example.wavespringboot.web.dto.response.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/client")
@RestController
public class ClientControllerImpl implements ClientController {
    private final ClientService clientService;

    @GetMapping("/accueil")
    @Override
    public ResponseEntity<AccueilDTOResponse> accueil() {
        return ResponseEntity.ok(clientService.accueil());
    }

    @GetMapping("/favoris")
    @Override
    public ResponseEntity<Collection<FavorisDTOResponse>> getFavoris() {
        return ResponseEntity.ok(clientService.getFavoris());
    }

    @PostMapping("/favoris")
    @Override
    public ResponseEntity<FavorisDTOResponse> addFavoris(@RequestBody @Valid FavorisDTORequest favorisDTORequest) {
        return ResponseEntity.ok(clientService.addFavoris(favorisDTORequest));
    }

    @PostMapping("/transferts")
    @Override
    public ResponseEntity<Collection<TransactionDTOResponse>> transfert(@RequestBody @Valid MultipleTransfertDTORequest transferts) {
        return ResponseEntity.ok(clientService.transfert(transferts))  ;
    }

    @PostMapping("paiement")
    @Override
    public ResponseEntity<TransactionDTOResponse> paiement(@RequestBody @Valid TransfertDTORequest transfert) {
        return ResponseEntity.ok(clientService.paiement(transfert));
    }

    @PostMapping("/transfert/annuler")
    @Override
    public ResponseEntity<TransactionDTOResponse> annulerTransaction(@RequestBody @Valid AnnulerTransDTORequest annulerTransDTORequest) {
        return ResponseEntity.ok(clientService.annulerTransaction(annulerTransDTORequest));
    }

    @PostMapping("/planification")
    @Override
    public ResponseEntity<PlanificationDTOResponse> planification(@RequestBody @Valid PlanificationDTORequest planificationDTORequest) {
        return ResponseEntity.ok(clientService.planification(planificationDTORequest));
    }

    @PostMapping("/planification/annuler")
    @Override
    public ResponseEntity<Boolean> annulerPlanification(@RequestBody @Valid AnnulerPlanifDTORequest annulerPlanifDTORequest) {
        return ResponseEntity.ok(clientService.annulerPlanification(annulerPlanifDTORequest));
    }

    @GetMapping("/planification")
    @Override
    public ResponseEntity<List<PlanificationDTOResponse>> getPlanifications() {
        return ResponseEntity.ok(clientService.getPlanifications());
    }
}
