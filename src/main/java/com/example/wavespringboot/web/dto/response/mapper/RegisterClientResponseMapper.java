package com.example.wavespringboot.web.dto.response.mapper;

import com.example.wavespringboot.data.entity.User;
import com.example.wavespringboot.web.dto.response.RegisterClientResponseDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")

public interface RegisterClientResponseMapper {
    User toEntity(RegisterClientResponseDTO registerClientResponseDTO);
    RegisterClientResponseDTO toDTO(User user);
    List<User> toDTOList(List<User> users);
    List<User> toEntityList(List<RegisterClientResponseDTO> registerClientResponseDTOS);
}
