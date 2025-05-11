package com.Proyecto.La_Tertulia.mapper;

import org.springframework.stereotype.Component;

import com.Proyecto.La_Tertulia.dto.ProductDTO;
import com.Proyecto.La_Tertulia.model.Product;

@Component
public class ProductMapperImplement implements ProductMapper {

    @Override
    public ProductDTO toDTO(Product product) {
        if (product == null) {
            return null;
        }
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescripcion_producto(product.getDescripcion_producto());
        productDTO.setPrice(product.getPrice());
        return productDTO;
    }

    @Override
    public Product toEntity(ProductDTO productDTO) {
        if (productDTO == null) {
            return null;
        }
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setDescripcion_producto(productDTO.getDescripcion_producto());
        product.setPrice(productDTO.getPrice());
        return product;
    }

}
