package com.Proyecto.La_Tertulia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.Proyecto.La_Tertulia.dto.UsuarioDTO;

import com.Proyecto.La_Tertulia.mapper.UsuarioMapper;
import com.Proyecto.La_Tertulia.model.Rol;
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

    public UsuarioDTO findUsuarioById(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario != null) {
            return usuarioMapper.toDTO(usuario);
        }
        return null;
    }

    /**
     * Actualiza el rol de un usuario existente.
     *
     * @param idUsuario      ID del usuario a actualizar.
     * @param nuevoNombreRol ID del nuevo rol a asignar al usuario.
     * @return El usuario actualizado como UsuarioDTO. y null si no se realizó
     *         ningún cambio.
     */
    public UsuarioDTO updtaeRolUsuario(Long idUsuario, Long nuevoNombreRol) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Rol nuevoRol = rolRepository.findById(nuevoNombreRol)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        if (!usuario.getRol().getId().equals(nuevoRol.getId())) {
            usuario.setRol(nuevoRol);
            usuarioRepository.save(usuario);
            return usuarioMapper.toDTO(usuario);

        }
        return null;

    }

    /**
     * Cambia la contraseña de un usuario existente.
     *
     * @param idUsuario       ID del usuario cuya contraseña se va a cambiar.
     * @param nuevaContrasena Nueva contraseña a establecer.
     * @return true si la contraseña se cambió correctamente, false si no se realizó
     *         ningún cambio.
     */
    public boolean changePasword(Long idUsuario, String nuevaContrasena) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if (nuevaContrasena != null && !nuevaContrasena.isBlank()
                && !usuario.getUserPassword().equals(nuevaContrasena)) {
            usuario.setUserPassword(nuevaContrasena);
            usuarioRepository.save(usuario);
            return true;
        }
        return false;

    }

    /**
     * Cambia el nombre de usuario de un usuario existente.
     *
     * @param idUsuario          ID del usuario cuyo nombre de usuario se va a
     *                           cambiar.
     * @param nuevoNombreUsuario Nuevo nombre de usuario a establecer.
     * @return true si el nombre de usuario se cambió correctamente, false si no se
     *         realizó ningún cambio.
     */
    public boolean changeUsername(Long idUsuario, String nuevoNombreUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (nuevoNombreUsuario != null &&
                !nuevoNombreUsuario.isBlank() &&
                !usuario.getUserName().equals(nuevoNombreUsuario)) {

            usuario.setUserName(nuevoNombreUsuario);
            usuarioRepository.save(usuario);
            return true;
        }

        return false;
    }

    /**
     * Actualiza un usuario existente en la base de datos.
     *
     * 
     * @param datosActualizados Datos actualizados del usuario.
     * @return El usuario actualizado como UsuarioDTO o null si lo actualizo con un
     *         dato que ya era el mismo ejemplo la misma contrasena.
     */
    public UsuarioDTO updateUsuario(UsuarioDTO datosActualizados) {
        Usuario usuario = usuarioRepository.findById(datosActualizados.getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (changePasword(datosActualizados.getId(), datosActualizados.getUserPassword()) &&
                updtaeRolUsuario(datosActualizados.getId(), datosActualizados.getRol().getId()) != null &&
                changeUsername(datosActualizados.getId(), datosActualizados.getUserName())) {
            Usuario usuarioToUpdate = usuarioRepository.findById(datosActualizados.getId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            return usuarioMapper.toDTO(usuarioToUpdate);
        }
        return null;

    }

}
