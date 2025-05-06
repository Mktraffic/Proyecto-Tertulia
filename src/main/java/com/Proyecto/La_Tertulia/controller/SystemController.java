package com.Proyecto.La_Tertulia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Proyecto.La_Tertulia.dto.UsuarioDTO;

@Controller
public class SystemController {
@GetMapping("/login")
    public String mostrarLogin(Model model,
                               @RequestParam(value = "success", required = false) String success,
                               @RequestParam(value = "error", required = false) String error) {
        model.addAttribute("usuarioDTO", new UsuarioDTO());
        if (success != null) {
            model.addAttribute("mensajeExito", success);
        }
        if (error != null) {
            model.addAttribute("mensajeError", error);
        }
        return "Login";
    }
    @GetMapping("/")
    public String mostrarFormularioRegistroUsuarios(Model model,
                                         @RequestParam(value = "success", required = false) String success,
                                         @RequestParam(value = "error", required = false) String error) {
        model.addAttribute("usuarioDTO", new UsuarioDTO());
        if (success != null) {
            model.addAttribute("mensajeExito", success);
        }
        if (error != null) {
            model.addAttribute("mensajeError", error);
        }
        return "UserRegistration";
    }
}
