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
import com.Proyecto.La_Tertulia.mapper.UsuarioMapper;
import com.Proyecto.La_Tertulia.model.Persona;
import com.Proyecto.La_Tertulia.model.Rol;
import com.Proyecto.La_Tertulia.model.Usuario;
import com.Proyecto.La_Tertulia.repository.UsuarioRepository;
import com.Proyecto.La_Tertulia.service.UsuarioService;

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

    @Test
    void testFindAllUsuarios() {
        Rol rolAdm = new Rol(1L, "ADMINISTRADOR");
        Rol rolVen = new Rol(2L, "VENDEDOR");

        Persona persona1 = new Persona(1L, "juan", "perez",
                310802532, "luis2020@gmail.com", "1998-01-01");
        Persona persona2 = new Persona(2L, "luis", "rincon", 320802341, "luis3050@gmail.com", "1990-01-01");
        
        Usuario usuario1 = new Usuario(1L, "user1", "password1", rolAdm, persona1);
        Usuario usuario2 = new Usuario(2L, "user2", "password2", rolVen, persona2);


        List<Usuario> usuarios = Arrays.asList(usuario1, usuario2);

        PersonaDTO personaDTO1 =  new PersonaDTO(1L, "juan", "perez",
        310802532,"luis2020@gmail.com", "1998-01-01");
        PersonaDTO personaDTO2 =new PersonaDTO(2L, "luis", "rincon",320802341,
        "luis3050@gmail.com", "1990-01-01");
        
        RolDTO rolDTO1 = new RolDTO(1L, "ADMINISTRADOR");
        RolDTO rolDTO2 = new RolDTO(2L, "VENDEDOR");
     
        UsuarioDTO usuarioDTO1 = new UsuarioDTO(1L, "user1", "password1", rolDTO1, personaDTO1);
        UsuarioDTO usuarioDTO2 = new UsuarioDTO(2L, "user2", "password2", rolDTO2, personaDTO2);
        

        List<UsuarioDTO> expected = Arrays.asList(usuarioDTO1, usuarioDTO2);

        Mockito.when(usuarioRepository.findAll()).thenReturn(usuarios);
        Mockito.when(usuarioMapper.toDTO(usuario1)).thenReturn(usuarioDTO1);
        Mockito.when(usuarioMapper.toDTO(usuario2)).thenReturn(usuarioDTO2);
       
        List<UsuarioDTO> result = usuarioService.findAllUsuarios();

        Assertions.assertEquals(expected.size(), result.size());
        Assertions.assertEquals(expected.get(0).getId(), result.get(0).getId());
        Assertions.assertEquals(expected.get(1).getId(), result.get(1).getId());
        Mockito.verify(usuarioRepository).findAll();
    }

    @Test
    void testAddUsuarioInDB() {

        Rol rolAdm = new Rol(1L, "ADMINISTRADOR");
        Rol rolVen = new Rol(2L, "VENDEDOR");
       
        Persona persona1 = new Persona(1L, "juan", "perez",
                310802532, "luis2020@gmail.com", "1998-01-01");
        Persona persona2 = new Persona(2L, "luis", "rincon", 320802341, "luis3050@gmail.com", "1990-01-01");
        Usuario usuario1 = new Usuario(1L, "user1", "password1", rolAdm, persona1);
        Usuario usuario2 = new Usuario(2L, "user2", "password2", rolVen, persona2);
       

        PersonaDTO personaDTO1 =  new PersonaDTO(1L, "juan", "perez",
        310802532,"luis2020@gmail.com", "1998-01-01");
        PersonaDTO personaDTO2 =new PersonaDTO(2L, "luis", "rincon",320802341,
        "luis3050@gmail.com", "1990-01-01");

        RolDTO rolDTO1 = new RolDTO(1L, "ADMINISTRADOR");
        RolDTO rolDTO2 = new RolDTO(2L, "VENDEDOR");
        UsuarioDTO usuarioDTO1 = new UsuarioDTO(1L, "user1", "password1", rolDTO1, personaDTO1);
        UsuarioDTO usuarioDTO2 = new UsuarioDTO(2L, "user2", "password2", rolDTO2, personaDTO2);
        

        Mockito.when(usuarioMapper.toEntity(usuarioDTO1)).thenReturn(usuario1);
        Mockito.when(usuarioRepository.save(usuario1)).thenReturn(usuario1);
        Mockito.when(usuarioMapper.toDTO(usuario1)).thenReturn(usuarioDTO1);

        Mockito.when(usuarioMapper.toEntity(usuarioDTO2)).thenReturn(usuario2);
        Mockito.when(usuarioRepository.save(usuario2)).thenReturn(usuario2);
        Mockito.when(usuarioMapper.toDTO(usuario2)).thenReturn(usuarioDTO2);

      

        UsuarioDTO result1 = usuarioService.addUsuarioInDB(usuarioDTO1);
        Assertions.assertNotNull(result1);
        Assertions.assertEquals(usuarioDTO1.getUser_name(), result1.getUser_name());
        Mockito.verify(usuarioRepository).save(usuario1);

        UsuarioDTO result2 = usuarioService.addUsuarioInDB(usuarioDTO2);
        Assertions.assertNotNull(result2);
        Assertions.assertEquals(usuarioDTO2.getUser_name(), result2.getUser_name());
        Mockito.verify(usuarioRepository).save(usuario2);

        
    }

    @Test
    void testValidateUserByUserName() {

        Rol rolAdm = new Rol(1L, "ADMINISTRADOR");
        Rol rolVen = new Rol(2L, "VENDEDOR");
        Persona persona1 = new Persona(1L, "juan", "perez",
                310802532, "luis2020@gmail.com", "1998-01-01");
        Persona persona2 = new Persona(2L, "luis", "rincon", 320802341, "luis3050@gmail.com", "1990-01-01");
        Usuario usuario1 = new Usuario(1L, "user1", "password1", rolAdm, persona1);
        Usuario usuario2 = new Usuario(2L, "user2", "password2", rolVen, persona2);


        PersonaDTO personaDTO1 =  new PersonaDTO(1L, "juan", "perez",
        310802532,"luis2020@gmail.com", "1998-01-01");
        PersonaDTO personaDTO2 =new PersonaDTO(2L, "luis", "rincon",320802341,
        "luis3050@gmail.com", "1990-01-01");
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

    
        String result4 = usuarioService.validateUserByUserName("user4", "password4");
        Assertions.assertEquals("false,", result4);
    }
}
