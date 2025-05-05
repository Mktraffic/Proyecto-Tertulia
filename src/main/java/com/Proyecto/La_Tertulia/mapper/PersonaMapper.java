package com.Proyecto.La_Tertulia.mapper;

import org.mapstruct.Mapper;

import com.Proyecto.La_Tertulia.dto.PersonaDTO;
import com.Proyecto.La_Tertulia.model.Persona;


@Mapper(componentModel = "spring")
public interface PersonaMapper {

    PersonaDTO toDTO(Persona persona);
    Persona toEntity(PersonaDTO personaDTO);
}