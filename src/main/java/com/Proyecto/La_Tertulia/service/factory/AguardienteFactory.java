package com.Proyecto.La_Tertulia.service.factory;

import org.springframework.beans.factory.annotation.Autowired;

import com.Proyecto.La_Tertulia.decorator.Bebida;

import com.Proyecto.La_Tertulia.decorator.BebidaAguardiente;
import com.Proyecto.La_Tertulia.decorator.DosCicuenta;
import com.Proyecto.La_Tertulia.decorator.Lata;
import com.Proyecto.La_Tertulia.decorator.Litro;
import com.Proyecto.La_Tertulia.decorator.SetenCincoMl;
import com.Proyecto.La_Tertulia.decorator.Tetrapack;
import com.Proyecto.La_Tertulia.decorator.TrecientosSeteCinconMl;
import com.Proyecto.La_Tertulia.decorator.TrecientosTreintaMililitros;
import com.Proyecto.La_Tertulia.decorator.Unidad;
import com.Proyecto.La_Tertulia.decorator.Vidrio;
import com.Proyecto.La_Tertulia.dto.ProductDTO;
import com.Proyecto.La_Tertulia.mapper.ProductMapperImplement;
import com.Proyecto.La_Tertulia.model.Aguardiente;
import com.Proyecto.La_Tertulia.model.Product;
import com.Proyecto.La_Tertulia.repository.ProductRepository;

public class AguardienteFactory implements ProductoFactory {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapperImplement productMapper;

    @Override
    public Product crearProducto(ProductDTO dto) {
        return productRepository.save((Aguardiente) productMapper.toEntity(dto));
    }
      public ProductDTO crearAguardienteUnidadTresMl(ProductDTO product) {
        Bebida aguardiente = new Unidad(new TrecientosSeteCinconMl(new Vidrio(new BebidaAguardiente())));
        ProductDTO resultado = aguardiente.descripcion(product);
        int precioFinal = aguardiente.precio(product);
        resultado.setPrice(precioFinal);
        return resultado;
    }
      public ProductDTO crearAguardienteUnidadSetenCinco(ProductDTO product) {
        Bebida aguardiente = new Unidad(new SetenCincoMl(new Vidrio(new BebidaAguardiente())));
        ProductDTO resultado = aguardiente.descripcion(product);
        int precioFinal = aguardiente.precio(product);
        resultado.setPrice(precioFinal);
        return resultado;
    }
      public ProductDTO crearAguardienteUnidadLitro(ProductDTO product) {
        Bebida aguardiente = new Unidad(new Litro(new Vidrio(new BebidaAguardiente())));
        ProductDTO resultado = aguardiente.descripcion(product);
        int precioFinal = aguardiente.precio(product);
        resultado.setPrice(precioFinal);
        return resultado;
    }
     public ProductDTO crearAguardienteUnidadDosCinTetra(ProductDTO product) {
        Bebida aguardiente = new Unidad(new DosCicuenta(new Tetrapack(new BebidaAguardiente())));
        ProductDTO resultado = aguardiente.descripcion(product);
        int precioFinal = aguardiente.precio(product);
        resultado.setPrice(precioFinal);
        return resultado;
    }
      public ProductDTO crearAguardienteUnidadLitroTetra(ProductDTO product) {
        Bebida aguardiente = new Unidad(new Litro(new Tetrapack(new BebidaAguardiente())));
        ProductDTO resultado = aguardiente.descripcion(product);
        int precioFinal = aguardiente.precio(product);
        resultado.setPrice(precioFinal);
        return resultado;
    }
}