package com.Proyecto.La_Tertulia.service;

import com.Proyecto.La_Tertulia.dto.CompraDTO;
import com.Proyecto.La_Tertulia.mapper.CompraMapperImplement;
import com.Proyecto.La_Tertulia.model.Compra;
import com.Proyecto.La_Tertulia.repository.CompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompraService {

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private CompraMapperImplement compraMapper;

    public String addCompra(CompraDTO compraDTO) {
        String result;
        try {
            Compra compra = compraMapper.toEntity(compraDTO);
            compraRepository.save(compra);
            result = "Compra registrada correctamente";
        } catch (Exception e) {
            result = "Error al registrar la compra";
        }
        return result;
    }

    public List<CompraDTO> findAllCompras() {
        return compraRepository.findAll()
                .stream()
                .map(compraMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<CompraDTO> findComprasBetweenDates(LocalDate fechaInicio, LocalDate fechaFin) {
        return compraRepository.findAll().stream()
                .filter(c -> c.getFechaCompra() != null &&
                             !c.getFechaCompra().isBefore(fechaInicio) &&
                             !c.getFechaCompra().isAfter(fechaFin))
                .map(compraMapper::toDTO)
                .collect(Collectors.toList());
    }
}
