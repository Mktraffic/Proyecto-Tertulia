package com.Proyecto.La_Tertulia.service.factory;

import com.Proyecto.La_Tertulia.dto.*;

public class ProductoFactoryProducer {
    
    public ProductoFactory getFactory(ProductDTO producto) {
        ProductoFactory factory = null;
        if (producto instanceof VinoDTO) {
            System.out.println("entro aca[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]");
            factory = new VinoFactory();
            factory.crearProducto(producto);
        } else if (producto instanceof CervezaDTO) {
            factory = new CervezaFactory();
            factory.crearProducto(producto);
        } else if (producto instanceof VodkaDTO) {
            factory = new VodkaFactory();
            factory.crearProducto(producto);
        } else if (producto instanceof AguardienteDTO) {
            factory = new AguardienteFactory();
            factory.crearProducto(producto);
        }
        return factory;
    }
}