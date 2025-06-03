package com.Proyecto.La_Tertulia.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.Proyecto.La_Tertulia.dto.CompraDTO;
import com.Proyecto.La_Tertulia.dto.DetalleCompraDTO;
import com.Proyecto.La_Tertulia.model.Compra;
import com.Proyecto.La_Tertulia.model.DetalleCompra;
import com.Proyecto.La_Tertulia.model.Product;
import com.Proyecto.La_Tertulia.repository.UsuarioRepository;
import com.Proyecto.La_Tertulia.repository.ProductRepository;

@Component
public class CompraMapperImplement implements CompraMapper {

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public CompraDTO toDTO(Compra compra) {
        if (compra == null) {
            return null;
        }

        CompraDTO dto = new CompraDTO();
        dto.setId(compra.getId());
        dto.setFechaCompra(compra.getFechaCompra());
        dto.setVendedor(usuarioMapper.toDTO(compra.getVendedor()));
        dto.setNombreProveedor(compra.getNombreProveedor());
        dto.setTotalVenta(compra.getTotalVenta());




        List<DetalleCompraDTO> detalles = compra.getDetalles().stream().map(detalle -> {
            DetalleCompraDTO detalleDTO = new DetalleCompraDTO();
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
    public Compra toEntity(CompraDTO dto) {
        if (dto == null) {
            return null;
        }

        Compra compra = new Compra();
        compra.setId(dto.getId());
        compra.setFechaCompra(dto.getFechaCompra());
      
        compra.setNombreProveedor(dto.getNombreProveedor());
        compra.setTotalVenta(dto.getTotalVenta());

        // Se asume que el comprador ya está creado
        compra.setVendedor(usuarioRepository.findById(dto.getVendedor().getId())
                .orElseThrow(() -> new RuntimeException("Comprador no encontrado")));

        // Mapeo manual de detalles
        List<DetalleCompra> detalles = dto.getDetalles().stream().map(detalleDTO -> {
            DetalleCompra detalle = new DetalleCompra();
            detalle.setCompra(compra); // Relación bidireccional
            Product producto = productRepository.findById(productMapper.toEntity(detalleDTO.getProducto()).getId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + detalleDTO.getProducto()));
            detalle.setProducto(producto);
            detalle.setNombreProducto(detalleDTO.getNombreProducto());
            detalle.setPrecioUnitario(detalleDTO.getPrecioUnitario());
            detalle.setCantidad(detalleDTO.getCantidad());
            detalle.setSubtotal(detalleDTO.getPrecioUnitario() * detalleDTO.getCantidad());
            return detalle;
        }).collect(Collectors.toList());

        compra.setDetalles(detalles);
        return compra;
    }
}
