package com.Proyecto.La_Tertulia.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.Proyecto.La_Tertulia.dto.VentaDTO;
import com.Proyecto.La_Tertulia.dto.DetalleVentaDTO;
import com.Proyecto.La_Tertulia.model.Venta;
import com.Proyecto.La_Tertulia.model.DetalleVenta;
import com.Proyecto.La_Tertulia.repository.UsuarioRepository;

@Component
public class VentaMapperImplement implements VentaMapper {

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private DetalleVentaMapper detalleVentaMapper;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public VentaDTO toDTO(Venta venta) {
        if (venta == null) {
            return null;
        }

        VentaDTO dto = new VentaDTO();
        dto.setId(venta.getId());
        dto.setFechaVenta(venta.getFechaVenta());
        dto.setNumeroDocumentoCliente(venta.getNumeroDocumentoCliente());
        dto.setNumeroDocumentoCliente(venta.getNumeroDocumentoCliente());
        dto.setTotalVenta(venta.getTotalVenta());
        dto.setVendedor(usuarioMapper.toDTO(venta.getVendedor()));

        List<DetalleVentaDTO> detalles = venta.getDetalles().stream()
                .map(detalleVentaMapper::toDTO)
                .collect(Collectors.toList());
        dto.setDetalles(detalles);

        return dto;
    }

    @Override
    public Venta toEntity(VentaDTO dto) {
        if (dto == null) {
            return null;
        }

        Venta venta = new Venta();
        venta.setId(dto.getId());
        venta.setFechaVenta(dto.getFechaVenta());
        venta.setTipoDocumentoCliente(dto.getTipoDocumentoCliente());
        venta.setNumeroDocumentoCliente(dto.getNumeroDocumentoCliente());
        venta.setTotalVenta(dto.getTotalVenta());

        // Se asume que el vendedor ya está creado
        venta.setVendedor(usuarioRepository.findById(dto.getVendedor().getId())
                .orElseThrow(() -> new RuntimeException("Vendedor no encontrado")));

        List<DetalleVenta> detalles = dto.getDetalles().stream()
                .map(detalleVentaDTO -> {
                    DetalleVenta detalle = detalleVentaMapper.toEntity(detalleVentaDTO);
                    detalle.setVenta(venta); // importante para la relación bidireccional
                    return detalle;
                })
                .collect(Collectors.toList());

        venta.setDetalles(detalles);
        return venta;
    }
}