package com.Proyecto.La_Tertulia.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Proyecto.La_Tertulia.model.Rol;
import com.Proyecto.La_Tertulia.repository.RolRepository;
@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    public Rol guardarRol(Rol rol) {
        return rolRepository.save(rol);
    }
    public Rol guardarRolSiNoExiste(String nombreRol) {
        Optional<Rol> rolExistente = rolRepository.findByNombreRol(nombreRol);
        if (rolExistente.isPresent()) {
            return rolExistente.get();
        } else {
            Rol nuevoRol = new Rol();
            nuevoRol.setNombreRol(nombreRol);
            return rolRepository.save(nuevoRol);
        }
    }
  
    }

