package com.Proyecto.La_Tertulia.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AguardienteDTO extends ProductDTO {
    private double graduacionAlcoholica;
    private String tipo;

    public AguardienteDTO(Long id, String name, String description, String presentation, int stock, int price, double graduacionAlcoholica, String tipo) {
        super(id, name, description, presentation, stock, price);
        this.graduacionAlcoholica = graduacionAlcoholica;
        this.tipo = tipo;
    }

    @JsonProperty("tipo")
    public String getTipo() {
        return "AGUARDIENTE";
    }
}
