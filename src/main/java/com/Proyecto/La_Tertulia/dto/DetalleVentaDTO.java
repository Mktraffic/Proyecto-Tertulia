package com.Proyecto.La_Tertulia.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleVentaDTO {

    private Long id;
<<<<<<< Updated upstream
    private VentaDTO venta;
    private ProductDTO idProducto;
=======
    private Long idProducto;
    private String tipoProducto;
>>>>>>> Stashed changes
    private String nombreProducto;
    private String presentacion;
    private double precioUnitario;
    private int cantidad;
    private double subtotal;
}