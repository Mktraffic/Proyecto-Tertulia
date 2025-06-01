package com.Proyecto.La_Tertulia.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.Proyecto.La_Tertulia.dto.VentaDTO;
import com.Proyecto.La_Tertulia.dto.DetalleVentaDTO;
import com.Proyecto.La_Tertulia.model.Venta;
import com.Proyecto.La_Tertulia.model.DetalleVenta;
import com.Proyecto.La_Tertulia.model.Product;
import com.Proyecto.La_Tertulia.repository.UsuarioRepository;
import com.Proyecto.La_Tertulia.repository.ProductRepository;

@Component
public class VentaMapperImplement implements VentaMapper {

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public VentaDTO toDTO(Venta venta) {
        if (venta == null) {
            return null;
        }

        VentaDTO dto = new VentaDTO();
        dto.setId(venta.getId());
        dto.setFechaVenta(venta.getFechaVenta());
        dto.setTipoDocumentoCliente(venta.getTipoDocumentoCliente());
        dto.setNumeroDocumentoCliente(venta.getNumeroDocumentoCliente());
        dto.setTotalVenta(venta.getTotalVenta());
        dto.setVendedor(usuarioMapper.toDTO(venta.getVendedor()));
        List<DetalleVentaDTO> detalles = venta.getDetalles().stream().map(detalle -> {
            DetalleVentaDTO detalleDTO = new DetalleVentaDTO();
            detalleDTO.setId(detalle.getId());
            detalleDTO.setProducto(productMapper.toDTO(detalle.getProducto()));
            detalleDTO.setNombreProducto(detalle.getNombreProducto());
            detalleDTO.setPrecioUnitario(detalle.getPrecioUnitario());
            detalleDTO.setCantidad(detalle.getCantidad());
            detalleDTO.setSubtotal(detalle.getSubtotal());
            return detalleDTO;
        }).collect(Collectors.toList());
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
        venta.setVendedor(usuarioRepository.findById(dto.getVendedor().getId())
                .orElseThrow(() -> new RuntimeException("Vendedor no encontrado")));
        List<DetalleVenta> detalles = dto.getDetalles().stream().map(detalleDTO -> {
            DetalleVenta detalle = new DetalleVenta();
            detalle.setVenta(venta);
            Product producto = productRepository.findById(productMapper.toEntity(detalleDTO.getProducto()).getId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + detalleDTO.getProducto()));
            detalle.setProducto(producto);
            detalle.setNombreProducto(detalleDTO.getNombreProducto());
            detalle.setPrecioUnitario(detalleDTO.getPrecioUnitario());
            detalle.setCantidad(detalleDTO.getCantidad());
            detalle.setSubtotal(detalleDTO.getPrecioUnitario() * detalleDTO.getCantidad());
            return detalle;
        }).collect(Collectors.toList());

        venta.setDetalles(detalles);
        return venta;
    }
}