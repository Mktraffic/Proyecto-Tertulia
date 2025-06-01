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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "factura_venta")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacturaVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_factura")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_vendedor", nullable = false, referencedColumnName = "id_usuario")
    private Usuario usuario;

    @Column(name = "tipo_documentoCliente", nullable = false)
    private String tipoDocumento;

    @Column(name = "numero_documentoCliente", length = 50)
    private Long numeroDocumento;

    @Column(name = "fecha_factura", nullable = false)
    private LocalDate fechaFactura;

    @OneToOne
    @JoinColumn(name = "id_venta", nullable = false, referencedColumnName = "id_venta")
    private Venta venta;

    @Column(name = "sub_total", nullable = false)
    private int subTotal;

    @Column(name = "iva", nullable = false)
    private double iva;

    @Column(name = "total_factura", nullable = false)
    private double totalFactura;

}
