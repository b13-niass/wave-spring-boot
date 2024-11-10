package com.example.wavespringboot.service;

import com.example.wavespringboot.web.dto.request.AnnulerTransDTORequest;
import com.example.wavespringboot.web.dto.request.FavorisDTORequest;
import com.example.wavespringboot.web.dto.request.MultipleTransfertDTORequest;
import com.example.wavespringboot.web.dto.request.TransfertDTORequest;
import com.example.wavespringboot.web.dto.response.AccueilDTOResponse;
import com.example.wavespringboot.web.dto.response.FavorisDTOResponse;
import com.example.wavespringboot.web.dto.response.TransactionDTOResponse;
import com.example.wavespringboot.web.dto.response.UserDTOResponse;

import java.util.Collection;
import java.util.List;

public interface ClientService {
    AccueilDTOResponse accueil();
    Collection<FavorisDTOResponse> getFavoris();
    FavorisDTOResponse addFavoris(FavorisDTORequest favorisDTORequest);
    Collection<TransactionDTOResponse> transfert(MultipleTransfertDTORequest transferts);
    TransactionDTOResponse paiement(TransfertDTORequest transfert);
    TransactionDTOResponse annulerTransaction(AnnulerTransDTORequest annulerTransDTORequest);
}
