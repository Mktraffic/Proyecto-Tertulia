package com.Proyecto.La_Tertulia.mapper;

import com.Proyecto.La_Tertulia.dto.FacturaDTO;
import com.Proyecto.La_Tertulia.model.FacturaCompra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class FacturaCompraMapperImplement implements FacturaCompraMapper {

    @Autowired
    private UsuarioMapper usuarioMapper;
     @Autowired
    private CompraMapper compraMapper;

    

    @Override
    public FacturaDTO toDTO(FacturaCompra factura) {
        if (factura == null) return null;
        FacturaDTO dto = new FacturaDTO();
        dto.setId(factura.getId());
        dto.setUsuario(usuarioMapper.toDTO(factura.getUsuario()));
        dto.setCompra(compraMapper.toDTO(factura.getCompra()));
        dto.setTipoDocumento(factura.getTipoDocumento());
        dto.setNumeroDocumento(factura.getNumeroDocumento());
        dto.setFechaFactura(factura.getFechaFactura());
        dto.setSubTotal(factura.getSubTotal());
        dto.setIva(factura.getIva());
        dto.setTotalFactura(factura.getTotalFactura());
        return dto;
    }

    @Override
    public FacturaCompra toEntity(FacturaDTO dto) {
        if (dto == null) return null;
        FacturaCompra factura = new FacturaCompra();
        factura.setId(dto.getId());
        factura.setUsuario(usuarioMapper.toEntity(dto.getUsuario()));
        factura.setCompra(compraMapper.toEntity(dto.getCompra()));
        factura.setTipoDocumento(dto.getTipoDocumento());
        factura.setNumeroDocumento(dto.getNumeroDocumento());
        factura.setFechaFactura(dto.getFechaFactura());
        factura.setSubTotal(dto.getSubTotal());
        factura.setIva(dto.getIva());
        factura.setTotalFactura(dto.getTotalFactura());
        return factura;
    }
}
