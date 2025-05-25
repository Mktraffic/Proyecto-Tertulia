package com.Proyecto.La_Tertulia.decorator;

import com.Proyecto.La_Tertulia.dto.ProductDTO;


public class Bandeja extends BebidaDecorator {
     public Bandeja(Bebida bebida) {
        super(bebida);
    }

    @Override
    public ProductDTO descripcion(ProductDTO product) {
        
        product = super.descripcion(product);
        String descripcionActual = product.getDescription();
        product.setDescription("Bandeja de " + descripcionActual);
        product.setPresentation("Bandeja");
        return product;
    }

    @Override
    public int precio(ProductDTO product) {

        return super.precio(product) * 12 - 1000;
        
    }
}