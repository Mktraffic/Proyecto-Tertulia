package com.Proyecto.La_Tertulia.mapper;

import com.Proyecto.La_Tertulia.dto.DetalleVentaDTO;
import com.Proyecto.La_Tertulia.model.DetalleVenta;

public interface DetalleVentaMapper {

    DetalleVentaDTO toDTO(DetalleVenta detalleVenta);

    DetalleVenta toEntity(DetalleVentaDTO dto);
}