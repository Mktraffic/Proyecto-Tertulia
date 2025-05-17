package com.Proyecto.La_Tertulia.mapper;

import org.mapstruct.Mapper;
import com.Proyecto.La_Tertulia.dto.VentaDTO;
import com.Proyecto.La_Tertulia.model.Venta;

@Mapper(componentModel = "spring", uses = {ProductMapper.class, UsuarioMapper.class}) 
public interface VentaMapper {

    VentaDTO toDTO(Venta venta);
    Venta toEntity(VentaDTO ventaDTO);
     
}