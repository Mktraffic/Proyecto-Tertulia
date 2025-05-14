package com.Proyecto.La_Tertulia.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

import com.Proyecto.La_Tertulia.dto.PersonaDTO;
import com.Proyecto.La_Tertulia.dto.RolDTO;
import com.Proyecto.La_Tertulia.dto.UsuarioDTO;
import com.Proyecto.La_Tertulia.model.Rol;
import com.Proyecto.La_Tertulia.service.PersonaService;
import com.Proyecto.La_Tertulia.service.RolService;
import com.Proyecto.La_Tertulia.service.UsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
public class SystemController {
    @Autowired
    private PersonaService personaService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private RolService rolService;

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

    @PostMapping("/loggeo")
    public String procesarLogin(@ModelAttribute UsuarioDTO usuarioDTO, Model model, HttpSession session) {
        try {
            String[] data = usuarioService
                    .validateUserByUserName(usuarioDTO.getUserName(), usuarioDTO.getUserPassword())
                    .split(",");
            boolean isAuthenticated = Boolean.parseBoolean(data[0].trim());

            if (!isAuthenticated) {
                String errorCode = data.length > 1 ? data[1].trim() : "UNKNOWN_ERROR";
                String mensajeError = "";
                switch (errorCode) {
                    case "USER_NOT_FOUND":
                        mensajeError = "El usuario no existe.";
                        break;
                    case "WRONG_PASSWORD":
                        mensajeError = "Contraseña incorrecta.";
                        break;
                    default:
                        mensajeError = "Credenciales incorrectas.";
                        break;
                }

                model.addAttribute("error", mensajeError);
                return "Login";
            }
            String rol = data[1];
            session.setAttribute("usuario", searchPersonByUserName(usuarioDTO.getUserName()));
            session.setAttribute("rol", rol);
            switch (rol) {
                case "Administrador":
                    return "AdministratorOptions";
                case "Vendedor":
                    return "SellerOptions";
            }
        } catch (Exception e) {
            model.addAttribute("error", "Ocurrió un error inesperado. Inténtalo de nuevo.");
            return "Login";
        }
        return null;
    }

    public String searchPersonByUserName(String user_name) {
        List<UsuarioDTO> userList = usuarioService.findAllUsuarios();
        String nombre = "";
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUserName().equals(user_name)) {
                nombre = userList.get(i).getPersona().getNombre() + " " + userList.get(i).getPersona().getApellido();
            }
        }
        return nombre;
    }

    @GetMapping("/logout")
    public String cerrarSesion(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
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
        return "AdminRegistration";
    }

    @PostMapping("/AdminRegistration")
    public String userRegister(@ModelAttribute UsuarioDTO usuario, Model model) {
        if (personaService.findById(usuario.getPersona().getDocumentoIdentidad()).isPresent()) {
            model.addAttribute("error", "El documento de identidad ya está registrado.");
            model.addAttribute("usuarioDTO", usuario);
            return "AdminRegistration";
        }
        if (usuarioService.validateExistUserName(usuario.getUserName())) {
            model.addAttribute("error", "El nombre de usuario ya esta registrado.");
            model.addAttribute("usuarioDTO", usuario);
            return "AdminRegistration";
        }
        try {
            PersonaDTO persona = personaService.createPerson(usuario.getPersona());

            PersonaDTO nuevaPersona = personaService.addPersonaInDB(persona);
            String nombreRol = "Administrador";
            Rol rolGuardado = rolService.guardarRolSiNoExiste(nombreRol);

            usuario.setPersona(nuevaPersona);
            usuario.setRol(new RolDTO(rolGuardado.getId(), rolGuardado.getNombreRol()));
            usuarioService.addUsuarioInDB(usuario);
            model.addAttribute("success", "Administrador registrado correctamente.");
            model.addAttribute("usuarioDTO", new UsuarioDTO());
            return "AdminRegistration";
        } catch (Exception e) {
            model.addAttribute("error", "Ocurrió un error inesperado. Inténtalo de nuevo.");
            model.addAttribute("usuarioDTO", usuario);
            return "AdminRegistration";
        }
    }

    @GetMapping("/dashboardAdm")
    public String administratorOptions(Model model) {
        model.addAttribute("usuarioDTO", new UsuarioDTO());
        return "AdministratorOptions";
    }

    @GetMapping("/dashboardSeller")
    public String sellerOptions(Model model) {
        model.addAttribute("usuarioDTO", new UsuarioDTO());
        return "SellerOptions";
    }
}
