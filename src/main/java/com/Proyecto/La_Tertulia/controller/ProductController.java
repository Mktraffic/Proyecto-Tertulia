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

  @GetMapping("/addProduct")
  public String showFormAddProduct(Model model) {
    return "TypeProduct";
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
    /*
     * VinoDTO vinoDTO = new VinoDTO(
     * null,
     * vinoDTO.getName(),
     * vinoDTO.getDescription(),
     * vinoDTO.getPresentation(),
     * vinoDTO.getStock(),
     * vinoDTO.getPrice());
     * // productService.addProductInDB(vinoDTO);
     */
    model.addAttribute("vinoDTO", new VinoDTO());
    return "WineRegistration";
  }

  @PostMapping("/vodkaRegistration")
  public String recordVodka(@ModelAttribute VodkaDTO vodka, Model model) {
    /*
     * VinoDTO vinoDTO = new VinoDTO(
     * null,
     * vinoDTO.getName(),
     * vinoDTO.getDescription(),
     * vinoDTO.getPresentation(),
     * vinoDTO.getStock(),
     * vinoDTO.getPrice());
     * // productService.addProductInDB(vinoDTO);
     */
    model.addAttribute("vodkaDTO", new VodkaDTO());
    return "VodkaRegistration";
  }

  @PostMapping("/beerRegistration")
  public String recordBeer(@ModelAttribute CervezaDTO cerveza, Model model) {
    /*
     * VinoDTO vinoDTO = new VinoDTO(
     * null,
     * vinoDTO.getName(),
     * vinoDTO.getDescription(),
     * vinoDTO.getPresentation(),
     * vinoDTO.getStock(),
     * vinoDTO.getPrice());
     * // productService.addProductInDB(vinoDTO);
     */
    model.addAttribute("cervezaDTO", new CervezaDTO());
    return "BeerRegistration";
  }

  @PostMapping("/schnappsRegistration")
  public String recordSchnapps(@ModelAttribute AguardienteDTO aguardiente, Model model) {
    /*
     * VinoDTO vinoDTO = new VinoDTO(
     * null,
     * vinoDTO.getName(),
     * vinoDTO.getDescription(),
     * vinoDTO.getPresentation(),
     * vinoDTO.getStock(),
     * vinoDTO.getPrice());
     * // productService.addProductInDB(vinoDTO);
     */
    model.addAttribute("aguardienteDTO", new AguardienteDTO());
    return "SchnappsRegistration";
  }

}
