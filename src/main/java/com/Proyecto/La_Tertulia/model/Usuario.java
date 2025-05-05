package com.Proyecto.La_Tertulia.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Usuario {

    @Id
    @Column(name = "id_usuario", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", nullable =  false)
    @Size(min = 1, max = 50, message = "El usuario debe tener entre 5 y 50 caracteres")
    private String user_name;

    @Column(name = "password", nullable = false)
    @Size(min = 1, max = 30, message = "La contraseña debe tener entre 5 y 20 caracteres")
    private String user_password;

    @ManyToOne
    @JoinColumn(name = "id_rol", nullable = false, referencedColumnName = "id_rol")
    private Rol rol;

    @OneToOne
    @JoinColumn(name = "id_persona", nullable = false, referencedColumnName = "id_persona")
    private Persona persona;

    public Usuario(String user_name, String user_password, Rol rol, Persona persona) {
        this.user_name = user_name;
        this.user_password = user_password;
        this.rol = rol;
        this.persona = persona;
    }
}
