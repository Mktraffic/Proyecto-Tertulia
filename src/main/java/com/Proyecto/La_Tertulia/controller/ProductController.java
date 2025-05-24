package com.Proyecto.La_Tertulia.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.Proyecto.La_Tertulia.dto.ProductDTO;
import com.Proyecto.La_Tertulia.dto.UsuarioDTO;
import com.Proyecto.La_Tertulia.service.ProductService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ProductController {
     
     @Autowired
    private ProductService productService; 

    @GetMapping("/manageProduct")
    public String chargeProductsToManage(Model model, HttpSession session) {
        List<ProductDTO> productos = productService.findAllProducts();
        model.addAttribute("Productos", productos);
        UsuarioDTO usuarioDTO = (UsuarioDTO) session.getAttribute("usuarioDTO");
        model.addAttribute("usuarioDTO", usuarioDTO);
        return "ProductManagement";
    }
    
    @PostMapping("/searchProduct")
    public String searchProduct(@RequestParam("codigoProducto") String nombre,Model model) {
        List<ProductDTO> products = productService.findProductByNameOrId(nombre);
        if(products.isEmpty()){
            products = productService.findAllProducts();
            model.addAttribute("error", "Producto no encontrado");
        }else{
            model.addAttribute("success", "Producto encontrado");
        }
        model.addAttribute("Productos",products);
        return "ProductManagement";
    }
    @GetMapping("/addProduct")
    public String showFormAddProduct(Model model,
            @RequestParam(value = "success", required = false) String success,
            @RequestParam(value = "error", required = false) String error) {
        model.addAttribute("ProductDTO", new ProductDTO());
        if (success != null) {
            model.addAttribute("mensajeExito", success);
        }
        if (error != null) {
            model.addAttribute("mensajeError", error);
        }
        return "ProductRegistration";
    }
    @PostMapping("/productRegistration")
    public String recordProduct(@ModelAttribute ProductDTO producto, Model model) {
        ProductDTO productDTO = new ProductDTO(
            null,
            producto.getType(),
            producto.getName(),
            producto.getDescripcion_producto(),
            producto.getPresentation(),
            producto.getStock(),
            producto.getPrice()
        );
        productService.addProductInDB(productDTO);
          model.addAttribute("ProductDTO", new ProductDTO());
        return "ProductRegistration";
    }
}
