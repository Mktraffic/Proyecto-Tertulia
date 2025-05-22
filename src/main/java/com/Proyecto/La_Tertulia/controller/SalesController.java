package com.Proyecto.La_Tertulia.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.Proyecto.La_Tertulia.dto.UsuarioDTO;
import com.Proyecto.La_Tertulia.service.VentaService;

import jakarta.servlet.http.HttpSession;


@Controller
public class SalesController {
    @Autowired
    private VentaService ventaService;
    
   @GetMapping("/manageSales")
    public String chargeSales(Model model, HttpSession session) {
        //List<VentaDTO> ventas = ventaService.
       // model.addAttribute("Usuarios", ventas);
        UsuarioDTO usuarioDTO = (UsuarioDTO) session.getAttribute("usuarioDTO");
        model.addAttribute("usuarioDTO", usuarioDTO);
        return "SalesManagement";
    }
}
