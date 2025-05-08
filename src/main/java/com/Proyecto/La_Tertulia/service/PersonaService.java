package com.Proyecto.La_Tertulia.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Proyecto.La_Tertulia.dto.PersonaDTO;
import com.Proyecto.La_Tertulia.mapper.PersonaMapper;
import com.Proyecto.La_Tertulia.model.Persona;
import com.Proyecto.La_Tertulia.repository.PersonaRepository;

@Service
public class PersonaService {

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private PersonaMapper personaMapper;

    public List<PersonaDTO> findAllPersonas() {
        List<Persona> personas = personaRepository.findAll();
        return personas.stream()
                .map(personaMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PersonaDTO addPersonaInDB(PersonaDTO personaDTO) {
        Persona personaGuardada = personaRepository.save(personaMapper.toEntity(personaDTO));
        return personaMapper.toDTO(personaGuardada);
    }

    public ResponseEntity<PersonaDTO> fetchPersonaById(Long id) {
        Optional<Persona> persona = personaRepository.findById(id);
        if (persona.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(personaMapper.toDTO(persona.get()), HttpStatus.OK);
    }

    public Optional<PersonaDTO> findById(Long id) {
        return personaRepository.findById(id).map(persona -> new PersonaDTO(persona.getId(), persona.getNombre(), persona.getApellido(),persona.getNumeroTelefono(), persona.getCorreo(), persona.getFechaNacimiento(),persona.getTipoDocumento()));
    }

}