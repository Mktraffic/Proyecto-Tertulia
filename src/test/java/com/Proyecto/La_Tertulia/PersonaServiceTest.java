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
                PersonaDTO personaDTO = new PersonaDTO(1L, (long) 1002525090, "Cedula de extranjeria", "juan", "perez",
                                true,
                                (long) 313570908, "luis2020@gmail.com", LocalDate.of(1998, 1, 1));
                PersonaDTO personaDTO2 = new PersonaDTO(2L, (long) 1002525091, "Cedula de ciudadania", "luis", "rincon",
                                true,
                                (long) 320802341,
                                "luis3050@gmail.com", LocalDate.of(1990, 1, 1));
                personaService.addPersonaInDB(personaDTO);
                personaService.addPersonaInDB(personaDTO2);

        }

        @Test
        void testFindAllPersonas() {

                Persona persona1 = new Persona(1L, (long) 1002525090, "Cedula de extranjeria", "juan", "perez", true,
                                (long) 313570908, "luis2020@gmail.com", LocalDate.of(1998, 1, 1));
                Persona persona2 = new Persona(2L, (long) 1002525091, "Cedula de ciudadania", "luis", "rincon", true,
                                (long) 320802341,
                                "luis3050@gmail.com", LocalDate.of(1990, 1, 1));
                List<Persona> personas = Arrays.asList(persona1, persona2);

                PersonaDTO personaDTO1 = new PersonaDTO(1L, (long) 1002525090, "Cedula de extranjeria", "juan", "perez",
                                true,
                                (long) 313570908, "luis2020@gmail.com", LocalDate.of(1998, 1, 1));
                PersonaDTO personaDTO2 = new PersonaDTO(2L, (long) 1002525091, "Cedula de ciudadania", "luis", "rincon",
                                true,
                                (long) 320802341,
                                "luis3050@gmail.com", LocalDate.of(1990, 1, 1));
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

                PersonaDTO personaDTO = new PersonaDTO(3L, (long) 887776, "Cedula de extranjeria", "max", "ruiz", true,
                                (long) 320854987,
                                "max202@gmail.com", LocalDate.of(1995, 1, 1));
                Persona persona = new Persona(3L, (long) 7776666, "Cedula de ciudadania", "max", "ruiz", true,
                                (long) 320854987,
                                "max202@gmail.com", LocalDate.of(1995, 1, 1));

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
                Persona persona = new Persona(id, (long) 456954, "Cedula de extranjeria", "juan", "perez", true,
                                (long) 310802532, "luis2020@gmail.com", LocalDate.of(1998, 1, 1));
                PersonaDTO personaDTO = new PersonaDTO(id, (long) 93986252, "Cedula de ciudadania", "juan", "perez",
                                true,
                                (long) 310802532, "luis2020@gmail.com", LocalDate.of(1990, 1, 1));

                Mockito.when(personaRepository.findById(id)).thenReturn(Optional.of(persona));
                Mockito.when(personaMapper.toDTO(persona)).thenReturn(personaDTO);

                ResponseEntity<PersonaDTO> response = personaService.fetchPersonaById(id);

                Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
                Assertions.assertNotNull(response.getBody());
                Assertions.assertEquals(personaDTO.getId(), response.getBody().getId());
                Mockito.verify(personaRepository).findById(id);

        }

        @Test
        void testUpdatePerson() {

                PersonaDTO existente = new PersonaDTO(
                                2L, 1002525091L, "Cedula de ciudadania", "luis", "rincon", true,
                                320802341L, "luis3050@gmail.com", LocalDate.of(1990, 1, 1));

                PersonaDTO nuevosDatos = new PersonaDTO(
                                2L, 1002525091L, "Cedula de ciudadania", "luis", "rincon", true,
                                321999999L, "nuevoemail@gmail.com", LocalDate.of(1990, 1, 1));

                Persona entidadActualizada = new Persona(
                                2L, 1002525091L, "Cedula de ciudadania", "luis", "rincon", true,
                                321999999L, "nuevoemail@gmail.com", LocalDate.of(1990, 1, 1));

                PersonaDTO dtoEsperado = new PersonaDTO(
                                2L, 1002525091L, "Cedula de ciudadania", "luis", "rincon", true,
                                321999999L, "nuevoemail@gmail.com", LocalDate.of(1990, 1, 1));

                Mockito.when(personaMapper.toEntity(existente)).thenReturn(entidadActualizada);
                Mockito.when(personaRepository.save(entidadActualizada)).thenReturn(entidadActualizada);
                Mockito.when(personaMapper.toDTO(entidadActualizada)).thenReturn(dtoEsperado);

                PersonaDTO resultado = personaService.updatePersona(existente, nuevosDatos);

                Assertions.assertEquals(dtoEsperado.getNumeroTelefono(), resultado.getNumeroTelefono());
                Assertions.assertEquals(dtoEsperado.getCorreo(), resultado.getCorreo());
                Assertions.assertTrue(resultado.isEstado());

                Mockito.verify(personaRepository).save(Mockito.any(Persona.class));
                Mockito.verify(personaMapper).toDTO(Mockito.any(Persona.class));
        }

      

        @Test
        void testFindByNombre() {

                Persona persona = new Persona(
                                1L, 123456789L, "Cedula de ciudadania", "Luis", "Rincon", true,
                                310123456L, "luis@gmail.com", LocalDate.of(1990, 1, 1));

                PersonaDTO personaDTO = new PersonaDTO(
                                1L, 123456789L, "Cedula de ciudadania", "Luis", "Rincon", true,
                                310123456L, "luis@gmail.com", LocalDate.of(1990, 1, 1));

                Mockito.when(personaRepository.findAll()).thenReturn(List.of(persona));
                Mockito.when(personaMapper.toDTO(persona)).thenReturn(personaDTO);

                List<PersonaDTO> resultado = personaService.findByNombre("luis rincon");

                Assertions.assertEquals(1, resultado.size());
                Assertions.assertEquals("Luis", resultado.get(0).getNombre());
                Assertions.assertEquals("Rincon", resultado.get(0).getApellido());
        }

}
