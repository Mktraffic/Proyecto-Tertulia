package com.Proyecto.La_Tertulia.service.factory;

import org.springframework.beans.factory.annotation.Autowired;

import com.Proyecto.La_Tertulia.decorator.Bandeja;
import com.Proyecto.La_Tertulia.decorator.Bebida;
import com.Proyecto.La_Tertulia.decorator.BebidaCerveza;
import com.Proyecto.La_Tertulia.decorator.Canasta;
import com.Proyecto.La_Tertulia.decorator.CuatrocientosSetentaTresMl;
import com.Proyecto.La_Tertulia.decorator.Lata;
import com.Proyecto.La_Tertulia.decorator.Litro;
import com.Proyecto.La_Tertulia.decorator.SixPack;
import com.Proyecto.La_Tertulia.decorator.TrecientosSeteCinconMl;
import com.Proyecto.La_Tertulia.decorator.TrecientosTreintaMililitros;
import com.Proyecto.La_Tertulia.decorator.Unidad;
import com.Proyecto.La_Tertulia.decorator.Vidrio;
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
     public ProductDTO crearCervezaLataUnidadTresMl(ProductDTO product) {
        Bebida cervezaLata = new Unidad(new TrecientosTreintaMililitros(new Lata(new BebidaCerveza())));
        ProductDTO resultado = cervezaLata.descripcion(product);
        int precioFinal = cervezaLata.precio(product);
        resultado.setPrice(precioFinal);
        return resultado;
    }
     public ProductDTO crearCervezaLataUnidadCuatroMl(ProductDTO product) {
        Bebida cervezaLata = new Unidad(new CuatrocientosSetentaTresMl(new Lata(new BebidaCerveza())));
        ProductDTO resultado = cervezaLata.descripcion(product);
        int precioFinal = cervezaLata.precio(product);
        resultado.setPrice(precioFinal);
        return resultado;
    }
    public ProductDTO crearCervezaLataSixPack(ProductDTO product) {
    Bebida cervezaSixPack = new SixPack(new TrecientosTreintaMililitros(new Lata(new BebidaCerveza())));
    ProductDTO resultado = cervezaSixPack.descripcion(product);
    int precioFinal = cervezaSixPack.precio(product);
     resultado.setPrice(precioFinal);
   return resultado;
}
    public ProductDTO crearCervezaLataBandeja(ProductDTO product) {
    Bebida cervezaBandeja = new Bandeja(new TrecientosTreintaMililitros(new Lata(new BebidaCerveza())));
    ProductDTO resultado = cervezaBandeja.descripcion(product);
    int precioFinal = cervezaBandeja.precio(product);
     resultado.setPrice(precioFinal);
   return resultado;
}
    public ProductDTO crearCervezaVidrioTresMlUnidad(ProductDTO product) {
    Bebida cervezaVidrioTresMl =  new Unidad(new TrecientosSeteCinconMl(new Vidrio(new BebidaCerveza())));
    ProductDTO resultado = cervezaVidrioTresMl.descripcion(product);
    int precioFinal = cervezaVidrioTresMl.precio(product);
     resultado.setPrice(precioFinal);
   return resultado;
}
   public ProductDTO crearCervezaVidrioTresMlCanasta(ProductDTO product) {
    Bebida cervezaVidrioTresMlCanasta =new Canasta(new TrecientosSeteCinconMl(new Vidrio(new BebidaCerveza())));
    ProductDTO resultado = cervezaVidrioTresMlCanasta.descripcion(product);
    int precioFinal = cervezaVidrioTresMlCanasta.precio(product);
     resultado.setPrice(precioFinal);
   return resultado;
}
    public ProductDTO crearCervezaVidrioLitro(ProductDTO product) {
    Bebida cervezaVidrioLitro = new Unidad(new Litro(new Vidrio(new BebidaCerveza())));
    ProductDTO resultado = cervezaVidrioLitro.descripcion(product);
    int precioFinal = cervezaVidrioLitro.precio(product);
     resultado.setPrice(precioFinal);
   return resultado;
}
}