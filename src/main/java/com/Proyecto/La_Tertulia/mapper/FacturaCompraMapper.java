package com.Proyecto.La_Tertulia.mapper;

import org.mapstruct.Mapper;

import com.Proyecto.La_Tertulia.dto.FacturaDTO;
import com.Proyecto.La_Tertulia.model.FacturaCompra;

@Mapper(componentModel = "spring")
public interface FacturaCompraMapper {

    FacturaDTO toDTO(FacturaCompra factura);

    FacturaCompra toEntity(FacturaDTO facturaDTO);

}
