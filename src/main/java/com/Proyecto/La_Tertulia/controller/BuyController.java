package com.Proyecto.La_Tertulia.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.Proyecto.La_Tertulia.dto.UsuarioDTO;

import jakarta.servlet.http.HttpSession;


@Controller
public class BuyController {
    
   @GetMapping("/manageBuy")
    public String chargeUsersToManage(Model model, HttpSession session) {
      //  List<UsuarioDTO> compras = usuarioService.findAllUsuarios();
      //  model.addAttribute("Compras", compras);
        UsuarioDTO usuarioDTO = (UsuarioDTO) session.getAttribute("usuarioDTO");
        model.addAttribute("usuarioDTO", usuarioDTO);
        return "BuyManagement";
    }
}
