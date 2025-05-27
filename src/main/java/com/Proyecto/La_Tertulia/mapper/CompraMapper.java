package com.Proyecto.La_Tertulia.mapper;

import org.mapstruct.Mapper;

import com.Proyecto.La_Tertulia.dto.CompraDTO;
import com.Proyecto.La_Tertulia.model.Compra;

@Mapper(componentModel = "spring")
public interface CompraMapper {

    CompraDTO toDTO(Compra compra);

    Compra toEntity(CompraDTO compraDTO);

}
