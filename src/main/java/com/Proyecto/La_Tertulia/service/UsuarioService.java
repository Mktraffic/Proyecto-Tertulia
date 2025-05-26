package com.Proyecto.La_Tertulia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Proyecto.La_Tertulia.dto.ProductDTO;
import com.Proyecto.La_Tertulia.dto.UsuarioDTO;
import com.Proyecto.La_Tertulia.mapper.RolMapper;
import com.Proyecto.La_Tertulia.mapper.UsuarioMapper;
import com.Proyecto.La_Tertulia.model.Usuario;
import com.Proyecto.La_Tertulia.repository.RolRepository;
import com.Proyecto.La_Tertulia.repository.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;
    @Autowired
    private RolMapper rolMapper;

    public List<UsuarioDTO> findAllUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(usuarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UsuarioDTO addUsuarioInDB(UsuarioDTO usuarioDTO) {
        Usuario usuarioGuardado = new Usuario();
        if (usuarioDTO.getRol().getNombreRol().toLowerCase().equals("proveedor")) {
            usuarioGuardado = usuarioRepository.save(usuarioMapper.toEntity(usuarioDTO));
        } else {
            if (usuarioDTO.getUserName() != null || usuarioDTO.getUserPassword() != null) {
                    usuarioGuardado = usuarioRepository.save(usuarioMapper.toEntity(usuarioDTO));
            }
        }
        return usuarioMapper.toDTO(usuarioGuardado);
    }

    public String validateUserByUserName(String userName, String password) {
        List<UsuarioDTO> userList = findAllUsuarios();
        for (UsuarioDTO usuario : userList) {
            if (usuario.getUserName().equals(userName)) {
                if (usuario.getUserPassword().equals(password)) {
                    return true + "," + usuario.getRol().getNombreRol();
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
            if (userList.get(i).getUserName().equals(userName)) {
                reponse = true;
                break;
            }
        }
        return reponse;
    }

    public ArrayList<UsuarioDTO> findUserByNameOrID(String id) {
        ArrayList<UsuarioDTO> userList = new ArrayList<>();
        List<UsuarioDTO> usuarioDTOs = findAllUsuarios();
        for (UsuarioDTO usuarioDTO : usuarioDTOs) {
            if (id.matches("\\d+")) {
                long idAux = Long.parseLong(id);
                if (usuarioDTO.getPersona().getId() == idAux) {
                    userList.add(usuarioDTO);
                    break;
                }
            } else {
                if (usuarioDTO.getPersona().getNombre().equalsIgnoreCase(id)) {
                    userList.add(usuarioDTO);
                }
            }

        }
        return userList;
    }

    public UsuarioDTO findUserByName(String name) {
        List<UsuarioDTO> usuarioDTOs = findAllUsuarios();
        for (UsuarioDTO usuarioDTO : usuarioDTOs) {
            if (usuarioDTO.getUserName().equals(name)) {
                System.out.println(usuarioDTO);
                System.out.println(usuarioDTO.getPersona());
                return usuarioDTO;
            }
        }
        return null;
    }

    public UsuarioDTO findUsuarioById(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario != null) {
            return usuarioMapper.toDTO(usuario);
        }
        return null;
    }

    public UsuarioDTO updateUsuario(UsuarioDTO usuarioExistente, UsuarioDTO datos) {
        if (datos.getRol().getNombreRol() != null && !datos.getRol().getNombreRol().isEmpty() && datos.getRol().getNombreRol() != usuarioExistente.getRol().getNombreRol()) {
            usuarioExistente.getRol().setNombreRol(datos.getRol().getNombreRol());
            rolRepository.save(rolMapper.toEntity(usuarioExistente.getRol()));

            System.out.println(datos.getRol().getNombreRol());
            System.out.println(usuarioExistente.getRol().getNombreRol());
        }
        if(datos.getUserPassword() != null && !datos.getUserPassword().isEmpty() && !datos.getUserPassword().equals(usuarioExistente.getUserPassword())) {
            usuarioExistente.setUserPassword(datos.getUserPassword());

            System.out.println(datos.getUserPassword());
            System.out.println(usuarioExistente.getUserPassword());
        }

        System.out.println(usuarioExistente);

        return usuarioMapper.toDTO(usuarioRepository.save(usuarioMapper.toEntity(usuarioExistente)));
    }
}
