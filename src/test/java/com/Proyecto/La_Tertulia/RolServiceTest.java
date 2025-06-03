package com.Proyecto.La_Tertulia;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.Proyecto.La_Tertulia.model.Rol;
import com.Proyecto.La_Tertulia.repository.RolRepository;
import com.Proyecto.La_Tertulia.service.RolService;

@ExtendWith(MockitoExtension.class)
class RolServiceTest {

    @Mock
    private RolRepository rolRepository;

    @InjectMocks
    private RolService rolService;

    @Test
    void testGuardarRol() {
        Rol rol = new Rol(1L, "ADMINISTRADOR");

        Mockito.when(rolRepository.save(rol)).thenReturn(rol);

        Rol resultado = rolService.guardarRol(rol);

        Assertions.assertEquals(rol.getId(), resultado.getId());
        Assertions.assertEquals(rol.getNombreRol(), resultado.getNombreRol());

        Mockito.verify(rolRepository).save(rol);
    }

    @Test
    void testGuardarRolSiExiste() {
        Rol rolExistente = new Rol(1L, "VENDEDOR");

        Mockito.when(rolRepository.findByNombreRol("VENDEDOR"))
                .thenReturn(Optional.of(rolExistente));

        Rol resultado = rolService.guardarRolSiNoExiste("VENDEDOR");

        Assertions.assertEquals("VENDEDOR", resultado.getNombreRol());
        Mockito.verify(rolRepository, Mockito.never()).save(Mockito.any());
    }

}
