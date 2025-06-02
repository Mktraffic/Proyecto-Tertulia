package com.Proyecto.La_Tertulia.service;

import com.Proyecto.La_Tertulia.dto.DetalleVentaDTO;
import com.Proyecto.La_Tertulia.dto.VentaDTO;
import com.Proyecto.La_Tertulia.mapper.VentaMapper;
import com.Proyecto.La_Tertulia.model.DetalleVenta;
import com.Proyecto.La_Tertulia.model.Product;
import com.Proyecto.La_Tertulia.model.Usuario;
import com.Proyecto.La_Tertulia.model.Venta;
import com.Proyecto.La_Tertulia.repository.ProductRepository;
import com.Proyecto.La_Tertulia.repository.UsuarioRepository;
import com.Proyecto.La_Tertulia.repository.VentaRepository;

import lombok.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
public class VentaService {

  @Autowired
  private VentaRepository ventaRepository;

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private VentaMapper ventaMapper;

  private ArrayList<DetalleVentaDTO> saleDetailsList;

  public VentaService() {
    this.saleDetailsList = new ArrayList<>();
  }

  public VentaDTO registrarVenta(VentaDTO ventaDTO) {
    Usuario vendedor = usuarioRepository.findById(ventaDTO.getVendedor().getId())
        .orElseThrow(() -> new RuntimeException("Vendedor no encontrado"));

    Venta venta = new Venta();
    venta.setFechaVenta(LocalDate.now());
    venta.setVendedor(vendedor);
    venta.setTipoDocumentoCliente(ventaDTO.getTipoDocumentoCliente());
    venta.setNumeroDocumentoCliente(ventaDTO.getNumeroDocumentoCliente());
    List<DetalleVenta> detalles = ventaDTO.getDetalles().stream().map(dto -> {
      DetalleVenta detalle = new DetalleVenta();
      Product producto = productRepository.findById(dto.getProducto().getId())
          .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + dto.getProducto()));
      detalle.setProducto(producto);
      detalle.setNombreProducto(dto.getNombreProducto());
      detalle.setPrecioUnitario(dto.getPrecioUnitario());
      detalle.setCantidad(dto.getCantidad());
      detalle.setSubtotal(dto.getPrecioUnitario() * dto.getCantidad());
      detalle.setVenta(venta);
      if (producto.getStock() < dto.getCantidad()) {
        throw new RuntimeException("Stock insuficiente para vender el producto: " + producto.getName());
      }
      producto.setStock(producto.getStock() - dto.getCantidad());
      productRepository.save(producto);
      return detalle;
    }).collect(Collectors.toList());

    double totalVenta = detalles.stream()
        .mapToDouble(DetalleVenta::getSubtotal)
        .sum();

    venta.setTotalVenta(totalVenta);
    venta.setDetalles(detalles);

    Venta ventaGuardada = ventaRepository.save(venta);

    return ventaMapper.toDTO(ventaGuardada);
  }

  public void guardarDetallesVenta(DetalleVentaDTO detallesVenta) {
    saleDetailsList.add(detallesVenta);
  }

  public void limpiarDetallesVenta() {
    saleDetailsList.clear();
  }

  // La vista debe pasar las fechas en el formato YYYY-MM-DD
  public List<VentaDTO> obtenerVentasEntreFechas(LocalDate fechaInicio, LocalDate fechaFin) {
    List<Venta> ventas = ventaRepository.findByFechaVentaBetween(fechaInicio, fechaFin);
    return ventas.stream()
        .map(ventaMapper::toDTO)
        .collect(Collectors.toList());
  }

  public List<VentaDTO> findAllSales() {
    List<Venta> ventas = ventaRepository.findAll();
    return ventas.stream()
        .map(ventaMapper::toDTO)
        .collect(Collectors.toList());
  }
}
