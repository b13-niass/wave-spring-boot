package com.example.wavespringboot.web.dto.response.mapper;

import com.example.wavespringboot.data.entity.User;
import com.example.wavespringboot.web.dto.response.UserDTOResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserResponseMapper {

    @Mapping(source = "pays.libelle", target = "paysLibelle")
    UserDTOResponse toDTO(User user);

    List<UserDTOResponse> toDTOList(List<User> users);
}

