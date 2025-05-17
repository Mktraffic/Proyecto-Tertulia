package com.Proyecto.La_Tertulia.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VentaDTO {

    private Long id;
    private LocalDate fechaVenta;
    private UsuarioDTO vendedor;
    private String nombreCliente;
    private Long numeroDocumentoCliente;
    private double totalVenta;
    private List<DetalleVentaDTO> detalles;
}