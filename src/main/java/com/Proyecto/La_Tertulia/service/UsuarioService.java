package com.Proyecto.La_Tertulia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Proyecto.La_Tertulia.dto.UsuarioDTO;
import com.Proyecto.La_Tertulia.mapper.UsuarioMapper;
import com.Proyecto.La_Tertulia.model.Usuario;
import com.Proyecto.La_Tertulia.repository.UsuarioRepository;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    public List<UsuarioDTO> findAllUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(usuarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UsuarioDTO addUsuarioInDB(UsuarioDTO usuarioDTO) {
        System.out.println("Usuario antes de persistir: " + usuarioDTO);
        System.out.println("Contraseña antes de persistir: " + usuarioDTO.getUserPassword());
        Usuario usuarioGuardado = usuarioRepository.save(usuarioMapper.toEntity(usuarioDTO));
        return usuarioMapper.toDTO(usuarioGuardado);
    }

    public String validateUserByUserName(String userName, String password) {
        List<UsuarioDTO> userList = findAllUsuarios();
        for (UsuarioDTO usuario : userList) {
            if (usuario.getUserName().equals(userName)) {
                if (usuario.getUserPassword().equals(password)) {
                return true+","+usuario.getRol().getNombreRol();
            } else {
                return "false,WRONG_PASSWORD";
            }
        }
        }
        return "false,USER_NOT_FOUND";
    }
    public boolean validateExistUserName(String userName) {
        List<UsuarioDTO> userList = findAllUsuarios();
        boolean reponse = false;
        for (int i = 0; i < userList.size(); i++) {
            if(userList.get(i).getUserName().equals(userName)){
                reponse=true;
                break;
            }
        }
        return reponse;
    }

}
