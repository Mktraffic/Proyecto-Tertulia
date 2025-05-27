package com.Proyecto.La_Tertulia.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompraDTO {

    private Long id;
    private LocalDate fechaCompra;
    private UsuarioDTO vendedor;
    private String nombreProveedor;
    private double totalVenta;
    private List<DetalleCompraDTO> detalles;

}
