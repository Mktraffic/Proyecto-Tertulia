package com.Proyecto.La_Tertulia.service.factory;
import com.Proyecto.La_Tertulia.mapper.ProductMapperImplement;

import org.springframework.beans.factory.annotation.Autowired;

import com.Proyecto.La_Tertulia.decorator.Bebida;
import com.Proyecto.La_Tertulia.decorator.BebidaVino;
import com.Proyecto.La_Tertulia.decorator.DosCicuenta;
import com.Proyecto.La_Tertulia.decorator.Litro;
import com.Proyecto.La_Tertulia.decorator.SetenCincoMl;
import com.Proyecto.La_Tertulia.decorator.Tetrapack;
import com.Proyecto.La_Tertulia.decorator.TrecientosSeteCinconMl;
import com.Proyecto.La_Tertulia.decorator.Unidad;
import com.Proyecto.La_Tertulia.decorator.Vidrio;
import com.Proyecto.La_Tertulia.dto.ProductDTO;
import com.Proyecto.La_Tertulia.model.Vino;
import com.Proyecto.La_Tertulia.repository.ProductRepository;
import com.Proyecto.La_Tertulia.model.Product;

public class VinoFactory implements ProductoFactory {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapperImplement productMapper;
    
    @Override
    public Product crearProducto(ProductDTO dto) {
        return productRepository.save((Vino) productMapper.toEntity(dto));
    }
    public ProductDTO crearVinoUnidadTresMl(ProductDTO product) {
    Bebida vino = new Unidad(new TrecientosSeteCinconMl(new Vidrio(new BebidaVino())));
    ProductDTO resultado = vino.descripcion(product);
    int precioFinal = vino.precio(product);
    resultado.setPrice(precioFinal);
    return resultado;
}

public ProductDTO crearVinoUnidadSetenCinco(ProductDTO product) {
    Bebida vino = new Unidad(new SetenCincoMl(new Vidrio(new BebidaVino())));
    ProductDTO resultado = vino.descripcion(product);
    int precioFinal = vino.precio(product);
    resultado.setPrice(precioFinal);
    return resultado;
}

public ProductDTO crearVinoUnidadLitro(ProductDTO product) {
    Bebida vino = new Unidad(new Litro(new Vidrio(new BebidaVino())));
    ProductDTO resultado = vino.descripcion(product);
    int precioFinal = vino.precio(product);
    resultado.setPrice(precioFinal);
    return resultado;
}

public ProductDTO crearVinoUnidadDosCinTetra(ProductDTO product) {
    Bebida vino = new Unidad(new DosCicuenta(new Tetrapack(new BebidaVino())));
    ProductDTO resultado = vino.descripcion(product);
    int precioFinal = vino.precio(product);
    resultado.setPrice(precioFinal);
    return resultado;
}

public ProductDTO crearVinoUnidadLitroTetra(ProductDTO product) {
    Bebida vino = new Unidad(new Litro(new Tetrapack(new BebidaVino())));
    ProductDTO resultado = vino.descripcion(product);
    int precioFinal = vino.precio(product);
    resultado.setPrice(precioFinal);
    return resultado;
}

}