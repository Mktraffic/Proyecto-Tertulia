package com.Proyecto.La_Tertulia.decorator;

import com.Proyecto.La_Tertulia.dto.ProductDTO;

public class Unidad extends BebidaDecorator {
     public Unidad(Bebida bebida) {
        super(bebida);
    }

    @Override
    public ProductDTO descripcion(ProductDTO product) {
        
        product = super.descripcion(product);

        String descripcionActual = product.getDescription();
        product.setDescription("Unidad " + descripcionActual);
         product.setPresentation("Unidad");

        return product;
    }

    @Override
    public int precio(ProductDTO product) {
        
        return super.precio(product) ; 
    }
    
}
