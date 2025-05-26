package com.Proyecto.La_Tertulia.mapper;

import org.springframework.stereotype.Component;

import com.Proyecto.La_Tertulia.dto.*;
import com.Proyecto.La_Tertulia.model.*;

@Component
public class ProductMapperImplement implements ProductMapper {

    @Override
    public ProductDTO toDTO(Product product) {
        return new ProductDTO(
            product.getId(),
            product.getName(),
            product.getType(),
            product.getDescription(),
            product.getPresentation(),
            product.getGradosAlcohol(),
            product.getStock(),
            product.getPrice()
        );
    }

    @Override
    public Product toEntity(ProductDTO dto) {
        return new Product(
            dto.getId(),
            dto.getName(),
            dto.getType(),
            dto.getDescription(),
            dto.getPresentation(),
            dto.getGradosAlcohol(),
            dto.getStock(),
            dto.getPrice()
        );
    }
}
