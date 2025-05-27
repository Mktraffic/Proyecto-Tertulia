package com.Proyecto.La_Tertulia.mapper;

import org.mapstruct.Mapper;

import com.Proyecto.La_Tertulia.dto.FacturaDTO;
import com.Proyecto.La_Tertulia.model.Factura;

@Mapper(componentModel = "spring")
public interface FacturaMapper {

    FacturaDTO toDTO(Factura factura);

    Factura toEntity(FacturaDTO facturaDTO);

}
