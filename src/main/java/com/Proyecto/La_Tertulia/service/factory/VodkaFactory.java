package com.Proyecto.La_Tertulia.service.factory;

import com.Proyecto.La_Tertulia.mapper.ProductMapperImplement;

import org.springframework.beans.factory.annotation.Autowired;

import com.Proyecto.La_Tertulia.dto.ProductDTO;
import com.Proyecto.La_Tertulia.model.Vodka;
import com.Proyecto.La_Tertulia.repository.ProductRepository;
import com.Proyecto.La_Tertulia.model.Product;

public class VodkaFactory implements ProductoFactory {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapperImplement productMapper;
    
    @Override
    public Product crearProducto(ProductDTO dto) {
        return productRepository.save((Vodka) productMapper.toEntity(dto));
    }
}
