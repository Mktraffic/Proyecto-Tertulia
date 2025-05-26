package com.Proyecto.La_Tertulia.mapper;

import org.mapstruct.Mapper;

import com.Proyecto.La_Tertulia.dto.RolDTO;
import com.Proyecto.La_Tertulia.model.Rol;

@Mapper(componentModel = "spring")
public interface RolMapper {

    RolDTO toDTO(Rol rol);

    
    Rol toEntity(RolDTO RolDTO);
}
