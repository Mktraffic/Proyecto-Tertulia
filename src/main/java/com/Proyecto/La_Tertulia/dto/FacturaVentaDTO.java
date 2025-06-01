package com.Proyecto.La_Tertulia.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacturaVentaDTO {

    private Long id;
    private UsuarioDTO usuario;
    private String tipoDocumento;
    private Long numeroDocumento;
    private LocalDate fechaFactura;
    private VentaDTO venta;
    private int subTotal;
    private double iva;
    private double totalFactura;
}
