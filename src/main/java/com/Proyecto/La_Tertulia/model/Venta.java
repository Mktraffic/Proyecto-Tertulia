package com.Proyecto.La_Tertulia.model;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "venta") 
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Venta {

    @Id
    @Column(name = "id_venta", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false, referencedColumnName = "id_producto")
    private Product producto;

    @Column(name = "nombre_producto", nullable = false)
    private String nombreProducto;

    @Column(name = "precio_producto", nullable = false)
    private double precio;

    @Column(name = "fecha_nacimiento", length = 90, nullable = false)
    private LocalDate fechaVenta;

    @Column(name = "cantidad_producto", nullable = false)
    private int cantidad;

    @OneToOne
    @JoinColumn(name = "id_vendedor", nullable = false, referencedColumnName = "id_usuario")
    private Usuario vendedor;

    @Column(name = "precio_total", nullable = false)
    private double totalVenta;

    @Column(name = "nombre_cliente", nullable = false)
    private String nombreCliente;

    @Column(name = "documento_cliente", length = 50, nullable = false)
    @Size(min = 3, max = 50)
    private Long numeroDocumentoCliente;

}
