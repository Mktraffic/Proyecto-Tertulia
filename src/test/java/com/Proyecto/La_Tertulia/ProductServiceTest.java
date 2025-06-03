package com.Proyecto.La_Tertulia;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.Proyecto.La_Tertulia.dto.ProductDTO;

import com.Proyecto.La_Tertulia.mapper.ProductMapperImplement;
import com.Proyecto.La_Tertulia.model.Product;
import com.Proyecto.La_Tertulia.repository.ProductRepository;
import com.Proyecto.La_Tertulia.service.ProductService;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapperImplement productMapper;

    @InjectMocks
    private ProductService productService;

    @Test
    void testFindAllProducts() {

        Product product1 = new Product(1L, "Producto 1", "tipo 1", "descripcionx", "300ml", "5 grados de alcohol", 10,
                5);
        Product product2 = new Product(2L, "Producto 2", "tipo 2", "descripciony", "500ml", "10 grados de alcohol", 20,
                10);
        List<Product> productos = Arrays.asList(product1, product2);

        ProductDTO productDTO1 = new ProductDTO(1L, "Producto 1", "tipo 1", "descripcionx", "300ml",
                "5 grados de alcohol", 10, 5);
        ProductDTO productDTO2 = new ProductDTO(2L, "Producto 2", "tipo 2", "descripciony", "500ml",
                "10 grados de alcohol", 20, 10);
        List<ProductDTO> expected = Arrays.asList(productDTO1, productDTO2);

        Mockito.when(productRepository.findAll()).thenReturn(productos);
        Mockito.when(productMapper.toDTO(product1)).thenReturn(productDTO1);
        Mockito.when(productMapper.toDTO(product2)).thenReturn(productDTO2);

        List<ProductDTO> result = productService.findAllProducts();

        Assertions.assertEquals(expected.size(), result.size());
        Assertions.assertEquals(expected.get(0).getId(), result.get(0).getId());
        Assertions.assertEquals(expected.get(1).getId(), result.get(1).getId());

        Mockito.verify(productRepository).findAll();
        Mockito.verify(productMapper).toDTO(product1);
        Mockito.verify(productMapper).toDTO(product2);
    }

    @Test
    void testAddProductInDB() {

        ProductDTO nuevoProducto = new ProductDTO(null, "Whisky", "Licor", "XXX", "750ml", "40%", 100, 10);
        Product entidadProducto = new Product(null, "Whisky", "Licor", "XXX", "750ml", "40%", 100, 10);

        Mockito.when(productRepository.findAll()).thenReturn(List.of());
        Mockito.when(productMapper.toEntity(nuevoProducto)).thenReturn(entidadProducto);
        Mockito.when(productRepository.save(entidadProducto)).thenReturn(entidadProducto);

        String resultado = productService.addProductInDB(nuevoProducto);

        Assertions.assertEquals("Producto agregado correctamente", resultado);
        Mockito.verify(productRepository).save(entidadProducto);
    }

    @Test
    void testFindById() {

        Long id = 1L;

        Product producto = new Product(id, "Cerveza", "Bebida", "Rubia", "330ml", "5%", 10, 5);
        ProductDTO productoDTO = new ProductDTO(id, "Cerveza", "Bebida", "Rubia", "330ml", "5%", 10, 5);

        Mockito.when(productRepository.findById(id)).thenReturn(Optional.of(producto));
        Mockito.when(productMapper.toDTO(producto)).thenReturn(productoDTO);

        Optional<ProductDTO> resultado = productService.findById(id);

        Assertions.assertTrue(resultado.isPresent());
        Assertions.assertEquals(id, resultado.get().getId());
        Mockito.verify(productRepository).findById(id);
        Mockito.verify(productMapper).toDTO(producto);
    }

    @Test
    void testFindAllProductoByName() {
        Product producto1 = new Product(1L, "Cerveza Rubia", "Bebida", "desc", "330ml", "5%", 10, 5);
        Product producto2 = new Product(2L, "Cerveza Negra", "Bebida", "desc", "500ml", "7%", 15, 10);
        Product producto3 = new Product(3L, "Whisky", "Licor", "desc", "700ml", "40%", 100, 8);
        List<Product> productos = List.of(producto1, producto2, producto3);

        ProductDTO dto1 = new ProductDTO(1L, "Cerveza Rubia", "Bebida", "desc", "330ml", "5%", 10, 5);
        ProductDTO dto2 = new ProductDTO(2L, "Cerveza Negra", "Bebida", "desc", "500ml", "7%", 15, 10);
        ProductDTO dto3 = new ProductDTO(3L, "Whisky", "Licor", "desc", "700ml", "40%", 100, 8);

        Mockito.when(productRepository.findAll()).thenReturn(productos);
        Mockito.when(productMapper.toDTO(producto1)).thenReturn(dto1);
        Mockito.when(productMapper.toDTO(producto2)).thenReturn(dto2);
        Mockito.when(productMapper.toDTO(producto3)).thenReturn(dto3);

        List<ProductDTO> resultado = productService.findAllProductoByName("cerveza");

        Assertions.assertEquals(2, resultado.size());
        Assertions.assertTrue(resultado.stream().allMatch(p -> p.getName().toLowerCase().contains("cerveza")));

        Mockito.verify(productRepository).findAll();
    }

    @Test
    void testFindProductoByName() {
        Product producto1 = new Product(1L, "Cerveza Ámbar", "Bebida", "desc", "330ml", "5%", 10, 5);
        Product producto2 = new Product(2L, "Cerveza IPA", "Bebida", "desc", "500ml", "6%", 15, 10);
        Product producto3 = new Product(3L, "Vino", "Licor", "desc", "750ml", "12%", 20, 8);

        ProductDTO dto1 = new ProductDTO(1L, "Cerveza Ámbar", "Bebida", "desc", "330ml", "5%", 10, 5);
        ProductDTO dto2 = new ProductDTO(2L, "Cerveza IPA", "Bebida", "desc", "500ml", "6%", 15, 10);
        ProductDTO dto3 = new ProductDTO(3L, "Vino", "Licor", "desc", "750ml", "12%", 20, 8);

        List<Product> productos = List.of(producto1, producto2, producto3);

        Mockito.when(productRepository.findAll()).thenReturn(productos);
        Mockito.when(productMapper.toDTO(producto1)).thenReturn(dto1);
        Mockito.when(productMapper.toDTO(producto2)).thenReturn(dto2);
        Mockito.when(productMapper.toDTO(producto3)).thenReturn(dto3);

        List<ProductDTO> resultado = productService.findProductoByName("cerveza");

        Assertions.assertEquals(2, resultado.size());
        Assertions.assertTrue(resultado.stream().allMatch(p -> p.getName().toLowerCase().contains("cerveza")));

        Mockito.verify(productRepository).findAll();
        Mockito.verify(productMapper).toDTO(producto1);
        Mockito.verify(productMapper).toDTO(producto2);
        Mockito.verify(productMapper).toDTO(producto3);
    }

    @Test
    void testUpdateProduct() {

        Product original = new Product(1L, "Cerveza", "Bebida", "Rubia", "330ml", "5%", 10, 5);
        ProductDTO datos = new ProductDTO(1L, "Cerveza Artesanal", "Bebida", "Rubia", "330ml", "5%", 12, 8);

        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(original));
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(original);

        String resultado = productService.updateProduct(datos);

        Assertions.assertEquals("Producto modificado correctamente", resultado);
        Mockito.verify(productRepository).save(Mockito.any());
    }

    @Test
    void testProductCategories() {
        Product producto1 = new Product(1L, "Cerveza", "Bebida", "desc", "330ml", "5%", 10, 5);
        Product producto2 = new Product(2L, "Vino", "Bebida", "desc", "750ml", "14%", 20, 10);
        Product producto3 = new Product(3L, "Ron", "Licor", "desc", "750ml", "35%", 15, 8);
        Product producto4 = new Product(4L, "Whisky", "Licor", "desc", "700ml", "40%", 18, 6);

        ProductDTO dto1 = new ProductDTO(1L, "Cerveza", "Bebida", "desc", "330ml", "5%", 10, 5);
        ProductDTO dto2 = new ProductDTO(2L, "Vino", "Bebida", "desc", "750ml", "14%", 20, 10);
        ProductDTO dto3 = new ProductDTO(3L, "Ron", "Licor", "desc", "750ml", "35%", 15, 8);
        ProductDTO dto4 = new ProductDTO(4L, "Whisky", "Licor", "desc", "700ml", "40%", 18, 6);

        List<Product> entityList = List.of(producto1, producto2, producto3, producto4);

        Mockito.when(productRepository.findAll()).thenReturn(entityList);
        Mockito.when(productMapper.toDTO(producto1)).thenReturn(dto1);
        Mockito.when(productMapper.toDTO(producto2)).thenReturn(dto2);
        Mockito.when(productMapper.toDTO(producto3)).thenReturn(dto3);
        Mockito.when(productMapper.toDTO(producto4)).thenReturn(dto4);

        List<String> categorias = productService.productCategories();

        Assertions.assertEquals(2, categorias.size());
        Assertions.assertTrue(categorias.contains("Bebida"));
        Assertions.assertTrue(categorias.contains("Licor"));

        Mockito.verify(productRepository).findAll();
        Mockito.verify(productMapper).toDTO(producto1);
        Mockito.verify(productMapper).toDTO(producto2);
        Mockito.verify(productMapper).toDTO(producto3);
        Mockito.verify(productMapper).toDTO(producto4);
    }

    @Test
    void testProductsByCategoryParam() {

        Product prod1 = new Product(1L, "Cerveza", "Bebida", "desc", "330ml", "5%", 10, 5);
        Product prod2 = new Product(2L, "Vino", "Bebida", "desc", "750ml", "14%", 20, 10);
        Product prod3 = new Product(3L, "Whisky", "Licor", "desc", "700ml", "40%", 15, 8);
        List<Product> listaProductos = List.of(prod1, prod2, prod3);

        ProductDTO dto1 = new ProductDTO(1L, "Cerveza", "Bebida", "desc", "330ml", "5%", 10, 5);
        ProductDTO dto2 = new ProductDTO(2L, "Vino", "Bebida", "desc", "750ml", "14%", 20, 10);
        ProductDTO dto3 = new ProductDTO(3L, "Whisky", "Licor", "desc", "700ml", "40%", 15, 8);

        Mockito.when(productRepository.findAll()).thenReturn(listaProductos);
        Mockito.when(productMapper.toDTO(prod1)).thenReturn(dto1);
        Mockito.when(productMapper.toDTO(prod2)).thenReturn(dto2);
        Mockito.when(productMapper.toDTO(prod3)).thenReturn(dto3);

        ArrayList<ProductDTO> resultado = productService.productsByCategoryParam("bebida");

        Assertions.assertEquals(2, resultado.size());
        Assertions.assertTrue(resultado.stream().allMatch(p -> p.getType().equalsIgnoreCase("bebida")));

        Mockito.verify(productRepository).findAll();
        Mockito.verify(productMapper).toDTO(prod1);
        Mockito.verify(productMapper).toDTO(prod2);
        Mockito.verify(productMapper).toDTO(prod3);
    }

    @Test
    void testPresentationByProductId_DevuelvePresentaciones() {

        Long productoId = 1L;
        List<String> presentaciones = List.of("330ml", "500ml");

        Mockito.when(productRepository.findPresentationByProductId(productoId)).thenReturn(presentaciones);

        List<String> resultado = productService.presentationByProductid(productoId);

        Assertions.assertEquals(2, resultado.size());
        Assertions.assertTrue(resultado.contains("330ml"));
        Assertions.assertTrue(resultado.contains("500ml"));
        Mockito.verify(productRepository).findPresentationByProductId(productoId);
    }

    @Test
    void testProductPrice_DevuelvePrecioCorrecto() {

        Long productoId = 1L;
        String presentacion = "500ml";
        double precioEsperado = 12.5;

        Mockito.when(productRepository.findPriceByProductIdAndPresentation(productoId, presentacion))
                .thenReturn(precioEsperado);

        double resultado = productService.productPrice(productoId, presentacion);

        Assertions.assertEquals(precioEsperado, resultado);
        Mockito.verify(productRepository).findPriceByProductIdAndPresentation(productoId, presentacion);
    }

}
