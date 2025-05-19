package com.Proyecto.La_Tertulia.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
                Persona persona = personaMapper.toEntity(personaDTO);
                persona.setId(null);
                personaGuardada = personaRepository.save(persona);
            } catch (DataIntegrityViolationException e) {
                String message = e.getMostSpecificCause().getMessage();
                if (message != null && message.contains("correo_electronico")) {
                    personaGuardada.setNombre("correo_electronico");
                    return personaMapper.toDTO(personaGuardada);
                } else if (message != null && message.contains("numero_documento")) {
                    personaGuardada.setNombre("numero_documento");
                    return personaMapper.toDTO(personaGuardada);
                }
            }
        } else {
            personaGuardada.setNombre("Persona_menor");
            return personaMapper.toDTO(personaGuardada);
        }
        return personaMapper.toDTO(personaGuardada);
    }

    public PersonaDTO updatePersona(PersonaDTO existingPersona) {
        Persona updatedPersona = new Persona();
        if (!existingPersona.getFechaNacimiento().isAfter(LocalDate.now().minusYears(18))) {
            Persona personaToUpdate = new Persona(existingPersona.getId(),
                    existingPersona.getDocumentoIdentidad(), existingPersona.getTipoDocumento(),
                    existingPersona.getNombre(), existingPersona.getApellido(), existingPersona.isEstado(),
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
        } else {
            updatedPersona.setNombre("Persona_menor");
        }
        return personaMapper.toDTO(updatedPersona);
    }

    // ESTO NO LO UTILIZA EL CONTROLADOR ES PARA LAS PRUEBAS UNITARIAS
    public ResponseEntity<PersonaDTO> fetchPersonaById(Long id) {
        Optional<Persona> persona = personaRepository.findById(id);
        if (persona.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else if (persona.get().isEstado() == false) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(personaMapper.toDTO(persona.get()), HttpStatus.OK);
    }

    // OJO PARA VALIDAD EN CONTROLLER HACER FINDBYID.ISPRESENT() SI ES FALSE ES QUE
    // NO EXISTE
    // LA PERSONA O TIENE EL ESTADO INACTIVO
    public Optional<PersonaDTO> findById(Long id) {
        Optional<Persona> foundPersona = personaRepository.findById(id);
        if (foundPersona.isPresent() && foundPersona.get().isEstado()) {
            return foundPersona.map(persona -> personaMapper.toDTO(persona));
        }
        return Optional.empty();

    }

    public PersonaDTO borrarPersonaInDB(Long id) {
        Optional<PersonaDTO> personaOptional = findById(id);
        if (personaOptional.isPresent()) {
            PersonaDTO personaDTO = personaOptional.get();
            personaDTO.setEstado(false);
            Persona persona = personaMapper.toEntity(personaDTO);
            Persona personaBorrada = personaRepository.save(persona);
            return personaMapper.toDTO(personaBorrada);
        }
        return null;

    }

    public PersonaDTO habiliarPersonaInDB(Long id) {
        Optional<Persona> personaOptional = personaRepository.findById(id);
        if (personaOptional.isPresent() && !personaOptional.get().isEstado()) {
            Persona persona = personaOptional.get();
            persona.setEstado(true);
            Persona personaHabilitada = personaRepository.save(persona);
            return personaMapper.toDTO(personaHabilitada);
        }
        return null;
    }

    public List<PersonaDTO> findByNombre(String nombreCompleto) {
        List<Persona> personas = personaRepository.findAll();
        String nombreBuscado = nombreCompleto.trim().toLowerCase();
        return personas.stream()
                .filter(Persona::isEstado)
                .filter(persona -> (persona.getNombre() + " " + persona.getApellido())
                        .trim()
                        .toLowerCase()
                        .equals(nombreBuscado))
                .map(personaMapper::toDTO)
                .collect(Collectors.toList());
    }
}