package com.Proyecto.La_Tertulia.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@DiscriminatorValue("AGUARDIENTE")
public class Aguardiente extends Product {

    @Column(name = "graduacion_alcoholica")
    private double graduacionAlcoholica;

    @Column(name = "tipo_Aguardiente")
    private String tipo;

    public Aguardiente(Long id, String name, String description, String presentation, int stock, int price, double graduacionAlcoholica, String tipo) {
        super(id, name, description, presentation, stock, price);
        this.graduacionAlcoholica = graduacionAlcoholica;
        this.tipo = tipo;
    }

}
