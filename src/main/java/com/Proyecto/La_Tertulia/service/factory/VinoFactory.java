package com.Proyecto.La_Tertulia.service.factory;

import com.Proyecto.La_Tertulia.mapper.ProductMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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
import com.Proyecto.La_Tertulia.repository.VinoRepository;
import com.Proyecto.La_Tertulia.model.Product;

@Component
public class VinoFactory implements ProductoFactory {

    @Autowired
    private VinoRepository VinoRepository;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public Product crearProducto(ProductDTO dto) {

        System.out.println("Antes de guardar: " + dto);
        System.out.println("Creando entidad O: " + dto.getName());
        Vino vino = (Vino) productMapper.toEntityVino(dto);
        System.out.println("Entidad a guardar: " + vino);

        Vino guardado = VinoRepository.save(vino);
        System.out.println("Guardado: " + guardado);

        System.out.println("Creando producto Vino: " + dto);
        return guardado;
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