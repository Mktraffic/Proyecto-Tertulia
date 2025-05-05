package com.Proyecto.La_Tertulia.mapper;

import org.springframework.stereotype.Component;

import com.Proyecto.La_Tertulia.dto.RolDTO;
import com.Proyecto.La_Tertulia.model.Rol;

@Component
public class RolMapperImplement implements RolMapper {

    @Override
    public RolDTO toDTO(Rol rol) {
        if (rol == null) {
            return null;
        }
        RolDTO rolDTO = new RolDTO();
        rolDTO.setId(rol.getId());
        rolDTO.setNombreRol(rol.getNombreRol());
        return rolDTO;
    }

    @Override
    public Rol toEntity(RolDTO rolDTO) {
        if (rolDTO == null) {
            return null;
        }
        Rol rol = new Rol();
        rol.setId(rolDTO.getId());
        rol.setNombreRol(rolDTO.getNombreRol());
        return rol;
    }

}
