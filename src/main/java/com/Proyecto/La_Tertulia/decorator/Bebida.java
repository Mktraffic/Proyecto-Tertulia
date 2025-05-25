package com.Proyecto.La_Tertulia.decorator;

import com.Proyecto.La_Tertulia.dto.ProductDTO;


public interface Bebida {
    ProductDTO descripcion(ProductDTO product);
    int precio(ProductDTO product);
}
