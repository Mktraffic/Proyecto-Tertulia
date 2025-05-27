package com.Proyecto.La_Tertulia.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.Proyecto.La_Tertulia.dto.CompraDTO;
import com.Proyecto.La_Tertulia.model.Compra;

@Component
public class CompraMapperImplement implements CompraMapper {

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private DetalleCompraMapperImplemet detalleCompraMapper;

    public CompraDTO toDTO(Compra compra) {
        if (compra == null) return null;
        CompraDTO dto = new CompraDTO();
        dto.setId(compra.getId());
        dto.setFechaCompra(compra.getFechaCompra());
        dto.setVendedor(usuarioMapper.toDTO(compra.getVendedor()));
        dto.setNombreProveedor(compra.getNombreProveedor());
        dto.setTotalVenta(compra.getTotalVenta());
        if (compra.getDetalles() != null) {
            dto.setDetalles(
                compra.getDetalles().stream()
                    .map(detalleCompraMapper::toDTO)
                    .toList()
            );
        }
        return dto;
    }

    public Compra toEntity(CompraDTO dto) {
        if (dto == null) return null;
        Compra compra = new Compra();
        compra.setId(dto.getId());
        compra.setFechaCompra(dto.getFechaCompra());
        compra.setVendedor(usuarioMapper.toEntity(dto.getVendedor()));
        compra.setNombreProveedor(dto.getNombreProveedor());
        compra.setTotalVenta(dto.getTotalVenta());
        if (dto.getDetalles() != null) {
            compra.setDetalles(
                dto.getDetalles().stream()
                    .map(detalleCompraMapper::toEntity)
                    .toList()
            );
        }
        return compra;
    }
}
