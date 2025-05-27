package com.Proyecto.La_Tertulia.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "compra")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_venta")
    private Long id;

    @Column(name = "fecha_compra", nullable = false)
    private LocalDate fechaCompra;

    @OneToOne
    @JoinColumn(name = "id_administrador", nullable = false, referencedColumnName = "id_usuario")
    private Usuario vendedor;

    @Column(name = "nombre_proveedor", nullable = false)
    private String nombreProveedor;

    @Column(name = "precio_total", nullable = false)
    private double totalVenta;

    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleCompra> detalles;
}