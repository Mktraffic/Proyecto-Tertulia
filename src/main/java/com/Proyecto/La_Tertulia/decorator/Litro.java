package com.Proyecto.La_Tertulia.decorator;

import com.Proyecto.La_Tertulia.dto.ProductDTO;


public class Litro extends BebidaDecorator {
    public Litro(Bebida bebida) {
        super(bebida);
    }

    @Override
    public ProductDTO descripcion(ProductDTO product) {
        
        product = super.descripcion(product);

        String descripcionActual = product.getDescription();
        product.setDescription( descripcionActual+" 1000 ml");

        return product;
    }

    @Override
    public int precio(ProductDTO product) {
        return super.precio(product) ;
    }
}

