

package com.Proyecto.La_Tertulia.decorator;

import com.Proyecto.La_Tertulia.dto.ProductDTO;


public abstract class BebidaDecorator implements Bebida {
    protected Bebida bebida;

    public BebidaDecorator(Bebida bebida) {
        this.bebida = bebida;
    }

    @Override
    public ProductDTO descripcion( ProductDTO  product) {
        return bebida.descripcion(product);
    }

    @Override
    public int precio(ProductDTO  product) {
        return bebida.precio(  product);
    }
}
