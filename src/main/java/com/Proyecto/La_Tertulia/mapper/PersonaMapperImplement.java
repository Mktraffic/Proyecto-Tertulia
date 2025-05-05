package com.Proyecto.La_Tertulia.mapper;

import org.springframework.stereotype.Component;

import com.Proyecto.La_Tertulia.dto.PersonaDTO;
import com.Proyecto.La_Tertulia.model.Persona;


@Component
public class PersonaMapperImplement implements PersonaMapper {

    @Override
    public PersonaDTO toDTO(Persona persona) {
        if (!persona.equals(null)) {
            return new PersonaDTO(persona.getId(), persona.getNombre(), persona.getApellido(), persona.getNumeroTelefono(), persona.getCorreo(), persona.getFechaNacimiento());
        }
        return null;
    }

    @Override
    public Persona toEntity(PersonaDTO personaDTO) {
        if (!personaDTO.equals(null)) {
            return new Persona(personaDTO.getId(), personaDTO.getNombre(), personaDTO.getApellido(), personaDTO.getNumeroTelefono(), personaDTO.getCorreo(), personaDTO.getFechaNacimiento());
        }
        return null;
    }

}
