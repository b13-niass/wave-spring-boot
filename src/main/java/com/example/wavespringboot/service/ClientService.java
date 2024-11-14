package com.example.wavespringboot.service;

import com.example.wavespringboot.web.dto.request.*;
import com.example.wavespringboot.web.dto.response.*;

import java.text.ParseException;
import java.util.Collection;
import java.util.List;

public interface ClientService {
    AccueilDTOResponse accueil();
    Collection<FavorisDTOResponse> getFavoris();
    FavorisDTOResponse addFavoris(FavorisDTORequest favorisDTORequest);
    Collection<TransactionDTOResponse> transfert(MultipleTransfertDTORequest transferts);
    TransactionDTOResponse paiement(TransfertDTORequest transfert);
    TransactionDTOResponse annulerTransaction(AnnulerTransDTORequest annulerTransDTORequest);
    PlanificationDTOResponse planification(PlanificationDTORequest planificationDTORequest);
    boolean annulerPlanification(AnnulerPlanifDTORequest annulerPlanifDTORequest);
    List<PlanificationDTOResponse> getPlanifications();
}
