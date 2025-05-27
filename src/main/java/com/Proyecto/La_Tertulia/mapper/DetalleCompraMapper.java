package com.Proyecto.La_Tertulia.mapper;

import org.mapstruct.Mapper;

import com.Proyecto.La_Tertulia.dto.DetalleCompraDTO;
import com.Proyecto.La_Tertulia.model.DetalleCompra;

@Mapper(componentModel = "spring")
public interface DetalleCompraMapper {

    DetalleCompraDTO toDTO(DetalleCompra detalleCompra);

    DetalleCompra toEntity(DetalleCompraDTO detalleCompraDTO);

}
