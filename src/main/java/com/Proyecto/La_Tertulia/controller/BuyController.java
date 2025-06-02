package com.Proyecto.La_Tertulia.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Proyecto.La_Tertulia.dto.CompraDTO;
import com.Proyecto.La_Tertulia.dto.DetalleCompraDTO;
import com.Proyecto.La_Tertulia.dto.DetalleVentaDTO;
import com.Proyecto.La_Tertulia.dto.ProductDTO;
import com.Proyecto.La_Tertulia.dto.UsuarioDTO;
import com.Proyecto.La_Tertulia.dto.VentaDTO;
import com.Proyecto.La_Tertulia.service.CompraService;
import com.Proyecto.La_Tertulia.service.ProductService;
import com.Proyecto.La_Tertulia.service.UsuarioService;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class BuyController {
  @Autowired
  private CompraService buyService;
  @Autowired
  private ProductService productService;

  @Autowired
  private UsuarioService usuarioService;

  @GetMapping("/manageBuy")
  public String chargeBuysToManage(@RequestParam(required = false) String error, Model model, HttpSession session) {
    if (error != null) {
      model.addAttribute("error", error);
    }
    UsuarioDTO usuarioDTO = (UsuarioDTO) session.getAttribute("usuario");
    if (usuarioDTO == null) {
      return "redirect:/login";
    }
    List<CompraDTO> compras = buyService.findAllCompras();
    model.addAttribute("compras", compras);
    model.addAttribute("usuario", usuarioDTO);
    return "BuyManagement";
  }

  @PostMapping("/searchBuy")
  public String searchBuy(@RequestParam("fechaInicial") LocalDate initialDate,
      @RequestParam("fechaFinal") LocalDate finaldate, Model model) {
    List<CompraDTO> compras = buyService.obtenerComprasEntreFechas(initialDate, finaldate);
    if (compras.isEmpty()) {
      compras = buyService.findAllCompras();
      model.addAttribute("error", "No hay compras registradas en ese rango de fechas");
    } else {
      model.addAttribute("success", "Compras realizadas");
    }
    model.addAttribute("compras", compras);
    return "SalesManagement";
  }

  @GetMapping("/addDetailBuy")
  public String showFormAddBuy(Model model) {
    List<String> categorias = productService.productCategories();
    model.addAttribute("categorias", categorias);
    model.addAttribute("detalleCompraDTO", new DetalleCompraDTO());
    return "DetailsBuyRegistration";
  }

  @PostMapping("/buyDetails")
  public String saveDetailBuy(@ModelAttribute DetalleCompraDTO detalleCompra, RedirectAttributes redirectAttributes,
      Model model) {
    ProductDTO product = productService.findById(detalleCompra.getProducto().getId()).orElse(null);
    if (product == null) {
      redirectAttributes.addFlashAttribute("error", "Producto no encontrado");
      model.addAttribute("detalleCompraDTO", detalleCompra);
      return "redirect:/addDetailsBuy";
    }
    detalleCompra.setNombreProducto(product.getName());
    detalleCompra.getProducto().setType(product.getType());
    detalleCompra.getProducto().setGradosAlcohol(product.getGradosAlcohol());
    detalleCompra.setSubtotal(detalleCompra.getCantidad() * detalleCompra.getPrecioUnitario());
    detalleCompra.setCompra(new CompraDTO());
    ArrayList<DetalleCompraDTO> buyDetailsList = buyService.getBuyDetailsList();
    if (!buyDetailsList.isEmpty()) {
      for (DetalleCompraDTO detalleCompraDTO : buyDetailsList) {
        if (detalleCompraDTO.getProducto().getId() == detalleCompra.getProducto().getId()) {
          detalleCompraDTO.setCantidad(detalleCompraDTO.getCantidad() + detalleCompra.getCantidad());
          detalleCompraDTO.setSubtotal(detalleCompraDTO.getPrecioUnitario() * detalleCompraDTO.getCantidad());
          redirectAttributes.addFlashAttribute("success", "Producto actualizado en la compra");
          return "redirect:/addDetailBuy";
        }
      }
    }
    buyService.guardarDetallesCompra(detalleCompra);
    redirectAttributes.addFlashAttribute("success", "Producto agregado a la Compra");
    return "redirect:/addDetailBuy";
  }

  @GetMapping("/finishBuy")
  private String showFinishBuyForm(Model model) {
    model.addAttribute("registroCompra", buyService.getBuyDetailsList());
    model.addAttribute("proveedores", usuarioService.obtainSuppliers());
    return "BuyRegistration";
  }

  @PostMapping("/removeDetailBuy")
  public String eliminarCompraProducto(@RequestParam("index") int index, RedirectAttributes redirectAttributes) {
    System.out.println("\n \n \n \n indice " + index);
    ArrayList<DetalleCompraDTO> buyDetailsList = buyService.getBuyDetailsList();
    if (index >= 0 && index < buyDetailsList.size()) {
      buyDetailsList.remove(index);
      if (buyDetailsList.isEmpty()) {
        redirectAttributes.addFlashAttribute("success", "Todos los productos han sido eliminados de la compra");
      } else {
        redirectAttributes.addFlashAttribute("success", "Producto eliminado de la compra");
      }
    } else {
      redirectAttributes.addFlashAttribute("error", "Índice inválido para eliminar producto");
    }
    return "redirect:/finishBuy";
  }

  @PostMapping("/buyRegistration")
  public String buyRegistration(@RequestParam("nombreProveedor") String nombreProveedor,
      RedirectAttributes redirectAttributes, HttpSession session) {

    ArrayList<DetalleCompraDTO> buyDetailsList = buyService.getBuyDetailsList();
    UsuarioDTO usuarioDTO = (UsuarioDTO) session.getAttribute("usuario");
    if (!buyDetailsList.isEmpty()) {
      CompraDTO compra = new CompraDTO(null, LocalDate.now(), usuarioDTO, nombreProveedor, obtainTotalBuy(),
          new ArrayList<DetalleCompraDTO>());
      for (DetalleCompraDTO detalleCompraDTO : buyDetailsList) {
        detalleCompraDTO.setCompra(compra);
      }
      compra.setDetalles(buyDetailsList);
      buyService.registrarCompra(compra);
      buyDetailsList.clear();
      redirectAttributes.addFlashAttribute("success", "Compra realizada exitosamente");
    } else {
      redirectAttributes.addFlashAttribute("error", "No hay productos en la compra");
    }
    return "redirect:/manageBuy";
  }

  private double obtainTotalBuy() {
    double totalBuy = 0;
    ArrayList<DetalleCompraDTO> buyDetailsList = buyService.getBuyDetailsList();
    for (DetalleCompraDTO detalleCompraDTO : buyDetailsList) {
      totalBuy += detalleCompraDTO.getSubtotal();
    }
    return totalBuy;
  }
}
