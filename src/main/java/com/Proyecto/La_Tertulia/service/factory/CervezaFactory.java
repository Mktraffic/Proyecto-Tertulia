package com.Proyecto.La_Tertulia.service.factory;

import org.springframework.beans.factory.annotation.Autowired;

import com.Proyecto.La_Tertulia.dto.ProductDTO;
import com.Proyecto.La_Tertulia.mapper.ProductMapperImplement;
import com.Proyecto.La_Tertulia.model.Cerveza;
import com.Proyecto.La_Tertulia.model.Product;
import com.Proyecto.La_Tertulia.repository.ProductRepository;

public class CervezaFactory implements ProductoFactory {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapperImplement productMapper;

    @Override
    public Product crearProducto(ProductDTO dto) {
        return productRepository.save((Cerveza) productMapper.toEntity(dto));
    }
}