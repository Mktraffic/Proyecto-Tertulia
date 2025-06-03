package com.Proyecto.La_Tertulia.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "venta")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_venta")
    private Long id;

    @Column(name = "fecha_venta", nullable = false)
    private LocalDate fechaVenta;

    @ManyToOne
    @JoinColumn(name = "id_vendedor", nullable = false, referencedColumnName = "id_usuario")
    private Usuario vendedor;

    @Column(name = "nombre_cliente", nullable = false)
    private String tipoDocumentoCliente;

    @Column(name = "documento_cliente", length = 50, nullable = false)
    private Long numeroDocumentoCliente;

    @Column(name = "precio_total", nullable = false)
    private double totalVenta;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<DetalleVenta> detalles;
}