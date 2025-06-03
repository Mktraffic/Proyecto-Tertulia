package com.Proyecto.La_Tertulia;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.Proyecto.La_Tertulia.dto.PersonaDTO;
import com.Proyecto.La_Tertulia.dto.RolDTO;
import com.Proyecto.La_Tertulia.dto.UsuarioDTO;
import com.Proyecto.La_Tertulia.mapper.RolMapper;
import com.Proyecto.La_Tertulia.mapper.UsuarioMapper;
import com.Proyecto.La_Tertulia.model.Persona;
import com.Proyecto.La_Tertulia.model.Rol;
import com.Proyecto.La_Tertulia.model.Usuario;
import com.Proyecto.La_Tertulia.repository.RolRepository;
import com.Proyecto.La_Tertulia.repository.UsuarioRepository;
import com.Proyecto.La_Tertulia.service.UsuarioService;

import static org.mockito.Mockito.mock;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioMapper usuarioMapper;

    @InjectMocks
    private UsuarioService usuarioService;
     @Mock
    private RolMapper rolMapper; 

    @Mock
    private RolRepository rolRepository; 


    @Test
    void testFindAllUsuarios() {
        Rol rolAdm = new Rol(1L, "ADMINISTRADOR");
        Rol rolVen = new Rol(2L, "VENDEDOR");
        Rol rolProv = new Rol(3L, "PROVEEDOR");

        Persona persona1 = new Persona(1L, (long) 1002525090, "Cedula de extranjeria", "juan", "perez", true,
                (long) 313570908, "luis2020@gmail.com", LocalDate.of(1998, 1, 1));
        Persona persona2 = new Persona(2L, (long) 1002525091, "Cedula de ciudadania", "luis", "rincon", true,
                (long) 320802341,
                "luis3050@gmail.com", LocalDate.of(1990, 1, 1));
        Persona persona3 = new Persona(3L, (long) 1002525095, "Cedula de ciudadania", "jose", "pachon", true,
                (long) 320802343,
                "pedro3050@gmail.com", LocalDate.of(1990, 1, 1));

        Usuario usuario1 = new Usuario(1L, "user1", "password1", rolAdm, persona1);
        Usuario usuario2 = new Usuario(2L, "user2", "password2", rolVen, persona2);
        Usuario usuario3 = new Usuario(3L, "user3", "password3", rolProv, persona3);

        List<Usuario> usuarios = Arrays.asList(usuario1, usuario2, usuario3);

        PersonaDTO personaDTO1 = new PersonaDTO(1L, (long) 1002525090, "Cedula de extranjeria", "juan", "perez", true,
                (long) 313570908, "luis2020@gmail.com", LocalDate.of(1998, 1, 1));
        PersonaDTO personaDTO2 = new PersonaDTO(2L, (long) 1002525091, "Cedula de ciudadania", "luis", "rincon", true,
                (long) 320802341,
                "luis3050@gmail.com", LocalDate.of(1990, 1, 1));
        PersonaDTO personaDTO3 = new PersonaDTO(3L, (long) 1002525095, "Cedula de ciudadania", "jose", "pachon", true,
                (long) 320802343,
                "pedro3050@gmail.com", LocalDate.of(1990, 1, 1));
        RolDTO rolDTO1 = new RolDTO(1L, "ADMINISTRADOR");
        RolDTO rolDTO2 = new RolDTO(2L, "VENDEDOR");
        RolDTO rolDTO3 = new RolDTO(3L, "PROVEEDOR");

        UsuarioDTO usuarioDTO1 = new UsuarioDTO(1L, "user1", "password1", rolDTO1, personaDTO1);
        UsuarioDTO usuarioDTO2 = new UsuarioDTO(2L, "user2", "password2", rolDTO2, personaDTO2);
        UsuarioDTO usuarioDTO3 = new UsuarioDTO(3L, "user3", "password3", rolDTO3, personaDTO3);
        List<UsuarioDTO> expected = Arrays.asList(usuarioDTO1, usuarioDTO2, usuarioDTO3);

        Mockito.when(usuarioRepository.findAll()).thenReturn(usuarios);
        Mockito.when(usuarioMapper.toDTO(usuario1)).thenReturn(usuarioDTO1);
        Mockito.when(usuarioMapper.toDTO(usuario2)).thenReturn(usuarioDTO2);
        Mockito.when(usuarioMapper.toDTO(usuario3)).thenReturn(usuarioDTO3);

        List<UsuarioDTO> result = usuarioService.findAllUsuarios();

        Assertions.assertEquals(expected.size(), result.size());
        Assertions.assertEquals(expected.get(0).getId(), result.get(0).getId());
        Assertions.assertEquals(expected.get(1).getId(), result.get(1).getId());
        Assertions.assertEquals(expected.get(2).getId(), result.get(2).getId());
        Mockito.verify(usuarioRepository).findAll();
    }

    @Test
    void testAddUsuarioInDB() {

        Rol rolAdm = new Rol(1L, "ADMINISTRADOR");
        Rol rolVen = new Rol(2L, "VENDEDOR");
        Rol rolProv = new Rol(3L, "PROVEEDOR");

        Persona persona1 = new Persona(1L, (long) 1002525090, "Cedula de extranjeria", "juan", "perez", true,
                (long) 313570908, "luis2020@gmail.com", LocalDate.of(1998, 1, 1));
        Persona persona2 = new Persona(2L, (long) 1002525091, "Cedula de ciudadania", "luis", "rincon", true,
                (long) 320802341,
                "luis3050@gmail.com", LocalDate.of(1990, 1, 1));
        Persona persona3 = new Persona(3L, (long) 1002525095, "Cedula de ciudadania", "jose", "pachon", true,
                (long) 320802343,
                "pedro3050@gmail.com", LocalDate.of(1990, 1, 1));

        Usuario usuario1 = new Usuario(1L, "user1", "password1", rolAdm, persona1);
        Usuario usuario2 = new Usuario(2L, "user2", "password2", rolVen, persona2);
        Usuario usuario3 = new Usuario(3L, "user3", "password3", rolProv, persona3);

        PersonaDTO personaDTO1 = new PersonaDTO(1L, (long) 1002525090, "Cedula de extranjeria", "juan", "perez", true,
                (long) 313570908, "luis2020@gmail.com", LocalDate.of(1998, 1, 1));
        PersonaDTO personaDTO2 = new PersonaDTO(2L, (long) 1002525091, "Cedula de ciudadania", "luis", "rincon", true,
                (long) 320802341,
                "luis3050@gmail.com", LocalDate.of(1990, 1, 1));
        PersonaDTO personaDTO3 = new PersonaDTO(3L, (long) 1002525095, "Cedula de ciudadania", "jose", "pachon", true,
                (long) 320802343,
                "pedro3050@gmail.com", LocalDate.of(1990, 1, 1));
        RolDTO rolDTO1 = new RolDTO(1L, "ADMINISTRADOR");
        RolDTO rolDTO2 = new RolDTO(2L, "VENDEDOR");
        RolDTO rolDTO3 = new RolDTO(3L, "PROVEEDOR");

        UsuarioDTO usuarioDTO1 = new UsuarioDTO(1L, "user1", "password1", rolDTO1, personaDTO1);
        UsuarioDTO usuarioDTO2 = new UsuarioDTO(2L, "user2", "password2", rolDTO2, personaDTO2);
        UsuarioDTO usuarioDTO3 = new UsuarioDTO(3L, "user3", "password3", rolDTO3, personaDTO3);

        Mockito.when(usuarioMapper.toEntity(usuarioDTO1)).thenReturn(usuario1);
        Mockito.when(usuarioRepository.save(usuario1)).thenReturn(usuario1);
        Mockito.when(usuarioMapper.toDTO(usuario1)).thenReturn(usuarioDTO1);

        Mockito.when(usuarioMapper.toEntity(usuarioDTO2)).thenReturn(usuario2);
        Mockito.when(usuarioRepository.save(usuario2)).thenReturn(usuario2);
        Mockito.when(usuarioMapper.toDTO(usuario2)).thenReturn(usuarioDTO2);

        Mockito.when(usuarioMapper.toEntity(usuarioDTO3)).thenReturn(usuario3);
        Mockito.when(usuarioRepository.save(usuario3)).thenReturn(usuario3);
        Mockito.when(usuarioMapper.toDTO(usuario3)).thenReturn(usuarioDTO3);

        UsuarioDTO result1 = usuarioService.addUsuarioInDB(usuarioDTO1);
        Assertions.assertNotNull(result1);
        Assertions.assertEquals(usuarioDTO1.getUserName(), result1.getUserName());
        Mockito.verify(usuarioRepository).save(usuario1);

        UsuarioDTO result2 = usuarioService.addUsuarioInDB(usuarioDTO2);
        Assertions.assertNotNull(result2);
        Assertions.assertEquals(usuarioDTO2.getUserName(), result2.getUserName());
        Mockito.verify(usuarioRepository).save(usuario2);
        UsuarioDTO result3 = usuarioService.addUsuarioInDB(usuarioDTO3);
        Assertions.assertNotNull(result3);
        Assertions.assertEquals(usuarioDTO3.getUserName(), result3.getUserName());
        Mockito.verify(usuarioRepository).save(usuario3);

    }

    @Test
    void testValidateUserByUserName() {

        Rol rolAdm = new Rol(1L, "ADMINISTRADOR");
        Rol rolVen = new Rol(2L, "VENDEDOR");

        Persona persona1 = new Persona(1L, (long) 1002525090, "Cedula de extranjeria", "juan", "perez", true,
                (long) 313570908, "luis2020@gmail.com", LocalDate.of(1998, 1, 1));
        Persona persona2 = new Persona(2L, (long) 1002525091, "Cedula de ciudadania", "luis", "rincon", true,
                (long) 320802341,
                "luis3050@gmail.com", LocalDate.of(1990, 1, 1));

        Usuario usuario1 = new Usuario(1L, "user1", "password1", rolAdm, persona1);
        Usuario usuario2 = new Usuario(2L, "user2", "password2", rolVen, persona2);

        PersonaDTO personaDTO1 = new PersonaDTO(1L, (long) 1002525090, "Cedula de extranjeria", "juan", "perez", true,
                (long) 313570908, "luis2020@gmail.com", LocalDate.of(1998, 1, 1));
        PersonaDTO personaDTO2 = new PersonaDTO(2L, (long) 1002525091, "Cedula de ciudadania", "luis", "rincon", true,
                (long) 320802341,
                "luis3050@gmail.com", LocalDate.of(1990, 1, 1));

        RolDTO rolDTO1 = new RolDTO(1L, "ADMINISTRADOR");
        RolDTO rolDTO2 = new RolDTO(2L, "VENDEDOR");

        UsuarioDTO usuarioDTO1 = new UsuarioDTO(1L, "user1", "password1", rolDTO1, personaDTO1);
        UsuarioDTO usuarioDTO2 = new UsuarioDTO(2L, "user2", "password2", rolDTO2, personaDTO2);

        Mockito.when(usuarioRepository.findAll()).thenReturn(Arrays.asList(usuario1, usuario2));
        Mockito.when(usuarioMapper.toDTO(usuario1)).thenReturn(usuarioDTO1);
        Mockito.when(usuarioMapper.toDTO(usuario2)).thenReturn(usuarioDTO2);

        String result1 = usuarioService.validateUserByUserName("user1", "password1");
        Assertions.assertEquals("true,ADMINISTRADOR", result1);

        String result2 = usuarioService.validateUserByUserName("user2", "password2");
        Assertions.assertEquals("true,VENDEDOR", result2);

    }

    @Test
    void testValidateExistUserName() {

        Rol rolAdm = new Rol(1L, "ADMINISTRADOR");
        Persona persona = new Persona(1L, 1002525090L, "Cedula de ciudadania", "juan", "perez", true,
                313570908L, "juan@example.com", LocalDate.of(1990, 1, 1));
        Usuario usuario = new Usuario(1L, "juan123", "pass", rolAdm, persona);

        PersonaDTO personaDTO = new PersonaDTO(1L, 1002525090L, "Cedula de ciudadania", "juan", "perez", true,
                313570908L, "juan@example.com", LocalDate.of(1990, 1, 1));
        RolDTO rolDTO = new RolDTO(1L, "ADMINISTRADOR");
        UsuarioDTO usuarioDTO = new UsuarioDTO(1L, "juan123", "pass", rolDTO, personaDTO);

        List<Usuario> usuarios = List.of(usuario);
        List<UsuarioDTO> usuariosDTO = List.of(usuarioDTO);

        Mockito.when(usuarioRepository.findAll()).thenReturn(usuarios);
        Mockito.when(usuarioMapper.toDTO(usuario)).thenReturn(usuarioDTO);

        boolean resultado = usuarioService.validateExistUserName("juan123");

        Assertions.assertTrue(resultado);
        Mockito.verify(usuarioRepository).findAll();
    }

    @Test
    void testFindUserByNameOrID() {

        Rol rolAdm = new Rol(1L, "ADMINISTRADOR");
        Persona persona = new Persona(1L, 1002525090L, "Cedula de ciudadania", "Luis", "rincon", true,
                313570908L, "luis@example.com", LocalDate.of(1990, 1, 1));
        Usuario usuario = new Usuario(1L, "user1", "pass1", rolAdm, persona);

        PersonaDTO personaDTO = new PersonaDTO(1L, 1002525090L, "Cedula de ciudadania", "Luis", "rincon", true,
                313570908L, "luis@example.com", LocalDate.of(1990, 1, 1));
        RolDTO rolDTO = new RolDTO(1L, "ADMINISTRADOR");
        UsuarioDTO usuarioDTO = new UsuarioDTO(1L, "user1", "pass1", rolDTO, personaDTO);

        Mockito.when(usuarioRepository.findAll()).thenReturn(List.of(usuario));
        Mockito.when(usuarioMapper.toDTO(usuario)).thenReturn(usuarioDTO);

        ArrayList<UsuarioDTO> resultado = usuarioService.findUserByNameOrID("luis");

        Assertions.assertEquals(1, resultado.size());
        Assertions.assertEquals("luis", resultado.get(0).getPersona().getNombre().toLowerCase());
    }

    @Test
