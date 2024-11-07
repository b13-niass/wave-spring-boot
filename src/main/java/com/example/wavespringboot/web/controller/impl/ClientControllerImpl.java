package com.example.wavespringboot.web.controller.impl;

import com.example.wavespringboot.service.ClientService;
import com.example.wavespringboot.web.controller.ClientController;
import com.example.wavespringboot.web.dto.request.AnnulerTransDTORequest;
import com.example.wavespringboot.web.dto.request.FavorisDTORequest;
import com.example.wavespringboot.web.dto.request.MultipleTransfertDTORequest;
import com.example.wavespringboot.web.dto.request.TransfertDTORequest;
import com.example.wavespringboot.web.dto.response.FavorisDTOResponse;
import com.example.wavespringboot.web.dto.response.TransactionDTOResponse;
import com.example.wavespringboot.web.dto.response.UserDTOResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RequiredArgsConstructor
@RequestMapping("/client")
@RestController
public class ClientControllerImpl implements ClientController {
    private final ClientService clientService;

    @GetMapping("/accueil")
    @Override
    public ResponseEntity<UserDTOResponse> accueil() {
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

}
