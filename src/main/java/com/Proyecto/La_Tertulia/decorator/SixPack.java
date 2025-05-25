package com.Proyecto.La_Tertulia.decorator;

import com.Proyecto.La_Tertulia.dto.ProductDTO;


public class SixPack extends BebidaDecorator {
    public SixPack(Bebida bebida) {
        super(bebida);
    }

    @Override
    public ProductDTO descripcion(ProductDTO product) {
        
        product = super.descripcion(product);

        String descripcionActual = product.getDescription();
        product.setDescription("Six Pack de " + descripcionActual);
         product.setPresentation("Six Pack");

        return product;
    }

    @Override
    public int precio(ProductDTO product) {
        
        return super.precio(product) * 6 - 500; 
    }
}
