package com.Proyecto.La_Tertulia.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import com.Proyecto.La_Tertulia.dto.ProductDTO;
import com.Proyecto.La_Tertulia.mapper.ProductMapperImplement;

import com.Proyecto.La_Tertulia.model.Product;
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

    public Product addProductInDB(ProductDTO productdto) {
        Product product = productMapper.toEntity(productdto);
        return productRepository.save(product);
    }
 public ResponseEntity<ProductDTO> fetchProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productMapper.toDTO(product.get()), HttpStatus.OK);
    }

    public Optional<ProductDTO> findById(Long id) {
        return productRepository.findById(id).map(product -> new ProductDTO(product.getId(), product.getName(), product.getDescripcion_producto(),product.getPresentation(),product.getStock(),product.getPrice()));
    }
    public List<ProductDTO> findAllProductoByName (String nombre) {
        List<ProductDTO> allproducts = findAllProducts();
        List<ProductDTO> products = new ArrayList<ProductDTO>();
        for (ProductDTO product : allproducts) {
            if (product.getName().toLowerCase().contains(nombre.trim().toLowerCase())) {
                products.add(product);
            }
        }
        return products;
    }
    public ProductDTO findProductoByName (String nombre) {
        List<ProductDTO> products = findAllProducts();
        for (ProductDTO product : products) {
            if (product.getName().toLowerCase().contains(nombre.trim().toLowerCase())) {
                return product;
            }
        }
        return null;
    }



}