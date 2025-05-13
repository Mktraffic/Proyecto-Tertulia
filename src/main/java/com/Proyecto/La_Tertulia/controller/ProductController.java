package com.Proyecto.La_Tertulia.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.Proyecto.La_Tertulia.dto.ProductDTO;
import com.Proyecto.La_Tertulia.service.ProductService;

import jakarta.servlet.http.HttpSession;
@Controller
public class ProductController {
     
     @Autowired
    private ProductService productService; 

    @GetMapping("/productManager")
    public String chargeUsersToManage(Model model, HttpSession session) {
        List<ProductDTO> productos = productService.findAllProducts();
        model.addAttribute("Productos", productos);
       // UsuarioDTO usuarioDTO = (UsuarioDTO) session.getAttribute("usuarioDTO");
       // model.addAttribute("usuarioDTO", usuarioDTO);
        return "ProductManagement";
    }
}
