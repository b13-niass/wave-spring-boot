package com.example.wavespringboot.web.dto.response.mapper;

import com.example.wavespringboot.data.entity.Wallet;
import com.example.wavespringboot.web.dto.response.WalletDTOResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WalletResponseMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.nom", target = "userNom")
    WalletDTOResponse toDTO(Wallet wallet);

    List<WalletDTOResponse> toDTOList(List<Wallet> wallets);
}
