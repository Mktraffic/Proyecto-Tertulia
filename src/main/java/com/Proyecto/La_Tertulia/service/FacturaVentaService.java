package com.Proyecto.La_Tertulia.service;

import com.Proyecto.La_Tertulia.dto.FacturaVentaDTO;
import com.Proyecto.La_Tertulia.mapper.FacturaVentaMapperImplement;
import com.Proyecto.La_Tertulia.model.FacturaVenta;
import com.Proyecto.La_Tertulia.model.Venta;
import com.Proyecto.La_Tertulia.repository.FacturaVentaRepository;
import com.Proyecto.La_Tertulia.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FacturaVentaService {

    @Autowired
    private FacturaVentaRepository facturaVentaRepository;

    @Autowired
    private FacturaVentaMapperImplement facturaVentaMapper;

    @Autowired
    private VentaRepository ventaRepository;

    public String addFacturaVenta(FacturaVentaDTO facturaVentaDTO) {
        String result;
        try {
            Venta venta = ventaRepository.findById(facturaVentaDTO.getVenta().getId())
                    .orElseThrow(() -> new RuntimeException("Venta no encontrada"));
            int subTotal = (int) venta.getTotalVenta();
            double iva = subTotal * 0.19;
            double totalFactura = subTotal + iva;
            facturaVentaDTO.setSubTotal(subTotal);
            facturaVentaDTO.setIva(iva);
            facturaVentaDTO.setTotalFactura(totalFactura);
            FacturaVenta facturaVenta = facturaVentaMapper.toEntity(facturaVentaDTO);
            facturaVentaRepository.save(facturaVenta);
            result = "Factura de venta registrada correctamente";
        } catch (Exception e) {
            result = "Error al registrar la factura de venta";
        }
        return result;
    }

    public List<FacturaVentaDTO> findAllFacturasVenta() {
        return facturaVentaRepository.findAll()
                .stream()
                .map(facturaVentaMapper::toDTO)
                .collect(Collectors.toList());
    }

    public FacturaVentaDTO findFacturaVentaById(Long id) {
        Optional<FacturaVenta> factura = facturaVentaRepository.findById(id);
        return factura.map(facturaVentaMapper::toDTO).orElse(null);
    }
}
