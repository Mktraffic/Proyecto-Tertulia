package com.Proyecto.La_Tertulia.dto;

import com.Proyecto.La_Tertulia.model.Venta;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleVentaDTO {

    private Long id;
    private VentaDTO venta;
    private Long idProducto;
    private String nombreProducto;
    private double precioUnitario;
    private int cantidad;
    private double subtotal;
}