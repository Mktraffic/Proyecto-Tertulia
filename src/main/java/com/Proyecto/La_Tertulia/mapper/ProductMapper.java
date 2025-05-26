package com.Proyecto.La_Tertulia.mapper;

import org.mapstruct.Mapper;

import com.Proyecto.La_Tertulia.dto.ProductDTO;
import com.Proyecto.La_Tertulia.model.Product;
import com.Proyecto.La_Tertulia.model.Vino;


@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDTO toDTO(Product product);

    Product toEntity(ProductDTO productDTO);

    Vino toEntityVino(ProductDTO productDTO);
}