void testUpdateUsuario() {
    // Arrange
    RolDTO rolActual = new RolDTO(1L, "VENDEDOR");
    RolDTO rolNuevo = new RolDTO(1L, "ADMINISTRADOR");

    PersonaDTO personaDTO = new PersonaDTO(1L, 1002525090L, "Cedula de ciudadania", "Luis", "Rincon", true,
            313570908L, "luis@example.com", LocalDate.of(1990, 1, 1));

    UsuarioDTO usuarioExistente = new UsuarioDTO(1L, "usuario1", "oldPass", rolActual, personaDTO);
    UsuarioDTO nuevosDatos = new UsuarioDTO(1L, "usuario1", "newPass", rolNuevo, personaDTO);

    Rol rolActualEntity = new Rol(1L, "VENDEDOR");
    Rol rolNuevoEntity = new Rol(1L, "ADMINISTRADOR");

    Usuario usuarioEntity = new Usuario(1L, "usuario1", "newPass", rolNuevoEntity,
            new Persona(1L, 1002525090L, "Cedula de ciudadania", "Luis", "Rincon", true,
                    313570908L, "luis@example.com", LocalDate.of(1990, 1, 1)));

    UsuarioDTO usuarioDTOFinal = new UsuarioDTO(1L, "usuario1", "newPass", rolNuevo, personaDTO);

    // Mocks
    Mockito.when(rolMapper.toEntity(rolNuevo)).thenReturn(rolNuevoEntity);
    Mockito.when(usuarioMapper.toEntity(usuarioExistente)).thenReturn(usuarioEntity);
    Mockito.when(usuarioRepository.save(usuarioEntity)).thenReturn(usuarioEntity);
    Mockito.when(usuarioMapper.toDTO(usuarioEntity)).thenReturn(usuarioDTOFinal);

    // Act
    UsuarioDTO resultado = usuarioService.updateUsuario(usuarioExistente, nuevosDatos);

    // Assert
    Assertions.assertNotNull(resultado);
    Assertions.assertEquals("ADMINISTRADOR", resultado.getRol().getNombreRol());
    Assertions.assertEquals("newPass", resultado.getUserPassword());

    // Verify saves
    Mockito.verify(rolRepository).save(rolNuevoEntity);
    Mockito.verify(usuarioRepository).save(usuarioEntity);
    Mockito.verify(usuarioMapper).toDTO(usuarioEntity);
}


