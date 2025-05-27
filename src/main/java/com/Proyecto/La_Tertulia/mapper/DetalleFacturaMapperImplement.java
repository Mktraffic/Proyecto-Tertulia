package com.Proyecto.La_Tertulia.mapper;

import com.Proyecto.La_Tertulia.dto.DetalleFacturaDTO;
import com.Proyecto.La_Tertulia.model.DetalleFactura;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DetalleFacturaMapperImplement implements DetalleFacturaMapper {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public DetalleFacturaDTO toDTO(DetalleFactura detalle) {
        if (detalle == null) return null;
        DetalleFacturaDTO dto = new DetalleFacturaDTO();
        dto.setId(detalle.getId());
        dto.setTipoDocumento(detalle.getTipoDocumento());
        dto.setNumeroDocumento(detalle.getNumeroDocumento());
        dto.setProducto(productMapper.toDTO(detalle.getProducto()));
        return dto;
    }

    @Override
    public DetalleFactura toEntity(DetalleFacturaDTO dto) {
        if (dto == null) return null;
        DetalleFactura detalle = new DetalleFactura();
        detalle.setId(dto.getId());
        detalle.setTipoDocumento(dto.getTipoDocumento());
        detalle.setNumeroDocumento(dto.getNumeroDocumento());
        detalle.setProducto(productMapper.toEntity(dto.getProducto()));
        return detalle;
    }
}
