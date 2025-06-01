package com.Proyecto.La_Tertulia.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleVentaDTO {

    private Long id;
    private VentaDTO venta;
    private ProductDTO Producto;
    private String nombreProducto;
    private double precioUnitario;
    private int cantidad;
    private double subtotal;
}