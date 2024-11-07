package com.example.wavespringboot.web.dto.response.mapper;

import com.example.wavespringboot.data.entity.Planification;
import com.example.wavespringboot.web.dto.response.PlanificationDTOResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlanificationResponseMapper {

    @Mapping(source = "sender.id", target = "senderId")
    @Mapping(source = "receiver.id", target = "receiverId")
    PlanificationDTOResponse toDTO(Planification planification);

    List<PlanificationDTOResponse> toDTOList(List<Planification> planifications);

    // Optional: For mapping DTO to entity if needed
    @Mapping(source = "senderId", target = "sender.id")
    @Mapping(source = "receiverId", target = "receiver.id")
    Planification toEntity(PlanificationDTOResponse planificationDTOResponse);
}
