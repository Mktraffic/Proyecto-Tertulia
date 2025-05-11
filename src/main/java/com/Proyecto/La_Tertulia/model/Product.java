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
public class Product {

    @Id
    @Column(name = "id_producto", length = 50, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nombre_producto", nullable = false)
    private String name;

    @Column(name = "descripcion_producto", nullable = false)
    private String descripcion_producto;

    @Column(name = "presentacion_producto", nullable = false)
    private String presentation;

    @Column(name = "precio_producto", nullable = false)
    private double price;

}
