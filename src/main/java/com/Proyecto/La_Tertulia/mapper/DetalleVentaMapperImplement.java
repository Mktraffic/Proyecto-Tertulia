package com.Proyecto.La_Tertulia.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.Proyecto.La_Tertulia.dto.DetalleVentaDTO;
import com.Proyecto.La_Tertulia.dto.VentaDTO;
import com.Proyecto.La_Tertulia.model.DetalleVenta;
import com.Proyecto.La_Tertulia.model.Product;
import com.Proyecto.La_Tertulia.model.Venta;
import com.Proyecto.La_Tertulia.repository.ProductRepository;

@Component
public class DetalleVentaMapperImplement implements DetalleVentaMapper {

    @Autowired
    private ProductRepository productoRepository;
   @Autowired
    private VentaMapper ventaMapper;
    
    

    @Override
    public DetalleVentaDTO toDTO(DetalleVenta detalleVenta) {
        if (detalleVenta == null) {
            return null;
        }

        DetalleVentaDTO dto = new DetalleVentaDTO();
        dto.setId(detalleVenta.getId());
        dto.setVenta(ventaMapper.toDTO(detalleVenta.getVenta()));
        dto.setIdProducto(detalleVenta.getProducto().getId());
        dto.setNombreProducto(detalleVenta.getNombreProducto());
        dto.setPrecioUnitario(detalleVenta.getPrecioUnitario());
        dto.setCantidad(detalleVenta.getCantidad());
        dto.setSubtotal(detalleVenta.getSubtotal());
        return dto;
    }

    @Override
    public DetalleVenta toEntity(DetalleVentaDTO dto) {
        if (dto == null) {
            return null;
        }

        Product producto = productoRepository.findById(dto.getIdProducto())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + dto.getIdProducto()));

        DetalleVenta detalle = new DetalleVenta();
         detalle.setVenta(ventaMapper.toEntity(dto.getVenta()));
        detalle.setProducto(producto);
        detalle.setNombreProducto(producto.getName());
        detalle.setPrecioUnitario(dto.getPrecioUnitario());
        detalle.setCantidad(dto.getCantidad());
        detalle.setSubtotal(dto.getPrecioUnitario() * dto.getCantidad());

        return detalle;
    }
}