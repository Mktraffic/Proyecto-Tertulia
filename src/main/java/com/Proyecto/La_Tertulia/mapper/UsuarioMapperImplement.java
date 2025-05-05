package com.Proyecto.La_Tertulia.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.Proyecto.La_Tertulia.dto.RolDTO;
import com.Proyecto.La_Tertulia.dto.UsuarioDTO;
import com.Proyecto.La_Tertulia.model.Rol;
import com.Proyecto.La_Tertulia.model.Usuario;


@Component
public class UsuarioMapperImplement implements UsuarioMapper {

    @Autowired
    private PersonaMapper personaMapper; 

    @Override
    public UsuarioDTO toDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setUser_name(usuario.getUser_name());
        usuarioDTO.setUser_password(usuario.getUser_password());
        usuarioDTO.setPersona(personaMapper.toDTO(usuario.getPersona()));
        usuarioDTO.setRol(new RolDTO(usuario.getRol().getId(), usuario.getRol().getNombreRol()));
        return usuarioDTO;
    }

    @Override
    public Usuario toEntity(UsuarioDTO usuarioDTO) {
        if (usuarioDTO == null) {
            return null;
        }
    
        Usuario usuario = new Usuario();
        usuario.setId(usuarioDTO.getId());
        usuario.setUser_name(usuarioDTO.getUser_name());
        usuario.setUser_password(usuarioDTO.getUser_password());
        usuario.setPersona(personaMapper.toEntity(usuarioDTO.getPersona()));
        usuario.setRol(new Rol(usuarioDTO.getRol().getId(), usuarioDTO.getRol().getNombreRol()));
        return usuario;
    }
}

