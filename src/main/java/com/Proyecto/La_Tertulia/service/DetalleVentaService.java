package com.Proyecto.La_Tertulia.service;

import com.Proyecto.La_Tertulia.dto.DetalleVentaDTO;

import com.Proyecto.La_Tertulia.mapper.DetalleVentaMapper;


import com.Proyecto.La_Tertulia.model.DetalleVenta;
import com.Proyecto.La_Tertulia.model.Product;
import com.Proyecto.La_Tertulia.model.Venta;
import com.Proyecto.La_Tertulia.repository.DetalleVentaRepository;
import com.Proyecto.La_Tertulia.repository.ProductRepository;
import com.Proyecto.La_Tertulia.repository.VentaRepository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class DetalleVentaService {

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;
    @Autowired
    private ProductRepository productRepository;
      @Autowired
    private VentaRepository ventaRepository;


 

    @Autowired
    private DetalleVentaMapper detalleVentaMapper;

    public DetalleVentaDTO registrarDetalleVenta(DetalleVentaDTO detalleVentaDTO) {

        Product product = productRepository.findById(detalleVentaDTO.getIdProducto())
                .orElseThrow(() -> new RuntimeException("producto no encontrado"));
        if (product.getStock() < detalleVentaDTO.getCantidad()) {
            throw new RuntimeException("Stock insuficiente para  vender el producto: " + product.getName());
        }
        
        product.setStock(product.getStock() - detalleVentaDTO.getCantidad());
        productRepository.save(product);
        
        DetalleVenta detalleVenta = detalleVentaMapper.toEntity(detalleVentaDTO);
        DetalleVenta detalleVentaGuardado = detalleVentaRepository.save(detalleVenta);
        return detalleVentaMapper.toDTO(detalleVentaGuardado);

    }
    // Obtener detalles venta por ID de venta 
public List<DetalleVentaDTO> obtenerDetallesPorIdVenta(Long idVenta) {
    
     Venta venta = ventaRepository.findById(idVenta).orElseThrow(() -> new RuntimeException("venta no encontrada con ID: " + idVenta));
    List<DetalleVenta> detalles = venta.getDetalles();
    return detalles.stream()
            .map(detalleVentaMapper::toDTO)
            .collect(Collectors.toList());
}
    
public List<DetalleVentaDTO> listarDetallesVenta() {
    List<DetalleVenta> detalles = detalleVentaRepository.findAll();
    return detalles.stream()
            .map(detalleVentaMapper::toDTO)
            .collect(Collectors.toList());
}


public DetalleVentaDTO obtenerDetallePorId(Long id) {
    DetalleVenta detalle = detalleVentaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Detalle de venta no encontrado con ID: " + id));
    return detalleVentaMapper.toDTO(detalle);
}




public DetalleVentaDTO actualizarDetalle(Long id, DetalleVentaDTO nuevoDetalle) {
    DetalleVenta existente = detalleVentaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Detalle de venta no encontrado con ID: " + id));

    
    int diferencia = nuevoDetalle.getCantidad() - existente.getCantidad();

    Product producto = existente.getProducto();
    if (producto.getStock() < diferencia) {
        throw new RuntimeException("Stock insuficiente para actualizar el detalle");
    }

    producto.setStock(producto.getStock() - diferencia);
    productRepository.save(producto);

    // Actualizar campos del detalle
    existente.setCantidad(nuevoDetalle.getCantidad());
    existente.setPrecioUnitario(nuevoDetalle.getPrecioUnitario());
    existente.setSubtotal(nuevoDetalle.getSubtotal());

    return detalleVentaMapper.toDTO(detalleVentaRepository.save(existente));
}

}
