package com.Proyecto.La_Tertulia.decorator;

import com.Proyecto.La_Tertulia.dto.ProductDTO;


public class BebidaCerveza implements Bebida {
    @Override
    public ProductDTO descripcion(ProductDTO product) {
        product.setDescription(product.getName());
        return product;
    }

    @Override
    public int precio(ProductDTO product) {
        return  product.getPrice();
    }
}
