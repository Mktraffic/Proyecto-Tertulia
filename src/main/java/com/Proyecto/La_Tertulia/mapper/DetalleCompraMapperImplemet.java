package com.Proyecto.La_Tertulia.mapper;

import com.Proyecto.La_Tertulia.dto.DetalleCompraDTO;
import com.Proyecto.La_Tertulia.model.DetalleCompra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DetalleCompraMapperImplemet implements DetalleCompraMapper {

    @Autowired
    private ProductMapperImplement productMapper;

    public DetalleCompraDTO toDTO(DetalleCompra detalle) {
        if (detalle == null) return null;
        DetalleCompraDTO dto = new DetalleCompraDTO();
        dto.setId(detalle.getId());
        dto.setCompra(detalle.getCompra());
        dto.setProducto(productMapper.toDTO(detalle.getProducto()));
        dto.setNombreProducto(detalle.getNombreProducto());
        dto.setPrecioUnitario(detalle.getPrecioUnitario());
        dto.setCantidad(detalle.getCantidad());
        dto.setSubtotal(detalle.getSubtotal());
        return dto;
    }

    public DetalleCompra toEntity(DetalleCompraDTO dto) {
        if (dto == null) return null;
        DetalleCompra detalle = new DetalleCompra();
        detalle.setId(dto.getId());
        detalle.setCompra(dto.getCompra());
        detalle.setProducto(productMapper.toEntity(dto.getProducto()));
        detalle.setNombreProducto(dto.getNombreProducto());
        detalle.setPrecioUnitario(dto.getPrecioUnitario());
        detalle.setCantidad(dto.getCantidad());
        detalle.setSubtotal(dto.getSubtotal());
        return detalle;
    }
}
