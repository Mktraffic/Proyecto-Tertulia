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

  @PostMapping("/addProduct")
  public String showFormAddProduct(@ModelAttribute ProductDTO productDTO ,Model model) {
    String message = productService.addProductInDB(productDTO);
    if(message.equals("Producto agregado correctamente")){
      model.addAttribute("success", message);
    } else {
      model.addAttribute("error", message);
    }
    return "TypeProduct";
  }

  @GetMapping("/formProduct")
  public String showFormModifyProduct(@ModelAttribute String nameProduct ,Model model) {
    //
    List<ProductDTO> product = productService.findProductoByName(nameProduct); //obtiene la lista de productos que en el nombre contengan el que buscamos
    //
    return "TypeProduct";
  }

  @PostMapping("/modifyProduct")
  public String modifyProduct(@ModelAttribute ProductDTO productDTO ,Model model) {
    String message = productService.updateProduct(productDTO);
    if (message.equals("Producto actualizado correctamente")) {
      model.addAttribute("success", message);
    } else {
      model.addAttribute("error", message);
    }
    return "formProduct";
  }

  @PostMapping("/openProductRegister")
  public String formularyProduct(@RequestParam("typeProduct") String product, Model model) {
    switch (product) {
      case "AGUARDIENTE":
        model.addAttribute("aguardienteDTO", new AguardienteDTO());
        return "SchnappsRegistration";
      case "CERVEZA":
        model.addAttribute("cervezaDTO", new CervezaDTO());
        return "BeerRegistration";
      case "VINO":
        model.addAttribute("vinoDTO", new VinoDTO());
        return "WineRegistration";
      case "VODKA":
        model.addAttribute("vodkaDTO", new VodkaDTO());
        return "VodkaRegistration";
    }
    return null;
  }

  @PostMapping("/wineRegistration")
  public String recordWine(@ModelAttribute VinoDTO vino, Model model) {
    System.out.println("Registro de vino: " + vino + "---------------------------------------------------------------------");
    String message = productService.saveProduct(vino);
    if (message.contains("Producto registrado correctamente")) {
      model.addAttribute("Producto registrado correctamente", message);
    } else {
      model.addAttribute("Error al resgitrar el producto: ", message);
    }
    model.addAttribute("vinoDTO", new VinoDTO());
    return "wineRegistration";
  }

  @PostMapping("/vodkaRegistration")
  public String recordVodka(@ModelAttribute VodkaDTO vodka, Model model) {
    String message = productService.saveProduct(vodka);
    if (message.contains("Producto registrado correctamente")) {
      model.addAttribute("Producto registrado correctamente", message);
    } else {
      model.addAttribute("Error al resgitrar el producto: ", message);
    }
    model.addAttribute("vodkaDTO", new VodkaDTO());
    return "vodkaRegistration";
  }

  @PostMapping("/beerRegistration")
  public String recordBeer(@ModelAttribute CervezaDTO cerveza, Model model) {
    String message = productService.saveProduct(cerveza);
    if (message.contains("Producto registrado correctamente")) {
      model.addAttribute("success", message);
    } else {
      model.addAttribute("Error al resgitrar el producto: ", message);
    }
    model.addAttribute("cervezaDTO", new CervezaDTO());
    return "beerRegistration";
  }

  @PostMapping("/schnappsRegistration")
  public String recordSchnapps(@ModelAttribute AguardienteDTO aguardiente, Model model) {
    String message = productService.saveProduct(aguardiente);
    if (message.contains("Producto registrado correctamente")) {
      model.addAttribute("Producto registrado correctamente", message);
    } else {
      model.addAttribute("Error al resgitrar el producto: ", message);
    }
    model.addAttribute("aguardienteDTO", new AguardienteDTO());
    return "schnappsRegistration";
  }

}
