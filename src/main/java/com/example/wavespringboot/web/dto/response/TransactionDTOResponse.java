package com.example.wavespringboot.web.dto.response;

import com.example.wavespringboot.enums.EtatTransactionEnum;
import com.example.wavespringboot.enums.TypeTransactionEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class TransactionDTOResponse {
    private Long id;
    private double montantEnvoye;
    private double montantRecus;
    private EtatTransactionEnum etatTransaction;
    private TypeTransactionEnum typeTransaction;
    private String senderName;
    private String receiverName;
    private Long senderId;
    private Long receiverId;
    private Double fraisValeur;  // Nullable if `frais` might not be present
}
