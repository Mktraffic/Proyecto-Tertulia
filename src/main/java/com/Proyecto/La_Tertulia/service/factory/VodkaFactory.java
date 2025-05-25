package com.Proyecto.La_Tertulia.service.factory;

import com.Proyecto.La_Tertulia.mapper.ProductMapperImplement;

import org.springframework.beans.factory.annotation.Autowired;

import com.Proyecto.La_Tertulia.decorator.Bebida;
import com.Proyecto.La_Tertulia.decorator.BebidaVodka;
import com.Proyecto.La_Tertulia.decorator.DosCicuenta;
import com.Proyecto.La_Tertulia.decorator.Litro;
import com.Proyecto.La_Tertulia.decorator.SetenCincoMl;
import com.Proyecto.La_Tertulia.decorator.Tetrapack;
import com.Proyecto.La_Tertulia.decorator.TrecientosSeteCinconMl;
import com.Proyecto.La_Tertulia.decorator.Unidad;
import com.Proyecto.La_Tertulia.decorator.Vidrio;
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
    public ProductDTO crearVodkaUnidadTresMl(ProductDTO product) {
    Bebida vodka = new Unidad(new TrecientosSeteCinconMl(new Vidrio(new BebidaVodka())));
    ProductDTO resultado = vodka.descripcion(product);
    int precioFinal = vodka.precio(product);
    resultado.setPrice(precioFinal);
    return resultado;
}

public ProductDTO crearVodkaUnidadSetenCinco(ProductDTO product) {
    Bebida vodka = new Unidad(new SetenCincoMl(new Vidrio(new BebidaVodka())));
    ProductDTO resultado = vodka.descripcion(product);
    int precioFinal = vodka.precio(product);
    resultado.setPrice(precioFinal);
    return resultado;
}

public ProductDTO crearVodkaUnidadLitro(ProductDTO product) {
    Bebida vodka = new Unidad(new Litro(new Vidrio(new BebidaVodka())));
    ProductDTO resultado = vodka.descripcion(product);
    int precioFinal = vodka.precio(product);
    resultado.setPrice(precioFinal);
    return resultado;
}

public ProductDTO crearVodkaUnidadDosCinTetra(ProductDTO product) {
    Bebida vodka = new Unidad(new DosCicuenta(new Tetrapack(new BebidaVodka())));
    ProductDTO resultado = vodka.descripcion(product);
    int precioFinal = vodka.precio(product);
    resultado.setPrice(precioFinal);
    return resultado;
}

public ProductDTO crearVodkaUnidadLitroTetra(ProductDTO product) {
    Bebida vodka = new Unidad(new Litro(new Tetrapack(new BebidaVodka())));
    ProductDTO resultado = vodka.descripcion(product);
    int precioFinal = vodka.precio(product);
    resultado.setPrice(precioFinal);
    return resultado;
}

}
