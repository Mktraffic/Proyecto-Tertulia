package com.Proyecto.La_Tertulia.mapper;

import org.mapstruct.Mapper;

import com.Proyecto.La_Tertulia.dto.FacturaVentaDTO;
import com.Proyecto.La_Tertulia.model.FacturaVenta;


@Mapper(componentModel = "spring")
public interface FacturaVentaMapper {

    FacturaVentaDTO toDTO(FacturaVenta factura);

    FacturaVenta toEntity(FacturaVentaDTO facturaDTO);

}
