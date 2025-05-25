package com.Proyecto.La_Tertulia.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.Proyecto.La_Tertulia.dto.AguardienteDTO;
import com.Proyecto.La_Tertulia.dto.CervezaDTO;
import com.Proyecto.La_Tertulia.dto.ProductDTO;
import com.Proyecto.La_Tertulia.dto.UsuarioDTO;
import com.Proyecto.La_Tertulia.dto.VinoDTO;
import com.Proyecto.La_Tertulia.dto.VodkaDTO;
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
        UsuarioDTO usuarioDTO = (UsuarioDTO) session.getAttribute("usuario");
        model.addAttribute("usuarioDTO", usuarioDTO);
        return "ProductManagement";
    }

    @PostMapping("/searchProduct")
    public String searchProduct(@RequestParam("codigoProducto") String nombre, Model model) {
        List<ProductDTO> products = productService.findProductByNameOrId(nombre);
        if (products.isEmpty()) {
            products = productService.findAllProducts();
            model.addAttribute("error", "Producto no encontrado");
        } else {
            model.addAttribute("success", "Producto encontrado");
        }
        model.addAttribute("Productos", products);
        return "ProductManagement";
    }

    @GetMapping("/fragment")
public String getProductFragment(@RequestParam("typeProduct") String typeProduct, Model model) {
    switch (typeProduct.toUpperCase()) {
        case "AGUARDIENTE":
            model.addAttribute("productDTO", new AguardienteDTO());
            return "FragmentsProducts :: aguardienteFields";
        case "CERVEZA":
            model.addAttribute("productDTO", new CervezaDTO());
            return "FragmentsProducts :: cervezaFields";
        case "VINO":
            model.addAttribute("productDTO", new VinoDTO());
            return "FragmentsProducts :: vinoFields";
        case "VODKA":
            model.addAttribute("productDTO", new VodkaDTO());
            return "FragmentsProducts :: vodkaFields";
        default:
            return "FragmentsProducts :: none";
    }
}


    @GetMapping("/addProduct")
    public String showFormAddProduct(Model model) {
       if (!model.containsAttribute("productDTO")) {
        model.addAttribute("productDTO", new VinoDTO());
    }
    return "ProductRegistration";
    }

  /*  @PostMapping("/productRegistration")
    public String recordProduct(@ModelAttribute ProductDTO producto, Model model) {
        ProductDTO productDTO = new ProductDTO(
                null,
                producto.getType(),
                producto.getName(),
                producto.getDescripcion(),
                producto.getPresentation(),
                producto.getStock(),
                producto.getPrice());
        productService.addProductInDB(productDTO);
        model.addAttribute("ProductDTO", new ProductDTO());
        return "ProductRegistration";
    }*/ 
}
