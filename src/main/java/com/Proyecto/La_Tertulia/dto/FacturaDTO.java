package com.Proyecto.La_Tertulia.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacturaDTO {

    private Long id;
    private UsuarioDTO usuario;
    private String tipoDocumento;
    private Long numeroDocumento;
    private LocalDate fechaFactura;
    private List<DetalleFacturaDTO> productos;
    private int subTotal;
    private double iva;
    private double totalFactura;
}
