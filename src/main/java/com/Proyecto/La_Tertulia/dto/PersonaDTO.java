package com.Proyecto.La_Tertulia.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonaDTO {
    private long id;
    private String nombre;
    private String apellido;
}