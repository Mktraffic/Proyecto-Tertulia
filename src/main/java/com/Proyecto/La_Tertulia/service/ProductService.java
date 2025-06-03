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
                List<ProductDTO> productosExistentes = findAllProducts().stream()
                    .filter(p -> p.getName().equalsIgnoreCase(productdto.getName())
                        && p.getDescription().equalsIgnoreCase(productdto.getDescription())
                        && p.getPresentation().equalsIgnoreCase(productdto.getPresentation())
                        && p.getPrice() == productdto.getPrice())
                    .collect(Collectors.toList());

                if (!productosExistentes.isEmpty()) {
                    ProductDTO existente = productosExistentes.get(0);
                    existente.setStock(existente.getStock() + productdto.getStock());
                    productRepository.save(productMapper.toEntity(existente));
                    result = "Producto ya existente, stock actualizado correctamente";
                } else {
                    productdto.setId(null);
                    productRepository.save(productMapper.toEntity(productdto));
                    result = "Producto agregado correctamente";
                }
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
            Optional<Product> optionalProduct = productRepository.findById(datos.getId());
            if (optionalProduct.isPresent()) {
                Product original = optionalProduct.get();
                boolean changed = false;

                if (!original.getName().equals(datos.getName())) {
                    original.setName(datos.getName());
                    changed = true;
                }
                if (original.getPrice() != datos.getPrice()) {
                    original.setPrice(datos.getPrice());
                    changed = true;
                }
                if (original.getStock() != datos.getStock()) {
                    original.setStock(datos.getStock());
                    changed = true;
                }

                if (changed) {
                    productRepository.save(original);
                    result = "Producto modificado correctamente";
                } else {
                    result = "No hubo cambios en el producto";
                }
            } else {
                result = "Producto no encontrado";
            }
        } catch (Exception e) {
            result = "Error en la modificacion del producto: " + datos.getName();
            System.out.println("El error es: " + e.getMessage());
        }
        return result;
    }
       public List<String> productCategories() {
        return findAllProducts().stream()
                .map(ProductDTO::getType)
                .distinct()
                .collect(Collectors.toList());
    }
    public ArrayList<ProductDTO> productsByCategoryParam(String type){
        ArrayList<ProductDTO> productList = new ArrayList<>();
        for (ProductDTO productDTO : findAllProducts()) {
            if(productDTO.getType().equalsIgnoreCase(type)){
                productList.add(productDTO);
            }
        }
        return productList;
    }

     public List<ProductDTO> productsByCategory(String categoria) {
        List<Product> productos = productRepository.findByType(categoria);
        return productos.stream()
                .map(p -> new ProductDTO(p.getId(), p.getName()))
                .collect(Collectors.toList());
    }
    public List<String> presentationByProductid(Long productoId) {
        return productRepository.findPresentationByProductId(productoId);
    }

    public double productPrice(Long productoId, String presentacion) {
        return productRepository.findPriceByProductIdAndPresentation(productoId, presentacion);
    }
}
