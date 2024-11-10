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
    @Mapping(source = "sender.id", target = "senderId")
    @Mapping(source = "receiver.id", target = "receiverId")
    @Mapping(source = "frais.valeur", target = "fraisValeur")  // Assumes `Frais` has a `montant` field
    @Mapping(source = "createdAt", target = "createdAt")
    TransactionDTOResponse toDTO(Transaction transaction);

    @Mapping(source = "senderId", target = "sender.id")
    @Mapping(source = "receiverId", target = "receiver.id")
    Transaction toEntity(TransactionDTOResponse planificationDTOResponse);

    List<TransactionDTOResponse> toDTOList(List<Transaction> transactions);
}

