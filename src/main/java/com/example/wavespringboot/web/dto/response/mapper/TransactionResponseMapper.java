package com.example.wavespringboot.web.dto.response.mapper;

import com.example.wavespringboot.data.entity.Transaction;
import com.example.wavespringboot.web.dto.response.TransactionDTOResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionResponseMapper {

    @Mapping(source = "sender.nom", target = "senderName")      // Assumes `User` has a `name` field
    @Mapping(source = "receiver.nom", target = "receiverName")  // Assumes `User` has a `name` field
    @Mapping(source = "frais.valeur", target = "fraisValeur")  // Assumes `Frais` has a `montant` field
    TransactionDTOResponse toDTO(Transaction transaction);

    List<TransactionDTOResponse> toDTOList(List<Transaction> transactions);
}

