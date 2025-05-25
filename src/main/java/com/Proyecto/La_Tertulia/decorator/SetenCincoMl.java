package com.Proyecto.La_Tertulia.decorator;

import com.Proyecto.La_Tertulia.dto.ProductDTO;

public class SetenCincoMl extends BebidaDecorator {
    public SetenCincoMl(Bebida bebida) {
        super(bebida);
    }

    @Override
    public ProductDTO descripcion(ProductDTO product) {
        product = super.descripcion(product);

        String descripcionActual = product.getDescription();
        product.setDescription(descripcionActual + " 750ml");

        return product;
    }

    @Override
    public int precio(ProductDTO product) {
        return super.precio(product);
    }
}
