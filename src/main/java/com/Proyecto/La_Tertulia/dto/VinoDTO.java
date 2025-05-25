package com.Proyecto.La_Tertulia.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class VinoDTO extends ProductDTO {
    private double graduacionAlcoholica;
    private String regionOrigen;
    private int anejamiento;

    public VinoDTO(Long id, String name, String description, String presentation, int stock, int price, double graduacionAlcoholica, String regionOrigen, int anejamiento) {
        super(id, name, description, presentation, stock, price);
        this.graduacionAlcoholica = graduacionAlcoholica;
        this.regionOrigen = regionOrigen;
        this.anejamiento = anejamiento;
    }

    @JsonProperty("tipo")
    public String getTipo() {
        return "VINO";
    }
}
