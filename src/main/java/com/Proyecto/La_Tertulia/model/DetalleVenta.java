package com.Proyecto.La_Tertulia.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "detalle_venta")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_venta", nullable = false)
    private Venta venta;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    private Product producto;

    @Column(name = "nombre_producto", nullable = false)
    private String nombreProducto;

    @Column(name = "precio_unitario", nullable = false)
    private double precioUnitario;

    @Column(name = "cantidad", nullable = false)
    private int cantidad;

    @Column(name = "subtotal", nullable = false)
    private double subtotal;
}