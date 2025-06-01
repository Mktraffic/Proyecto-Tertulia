package com.Proyecto.La_Tertulia.service;
import com.Proyecto.La_Tertulia.dto.VentaDTO;
import com.Proyecto.La_Tertulia.mapper.VentaMapper;
import com.Proyecto.La_Tertulia.model.DetalleVenta;
import com.Proyecto.La_Tertulia.model.Product;
import com.Proyecto.La_Tertulia.model.Usuario;
import com.Proyecto.La_Tertulia.model.Venta;
import com.Proyecto.La_Tertulia.repository.ProductRepository;
import com.Proyecto.La_Tertulia.repository.UsuarioRepository;
import com.Proyecto.La_Tertulia.repository.VentaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private VentaMapper ventaMapper;

    public VentaDTO registrarVenta(VentaDTO ventaDTO) {
        Usuario vendedor = usuarioRepository.findById(ventaDTO.getVendedor().getId())
                .orElseThrow(() -> new RuntimeException("Vendedor no encontrado"));

        Venta venta = new Venta();
        venta.setFechaVenta(LocalDate.now());
        venta.setVendedor(vendedor);
        venta.setTipoDocumentoCliente(ventaDTO.getTipoDocumentoCliente());
        venta.setNumeroDocumentoCliente(ventaDTO.getNumeroDocumentoCliente());

        // Mapeo manual de los detalles
        List<DetalleVenta> detalles = ventaDTO.getDetalles().stream().map(dto -> {
            DetalleVenta detalle = new DetalleVenta();
            Product producto = productRepository.findById(dto.getIdProducto().getId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + dto.getIdProducto()));
            detalle.setProducto(producto);
            detalle.setNombreProducto(dto.getNombreProducto());
            detalle.setPrecioUnitario(dto.getPrecioUnitario());
            detalle.setCantidad(dto.getCantidad());
            detalle.setSubtotal(dto.getPrecioUnitario() * dto.getCantidad());
            detalle.setVenta(venta); // Relación bidireccional
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
