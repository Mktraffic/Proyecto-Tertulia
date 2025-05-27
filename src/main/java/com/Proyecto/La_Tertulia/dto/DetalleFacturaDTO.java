package com.Proyecto.La_Tertulia.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleFacturaDTO {
    private Long id;
    private String tipoDocumento;
    private Long numeroDocumento;
    private ProductDTO producto;

}
