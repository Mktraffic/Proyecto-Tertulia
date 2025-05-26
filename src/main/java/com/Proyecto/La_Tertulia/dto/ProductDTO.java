package com.Proyecto.La_Tertulia.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private String type;
    private String description;
    private String presentation;
    private String gradosAlcohol;
    private int stock;
    private int price;
}
