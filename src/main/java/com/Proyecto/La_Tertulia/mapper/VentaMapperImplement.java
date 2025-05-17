package com.Proyecto.La_Tertulia.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.Proyecto.La_Tertulia.dto.VentaDTO;
import com.Proyecto.La_Tertulia.dto.ProductDTO;
import com.Proyecto.La_Tertulia.dto.UsuarioDTO;
import com.Proyecto.La_Tertulia.model.Venta;
import com.Proyecto.La_Tertulia.model.Product;
import com.Proyecto.La_Tertulia.model.Usuario;

@Component
public class VentaMapperImplement implements VentaMapper {

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public VentaDTO toDTO(Venta venta) {
        VentaDTO ventaDTO = new VentaDTO();
        if (venta != null) {
            ventaDTO.setId(venta.getId());
            ventaDTO.setProducto(productMapper.toDTO(venta.getProducto()));
            ventaDTO.setNombreProducto(venta.getNombreProducto());
            ventaDTO.setPrecio(venta.getPrecio());
            ventaDTO.setFechaVenta(venta.getFechaVenta());
            ventaDTO.setCantidad(venta.getCantidad());
            ventaDTO.setVendedor(venta.getVendedor() != null ? usuarioMapper.toDTO(venta.getVendedor()) : null);
            ventaDTO.setTotalVenta(venta.getTotalVenta());
            ventaDTO.setNombreCliente(venta.getNombreCliente());
            ventaDTO.setNumeroDocumentoCliente(venta.getNumeroDocumentoCliente());

        }
        return null;
    }

    @Override
    public Venta toEntity(VentaDTO ventaDTO) {
        Venta venta = new Venta();
        if (ventaDTO != null) {
            venta.setId(ventaDTO.getId());
            venta.setProducto(productMapper.toEntity(ventaDTO.getProducto()));
            venta.setNombreProducto(ventaDTO.getNombreProducto());
            venta.setPrecio(ventaDTO.getPrecio());
            venta.setFechaVenta(ventaDTO.getFechaVenta());
            venta.setCantidad(ventaDTO.getCantidad());
            venta.setVendedor(ventaDTO.getVendedor() != null ? usuarioMapper.toEntity(ventaDTO.getVendedor()) : null);
            venta.setTotalVenta(ventaDTO.getTotalVenta());
            venta.setNombreCliente(ventaDTO.getNombreCliente());
            venta.setNumeroDocumentoCliente(ventaDTO.getNumeroDocumentoCliente());
        }
        return null;
    }
}
