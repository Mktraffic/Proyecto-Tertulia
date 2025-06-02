package com.Proyecto.La_Tertulia.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.Proyecto.La_Tertulia.dto.ProductDTO;

@Component
public class StringToProductDTOConverter implements Converter<String, ProductDTO> {
    
    @Override
    public ProductDTO convert(String source) {
        if (source == null || source.trim().isEmpty()) {
            return null;
        }
        
        try {
            Long productId = Long.parseLong(source.trim());
            // Solo crear un ProductDTO con el ID, no buscar en BD
            ProductDTO product = new ProductDTO();
            product.setId(productId);
            return product;
        } catch (NumberFormatException e) {
            System.err.println("Error converting string to ProductDTO: " + source);
            return null;
        }
    }
}