@Test
void testObtainSuppliers() {
    // Arrange
    Rol rolProveedor = new Rol(3L, "Proveedor");
    RolDTO rolProveedorDTO = new RolDTO(3L, "Proveedor");

    Persona persona1 = new Persona(1L, 100L, "Cedula", "Carlos", "Ruiz", true,
            123456789L, "carlos@proveedor.com", LocalDate.of(1980, 1, 1));
    PersonaDTO personaDTO1 = new PersonaDTO(1L, 100L, "Cedula", "Carlos", "Ruiz", true,
            123456789L, "carlos@proveedor.com", LocalDate.of(1980, 1, 1));

    Usuario usuario1 = new Usuario(1L, "proveedor1", "pass", rolProveedor, persona1);
    UsuarioDTO usuarioDTO1 = new UsuarioDTO(1L, "proveedor1", "pass", rolProveedorDTO, personaDTO1);

    // 👇 Aquí está la forma correcta
    Mockito.when(usuarioRepository.findAll()).thenReturn(List.of(usuario1));
    Mockito.when(usuarioMapper.toDTO(usuario1)).thenReturn(usuarioDTO1);

    // Act
    ArrayList<String> resultado = usuarioService.obtainSuppliers();

    // Assert
    Assertions.assertEquals(1, resultado.size());
    Assertions.assertTrue(resultado.contains("Carlos Ruiz"));
}


}
