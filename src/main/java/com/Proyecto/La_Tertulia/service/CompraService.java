package com.Proyecto.La_Tertulia.service;

import com.Proyecto.La_Tertulia.dto.CompraDTO;
import com.Proyecto.La_Tertulia.dto.DetalleCompraDTO;
import com.Proyecto.La_Tertulia.dto.VentaDTO;
import com.Proyecto.La_Tertulia.mapper.CompraMapper;
import com.Proyecto.La_Tertulia.model.DetalleCompra;
import com.Proyecto.La_Tertulia.model.Product;
import com.Proyecto.La_Tertulia.model.Usuario;
import com.Proyecto.La_Tertulia.model.Venta;
import com.Proyecto.La_Tertulia.model.Compra;
import com.Proyecto.La_Tertulia.repository.ProductRepository;
import com.Proyecto.La_Tertulia.repository.UsuarioRepository;

import lombok.Data;

import com.Proyecto.La_Tertulia.repository.CompraRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
public class CompraService {

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CompraMapper compraMapper;

    private ArrayList<DetalleCompraDTO> buyDetailsList;
    public CompraService() {
        this.buyDetailsList = new ArrayList<>();
    }

    public CompraDTO registrarCompra(CompraDTO compraDTO) {
        Usuario vendedor = usuarioRepository.findById(compraDTO.getVendedor().getId())
                .orElseThrow(() -> new RuntimeException("Comprador no encontrado"));

        Compra compra = new Compra();
        compra.setFechaCompra(LocalDate.now());
        compra.setVendedor(vendedor);
        compra.setNombreProveedor(compraDTO.getNombreProveedor());
        compra.setTotalVenta(compraDTO.getTotalVenta());

        // Mapeo manual de los detalles
        List<DetalleCompra> detalles = compraDTO.getDetalles().stream().map(dto -> {
            DetalleCompra detalle = new DetalleCompra();
            Product producto = productRepository.findById(dto.getProducto().getId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + dto.getProducto()));
            detalle.setProducto(producto);
            detalle.setNombreProducto(dto.getNombreProducto());
            detalle.setPrecioUnitario(dto.getPrecioUnitario());
            detalle.setCantidad(dto.getCantidad());
            detalle.setSubtotal(dto.getPrecioUnitario() * dto.getCantidad());
            detalle.setCompra(compra); // Relación bidireccional

            // Actualizar stock del producto
            producto.setStock(producto.getStock() + dto.getCantidad());
            productRepository.save(producto);

            return detalle;
        }).collect(Collectors.toList());

        double totalCompra = detalles.stream()
                .mapToDouble(DetalleCompra::getSubtotal)
                .sum();

        compra.setTotalVenta(totalCompra);
        compra.setDetalles(detalles);

        Compra compraGuardada = compraRepository.save(compra);

        return compraMapper.toDTO(compraGuardada);
    }

    public List<CompraDTO> obtenerComprasEntreFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        List<Compra> compras = compraRepository.findByFechaCompraBetween(fechaInicio, fechaFin);
        return compras.stream()
                .map(compraMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<CompraDTO> findAllCompras() {
        List<Compra> compras = compraRepository.findAll();
        return compras.stream()
                .map(compraMapper::toDTO)
                .collect(Collectors.toList());
    }
    public void guardarDetallesCompra(DetalleCompraDTO detallesCompra) {
        buyDetailsList.add(detallesCompra);
    }

    public void limpiarDetallesCompra() {
        buyDetailsList.clear();
    }

  public List<CompraDTO> findAllBuys() {
    List<Compra> compras = compraRepository.findAll();
    return compras.stream()
        .map(compraMapper::toDTO)
        .collect(Collectors.toList());
  }
}
