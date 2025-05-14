package com.Proyecto.La_Tertulia.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.aspectj.weaver.NewConstructorTypeMunger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
        Persona personaGuardada = new Persona();
        if (!personaDTO.getFechaNacimiento().isAfter(LocalDate.now().minusYears(18))) {
        try {
            personaGuardada = personaRepository.save(personaMapper.toEntity(personaDTO));
        } catch (DataIntegrityViolationException e) {
            String message = e.getMostSpecificCause().getMessage();
            if (message != null && message.contains("correo_electronico")) {
                personaGuardada.setNombre("correo_electronico");
            } else if (message != null && message.contains("numero_documento")) {
                personaGuardada.setNombre("numero_documento");
            }
        }
        }else {
            personaGuardada.setNombre("Persona_menor");
        }
        return personaMapper.toDTO(personaGuardada);
    }

    public PersonaDTO updatePersona(PersonaDTO existingPersona) {
        Persona updatedPersona = new Persona();
        if (!existingPersona.getFechaNacimiento().isAfter(LocalDate.now().minusYears(18))) {
            Persona personaToUpdate = new Persona(existingPersona.getId(),
                existingPersona.getDocumentoIdentidad(), existingPersona.getTipoDocumento(),
                existingPersona.getNombre(), existingPersona.getApellido(),
                existingPersona.getNumeroTelefono(), existingPersona.getCorreo(),
                existingPersona.getFechaNacimiento());
        try {
            updatedPersona = personaRepository.save(personaToUpdate);
        } catch (DataIntegrityViolationException e) {
            String message = e.getMostSpecificCause().getMessage();
            if (message != null && message.contains("correo_electronico")) {
                personaToUpdate.setNombre("correo_electronico");
            } else if (message != null && message.contains("numero_documento")) {
                personaToUpdate.setNombre("numero_documento");
            }
        }
        }else {
            updatedPersona.setNombre("Persona_menor");
        }
        return personaMapper.toDTO(updatedPersona);
    }

    public PersonaDTO createPerson(PersonaDTO person) {
        PersonaDTO persona = new PersonaDTO();
        persona.setDocumentoIdentidad(person.getDocumentoIdentidad());
        persona.setTipoDocumento(person.getTipoDocumento());
        persona.setNombre(person.getNombre());
        persona.setApellido(person.getApellido());
        persona.setNumeroTelefono(person.getNumeroTelefono());
        persona.setCorreo(person.getCorreo());
        persona.setFechaNacimiento(person.getFechaNacimiento());
        return persona;
    }

    public ResponseEntity<PersonaDTO> fetchPersonaById(Long id) {
        Optional<Persona> persona = personaRepository.findById(id);
        if (persona.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(personaMapper.toDTO(persona.get()), HttpStatus.OK);
    }

    public Optional<PersonaDTO> findById(Long id) {
        return personaRepository.findById(id)
                .map(persona -> new PersonaDTO(persona.getId(), persona.getDocumentoIdentidad(),
                        persona.getTipoDocumento(), persona.getNombre(), persona.getApellido(),
                        persona.getNumeroTelefono(), persona.getCorreo(), persona.getFechaNacimiento()));
    }

    public List<PersonaDTO> findByName(String nombre) {
        List<Persona> personas = personaRepository.findByName(nombre);
        return personas.stream()
                .map(personaMapper::toDTO)
                .collect(Collectors.toList());
    }
}