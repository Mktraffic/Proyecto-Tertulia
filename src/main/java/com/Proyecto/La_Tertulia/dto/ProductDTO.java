package com.Proyecto.La_Tertulia.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private long id;
    private String name;
    private String descripcion_producto;
    private String presentation;
    private int stock;
    private double price;

}
