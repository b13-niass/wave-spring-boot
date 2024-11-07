package com.example.wavespringboot.web.dto.response.mapper;

import com.example.wavespringboot.data.entity.Favoris;
import com.example.wavespringboot.web.dto.response.FavorisDTOResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FavorisResponseMapper {

    @Mapping(source = "user.id", target = "userId")
    FavorisDTOResponse toDTO(Favoris favoris);

    List<FavorisDTOResponse> toDTOList(List<Favoris> favorisList);

    @Mapping(source = "userId", target = "user.id")
    Favoris toEntity(FavorisDTOResponse favorisDTOResponse);
}
