package com.Proyecto.La_Tertulia.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "tipo")
@JsonSubTypes({
        @JsonSubTypes.Type(value = AguardienteDTO.class, name = "AGUARDIENTE"),
        @JsonSubTypes.Type(value = CervezaDTO.class, name = "CERVEZA"),
        @JsonSubTypes.Type(value = VinoDTO.class, name = "VINO"),
        @JsonSubTypes.Type(value = VodkaDTO.class, name = "VODKA")
})
public abstract class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private String presentation;
    private int stock;
    private int price;
}
