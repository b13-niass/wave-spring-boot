package com.example.wavespringboot.web.dto.request.mapper;

import com.example.wavespringboot.data.entity.Transaction;

import com.example.wavespringboot.web.dto.request.TransfertDTORequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransfertDTOMapper {
    @Mapping(source = "telephone", target = "receiver.telephone")
    Transaction toEntity(TransfertDTORequest dto);
}
