package com.Proyecto.La_Tertulia.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VodkaDTO extends ProductDTO {
    private double graduacionAlcoholica;

    @JsonProperty("tipo")
    public String getTipo() {
        return "VODKA";
    }
    
    public VodkaDTO(Long id, String name, String description, String presentation,  int stock, int price, double graduacionAlcoholica) {
        super(id, name, description, presentation, stock, price);
        this.graduacionAlcoholica = graduacionAlcoholica;
    }
    
}
