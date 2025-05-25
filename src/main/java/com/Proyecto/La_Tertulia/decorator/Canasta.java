package com.Proyecto.La_Tertulia.decorator;

import com.Proyecto.La_Tertulia.dto.ProductDTO;


public class Canasta extends BebidaDecorator {
    public Canasta(Bebida bebida) {
        super(bebida);
    }

     @Override
    public ProductDTO descripcion(ProductDTO product) {
        
        product = super.descripcion(product);

        String descripcionActual = product.getDescription();
        product.setDescription("canasta de " + descripcionActual);
         product.setPresentation("Canasta");

        return product;
    }

    @Override
    public int precio(ProductDTO product) {
        
        return super.precio(product) * 30 - 3000; 
        
    }
}
