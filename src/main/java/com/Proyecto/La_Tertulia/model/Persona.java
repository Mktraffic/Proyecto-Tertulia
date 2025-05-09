package com.Proyecto.La_Tertulia.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "persona")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Persona {

    @Id
    @Column(name = "id_persona", length = 50, nullable = false)
    //numero de documento
    private Long id;

    @Column(name = "nombre", length = 100)
    @Size(min = 3, max = 100)
    private String nombre;

    @Column(name = "apellido", length = 100)
    @Size(min = 3, max = 100)
    private String apellido;

    @Column(name = "numero_telefono", length = 20, nullable = false)
    @Size(min = 3, max = 20)
    private int numeroTelefono;

    @Column(name = "correo_electronico", length = 200)
    @Size(min = 3, max = 200)
    private String correo;

    @Column(name = "fecha_nacimiento", length = 80)
    @Size(min = 3, max = 80)
    private String fechaNacimiento;
     
}