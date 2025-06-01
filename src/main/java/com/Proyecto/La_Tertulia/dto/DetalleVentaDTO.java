package com.Proyecto.La_Tertulia.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleVentaDTO {

    private Long id;
    private VentaDTO venta;
    private ProductDTO producto;
    private String nombreProducto;
    private double precioUnitario;
    private int cantidad;
    private double subtotal;
}