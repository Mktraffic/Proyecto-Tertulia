package com.Proyecto.La_Tertulia.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@DiscriminatorValue("VINO")
public class Vino extends Product {

    @Column(name = "graduacion_alcoholica")
    private double graduacionAlcoholica;

    @Column(name = "region_origen")
    private String regionOrigen;

    @Column(name = "anejamiento")
    private int anejamiento;

    public Vino(Long id, String name, String description, String presentation, int stock, int price, double graduacionAlcoholica, String regionOrigen, int anejamiento) {
        super(id, name, description, presentation, stock, price);
        this.graduacionAlcoholica = graduacionAlcoholica;
        this.regionOrigen = regionOrigen;
        this.anejamiento = anejamiento;
    }

}
