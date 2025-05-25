package com.Proyecto.La_Tertulia.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@DiscriminatorValue("CERVEZA")
public class Cerveza extends Product {

    @Column(name = "graduacion_alcoholica")
    private double graduacionAlcoholica;
    
    public Cerveza(Long id, String name, String description, String presentation, int stock, int price, double graduacionAlcoholica) {
        super(id, name, description, presentation, stock, price);
        this.graduacionAlcoholica = graduacionAlcoholica;
    }

}
