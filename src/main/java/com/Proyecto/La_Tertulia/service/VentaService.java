package com.Proyecto.La_Tertulia.service;

import com.Proyecto.La_Tertulia.dto.DetalleVentaDTO;
import com.Proyecto.La_Tertulia.dto.UsuarioDTO;
import com.Proyecto.La_Tertulia.dto.VentaDTO;
import com.Proyecto.La_Tertulia.mapper.DetalleVentaMapper;
import com.Proyecto.La_Tertulia.mapper.UsuarioMapper;
import com.Proyecto.La_Tertulia.mapper.VentaMapper;
import com.Proyecto.La_Tertulia.model.DetalleVenta;
import com.Proyecto.La_Tertulia.model.Product;
import com.Proyecto.La_Tertulia.model.Usuario;
import com.Proyecto.La_Tertulia.model.Venta;
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
    private DetalleVentaMapper detalleVentaMapper;

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

        List<DetalleVenta> detalles = ventaDTO.getDetalles().stream().map(dto -> {
            DetalleVenta detalle = detalleVentaMapper.toEntity(dto);
            detalle.setVenta(venta);
            detalle.setSubtotal(detalle.getPrecioUnitario() * detalle.getCantidad());
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
//la vista debe pasar las fechas en el formato YYYY-MM-DD
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
