package com.Proyecto.La_Tertulia.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Proyecto.La_Tertulia.dto.UsuarioDTO;


import jakarta.servlet.http.HttpSession;

@Controller
public class BuyController {

  @GetMapping("/manageBuy")
  public String chargeBuysToManage(Model model, HttpSession session) {
    // List<BuyDTO> compras = buyService.findAllCompras();
    // model.addAttribute("Compras", compras);
    UsuarioDTO usuarioDTO = (UsuarioDTO) session.getAttribute("usuario");
    model.addAttribute("usuarioDTO", usuarioDTO);
    return "BuyManagement";
  }
/*
  @PostMapping("/searchBuy")
  public String searchBuy(@RequestParam("fechaInicial") LocalDate initialDate,
      @RequestParam("fechaFinal") LocalDate finaldate, Model model) {
    List<BuyDTO> compras = buyService.obtenerComprasEntreFechas(initialDate, finaldate);//Servicio compras
    if (compras.isEmpty()) {
      compras = buyService.findAllSales();
      model.addAttribute("error", "No hay compras registradas en ese rango de fechas");
    } else {
      model.addAttribute("success", "Compras realizadas");
    }
    model.addAttribute("Compras", compras);
    return "SalesManagement";
  }
  @PostMapping("/deleteBuy")
    public String deleteBuy(@RequestParam("id") Long id) {
        // Logica para eliminar compra, deben de quitar la cantidad añadida del
        // producto

        return "redirect:/manageBuy";
    }
    @GetMapping("/addBuy")
    public String showFormAddBuy(Model model) {
        model.addAttribute("BuyDTO", new BuyDTO());
        return "BuyRegistration";
    }
    @PostMapping("/buyRegistration")
    public String buyRegistration(@ModelAttribute BuyDTO buy, Model model) {
       
        model.addAttribute("buyDTO", new BuyDTO());
        return "redirect:/buyRegistration";
    }*/
}
