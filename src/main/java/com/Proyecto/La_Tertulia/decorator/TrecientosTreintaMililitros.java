package com.Proyecto.La_Tertulia.decorator;

import com.Proyecto.La_Tertulia.dto.ProductDTO;


public class TrecientosTreintaMililitros extends BebidaDecorator {
    public TrecientosTreintaMililitros(Bebida bebida) {
        super(bebida);
    }

   @Override
    public ProductDTO descripcion(ProductDTO product) {
        
        product = super.descripcion(product);

        String descripcionActual = product.getDescription();
        product.setDescription( descripcionActual+" 375ml");

        return product;
    }

    @Override
    public int precio(ProductDTO product) {
        
        return super.precio(product) ;
    }
}