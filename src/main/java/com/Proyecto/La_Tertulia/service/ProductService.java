package com.Proyecto.La_Tertulia.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.Proyecto.La_Tertulia.dto.*;
import com.Proyecto.La_Tertulia.mapper.ProductMapperImplement;
import com.Proyecto.La_Tertulia.model.*;
import com.Proyecto.La_Tertulia.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapperImplement productMapper;

    public List<ProductDTO> findAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    public String addProductInDB(ProductDTO productdto) {
        String result = "";
        if (productdto != null) {
            try {
                productdto.setId(null);
                productRepository.save(productMapper.toEntity(productdto));
                result = "Producto agregado correctamente";
            } catch (Exception e) {
                result = "Error al agregar el producto";
            }
        }
        return result;
    }

    public ResponseEntity<ProductDTO> fetchProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productMapper.toDTO(product.get()), HttpStatus.OK);
    }

    public Optional<ProductDTO> findById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toDTO);
    }

    public List<ProductDTO> findAllProductoByName(String nombre) {
        List<ProductDTO> allproducts = findAllProducts();
        List<ProductDTO> products = new ArrayList<>();
        for (ProductDTO product : allproducts) {
            if (product.getName().toLowerCase().contains(nombre.trim().toLowerCase())) {
                products.add(product);
            }
        }
        return products;
    }

    public List<ProductDTO> findProductoByName(String nombre) {
        List<ProductDTO> products = findAllProducts();
        List<ProductDTO> result = new ArrayList<>();
        for (ProductDTO product : products) {
            if (product.getName().toLowerCase().contains(nombre.trim().toLowerCase())) {
                result.add(product);
            }
        }
        return result;
    }

    public List<ProductDTO> findProductByNameOrId(String nombre) {
        ArrayList<ProductDTO> productList = new ArrayList<>();
        List<ProductDTO> productDTOs = findAllProducts();
        for (ProductDTO productDTO : productDTOs) {
            if (nombre.matches("\\d+")) {
                long idAux = Long.parseLong(nombre);
                if (productDTO.getId() == idAux) {
                    productList.add(productDTO);
                    break;
                }
            } else {
                if (productDTO.getName().equalsIgnoreCase(nombre)) {
                    productList.add(productDTO);
                }
            }
        }
        return productList;
    }

    public String updateProduct(ProductDTO datos) {
        String result = "";
       try {
        productRepository.save(productMapper.toEntity(datos));
        result = "Producto modificado correctamente";
       } catch (Exception e) {
        result = "Error en la modificacion del producto: " + datos.getName();
        System.out.println("El error es: " + e.getMessage());
       }
       return result;
    }
}