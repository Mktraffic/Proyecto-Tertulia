package com.Proyecto.La_Tertulia.service;

import com.Proyecto.La_Tertulia.dto.FacturaDTO;
import com.Proyecto.La_Tertulia.mapper.FacturaMapperImplement;
import com.Proyecto.La_Tertulia.model.Factura;
import com.Proyecto.La_Tertulia.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private FacturaMapperImplement facturaMapper;

    public String addFactura(FacturaDTO facturaDTO) {
        String result;
        try {
            Factura factura = facturaMapper.toEntity(facturaDTO);
            facturaRepository.save(factura);
            result = "Factura registrada correctamente";
        } catch (Exception e) {
            result = "Error al registrar la factura";
        }
        return result;
    }

    public List<FacturaDTO> findAllFacturas() {
        return facturaRepository.findAll()
                .stream()
                .map(facturaMapper::toDTO)
                .collect(Collectors.toList());
    }

    public FacturaDTO findFacturaById(Long id) {
        Optional<Factura> factura = facturaRepository.findById(id);
        return factura.map(facturaMapper::toDTO).orElse(null);
    }
}
