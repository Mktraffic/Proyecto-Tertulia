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
    private ProductDTO producto;
    private String nombreProducto;
    private double precio;
    private LocalDate fechaVenta;
    private int cantidad;
    private UsuarioDTO vendedor;
    private double totalVenta;
    private String nombreCliente;
    private Long numeroDocumentoCliente;

}
