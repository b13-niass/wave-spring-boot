package com.example.wavespringboot.web.dto.request.mapper;

import com.example.wavespringboot.data.entity.User;
import com.example.wavespringboot.web.dto.request.InscriptionClientDTORequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InscriptionClientDTOMapper {
    User toEntity(InscriptionClientDTORequest inscriptionClientDTORequest);
}
