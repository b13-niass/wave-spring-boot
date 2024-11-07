package com.example.wavespringboot.web.dto.mapper;

import com.example.wavespringboot.data.entity.Transaction;
import com.example.wavespringboot.data.entity.User;
import com.example.wavespringboot.enums.EtatTransactionEnum;
import com.example.wavespringboot.enums.TypeTransactionEnum;
import com.example.wavespringboot.web.dto.request.TransfertDTORequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;


@Mapper(componentModel = "spring")
public interface TransfertDTOMapper {
    @Mapping(source = "receiverId", target = "receiver.id")
    Transaction toEntity(TransfertDTORequest dto);
}
