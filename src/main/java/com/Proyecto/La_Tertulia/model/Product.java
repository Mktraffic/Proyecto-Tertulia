package com.Proyecto.La_Tertulia.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "producto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class  Product {

    @Id
    @Column(name = "id_producto", length = 50, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_producto", nullable = false)
    private String name;

    @Column(name = "tipo_producto", nullable = false)
    private String type;

    @Column(name = "descripcion_producto", nullable = false)
    private String description;

    @Column(name = "presentacion_producto", nullable = false)
    private String presentation;
    
    @Column(name = "grados_alcohol", nullable = false)
    private String gradosAlcohol;

    @Column(name = "stock_producto", nullable = false)
    private int stock;

    @Column(name = "precio_producto", nullable = false)
    private int price;


}
