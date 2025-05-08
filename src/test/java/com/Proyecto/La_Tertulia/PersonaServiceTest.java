package com.Proyecto.La_Tertulia;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.Proyecto.La_Tertulia.dto.PersonaDTO;
import com.Proyecto.La_Tertulia.mapper.PersonaMapper;
import com.Proyecto.La_Tertulia.model.Persona;
import com.Proyecto.La_Tertulia.repository.PersonaRepository;
import com.Proyecto.La_Tertulia.service.PersonaService;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class PersonaServiceTest {

    @Mock
    private PersonaRepository personaRepository;

    @Mock
    private PersonaMapper personaMapper;

    @InjectMocks
    private PersonaService personaService;

    @BeforeEach
    void setUp() {
        PersonaDTO personaDTO = new PersonaDTO(1L, "juan", "perez",
        310802532,"luis2020@gmail.com", LocalDate.of(1998, 1, 1),"Cedula de ciudadania");
        PersonaDTO personaDTO2 = new PersonaDTO(2L, "luis", "rincon",320802341,
        "luis3050@gmail.com", LocalDate.of(1990, 1, 1),"Cedula de ciudadania");
        personaService.addPersonaInDB(personaDTO);
        personaService.addPersonaInDB(personaDTO2);

    }

    @Test
    void testFindAllPersonas() {

        Persona persona1 = new Persona(1L, "juan", "perez",
        310802532,"luis2020@gmail.com",LocalDate.of(1998, 1, 1),"Cedula de ciudadania");
        Persona persona2 = new Persona(2L, "luis", "rincon"
        ,320802341,"luis3050@gmail.com", LocalDate.of(1990, 1, 1),"Cedula de ciudadania");
        List<Persona> personas = Arrays.asList(persona1, persona2);

        PersonaDTO personaDTO1 = new PersonaDTO(1L, "juan", "perez",
        310802532,"luis2020@gmail.com", LocalDate.of(1998, 1, 1),"Cedula de ciudadania");
        PersonaDTO personaDTO2 =new PersonaDTO(2L, "luis", "rincon",320802341,
        "luis3050@gmail.com", LocalDate.of(1990, 1, 1),"Cedula de ciudadania");
        List<PersonaDTO> expected = Arrays.asList(personaDTO1, personaDTO2);

        Mockito.when(personaRepository.findAll()).thenReturn(personas);
        Mockito.when(personaMapper.toDTO(persona1)).thenReturn(personaDTO1);
        Mockito.when(personaMapper.toDTO(persona2)).thenReturn(personaDTO2);

        List<PersonaDTO> result = personaService.findAllPersonas();
        Mockito.verify(personaRepository).findAll();
        Assertions.assertEquals(expected.size(), result.size());
        Assertions.assertEquals(expected.get(0).getId(), result.get(0).getId());
        Assertions.assertEquals(expected.get(1).getId(), result.get(1).getId());
    }

    @Test
    void testAddPersonaInDB() {

        PersonaDTO personaDTO = new PersonaDTO(3L, "max", "ruiz",320854987,
        "max202@gmail.com", LocalDate.of(1995, 1, 1),"Cedula de ciudadania");
        Persona persona = new Persona(3L, "max", "ruiz",320854987,
        "max202@gmail.com",  LocalDate.of(1995, 1, 1),"Cedula de ciudadania");

        Mockito.when(personaMapper.toEntity(personaDTO)).thenReturn(persona);
        Mockito.when(personaRepository.save(persona)).thenReturn(persona);
        Mockito.when(personaMapper.toDTO(persona)).thenReturn(personaDTO);

        PersonaDTO resultado = personaService.addPersonaInDB(personaDTO);
        Assertions.assertEquals(personaDTO.getId(), resultado.getId());
        Mockito.verify(personaRepository).save(persona);
       

    }

    @Test
    void testFetchPersonaByIdFound() {
    Long id = 1L;
    Persona persona = new Persona(id, "juan", "perez",
    310802532,"luis2020@gmail.com", LocalDate.of(1998, 1, 1),"Cedula de ciudadania");
    PersonaDTO personaDTO = new PersonaDTO(id, "juan", "perez",
    310802532,"luis2020@gmail.com", LocalDate.of(1990, 1, 1),"Cedula de ciudadania");

    Mockito.when(personaRepository.findById(id)).thenReturn(Optional.of(persona));
    Mockito.when(personaMapper.toDTO(persona)).thenReturn(personaDTO);

    ResponseEntity<PersonaDTO> response = personaService.fetchPersonaById(id);

    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertNotNull(response.getBody());
    Assertions.assertEquals(personaDTO.getId(), response.getBody().getId());
    Mockito.verify(personaRepository).findById(id);

    }

   
}
