package com.Proyecto.La_Tertulia.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
 @GetMapping("/addProduct")
    public String agregarProducto(Model model) {
        model.addAttribute("productDTO", new ProductDTO());
        return "ProductRegistration";
    }
  @PostMapping("/productRegistration")
  public String showFormAddProduct(@ModelAttribute ProductDTO productDTO ,Model model) {
    String message = productService.addProductInDB(productDTO);
    if(message.equals("Producto agregado correctamente")){
      model.addAttribute("success", message);
    } else {
      model.addAttribute("error", message);
    }
    model.addAttribute("productDTO", new ProductDTO());
    return "ProductRegistration";
  }

  @PostMapping("/updateProduct")
  public String modifyProduct(@ModelAttribute ProductDTO productDTO , RedirectAttributes redirectAttributes) {
    String message = productService.updateProduct(productDTO);
     if (message.equalsIgnoreCase("Producto modificado correctamente")) {
        redirectAttributes.addFlashAttribute("success", message);
    } else if (message.equalsIgnoreCase("No hubo cambios en el producto")) {
        redirectAttributes.addFlashAttribute("error", message);
    }
    return "redirect:/manageProduct";
  }
}
