package com.Proyecto.La_Tertulia.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonaDTO {
    private long id;
    private int documentoIdentidad;
    private String tipoDocumento;
    private String nombre;
    private String apellido;
    private long numeroTelefono;
    private String correo;
    private LocalDate fechaNacimiento;
}