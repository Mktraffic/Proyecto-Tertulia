package com.Proyecto.La_Tertulia.service.factory;

import com.Proyecto.La_Tertulia.dto.ProductDTO;
import com.Proyecto.La_Tertulia.model.Product;

public interface ProductoFactory {
    Product crearProducto(ProductDTO dto);
}