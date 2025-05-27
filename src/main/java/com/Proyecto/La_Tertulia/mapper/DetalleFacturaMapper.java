package com.Proyecto.La_Tertulia.mapper;

import org.mapstruct.Mapper;

import com.Proyecto.La_Tertulia.dto.DetalleFacturaDTO;
import com.Proyecto.La_Tertulia.model.DetalleFactura;

@Mapper(componentModel = "spring")
public interface DetalleFacturaMapper {

    DetalleFacturaDTO toDTO(DetalleFactura detalleFactura);

    DetalleFactura toEntity(DetalleFacturaDTO detalleFacturaDTO);

}
