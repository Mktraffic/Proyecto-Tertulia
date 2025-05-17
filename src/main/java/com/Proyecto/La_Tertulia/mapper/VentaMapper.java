package com.Proyecto.La_Tertulia.mapper;

import com.Proyecto.La_Tertulia.dto.VentaDTO;
import com.Proyecto.La_Tertulia.model.Venta;

public interface VentaMapper {

    VentaDTO toDTO(Venta venta);

    Venta toEntity(VentaDTO ventaDTO);
}