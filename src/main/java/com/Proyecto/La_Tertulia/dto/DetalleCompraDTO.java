package com.Proyecto.La_Tertulia.dto;

import com.Proyecto.La_Tertulia.model.Compra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleCompraDTO {

    private Long id;
    private Compra compra;
    private ProductDTO producto;
    private String nombreProducto;
    private double precioUnitario;
    private int cantidad;
    private double subtotal;

}
