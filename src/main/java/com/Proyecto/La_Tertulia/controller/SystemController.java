package com.Proyecto.La_Tertulia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.Proyecto.La_Tertulia.dto.PersonaDTO;
import com.Proyecto.La_Tertulia.dto.RolDTO;
import com.Proyecto.La_Tertulia.dto.UsuarioDTO;
import com.Proyecto.La_Tertulia.model.Rol;
import com.Proyecto.La_Tertulia.service.PersonaService;
import com.Proyecto.La_Tertulia.service.RedirectionService;
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
    @Autowired
    private RedirectionService redirectionService;

    @GetMapping("/login")
    public String mostrarLogin(Model model) {
        model.addAttribute("usuarioDTO", new UsuarioDTO());
        return "Login";
    }

    @PostMapping("/loggeo")
    public String processLogin(@ModelAttribute UsuarioDTO usuarioDTO, Model model, HttpSession session) {
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
                case "INVALID_CREDENTIALS":
                    mensajeError = "Credenciales incorrectas.";
                    break;
                case "DISABLED_USER":
                    mensajeError = "Usuario sin acceso al sistema";
                    break;
                default:
                    mensajeError = "Credenciales incorrectas.";
                    break;
            }

            model.addAttribute("error", mensajeError);
            return "Login";
        }
        String rol = data[1];
        UsuarioDTO usuarioSession = searchPersonByUserName(usuarioDTO.getUserName());
        usuarioSession.getRol().setNombreRol(rol);
        session.setAttribute("usuario", usuarioSession);
        session.setAttribute("rol", rol);
        return redirectionService.redirectionUser(rol);
    }

    public UsuarioDTO searchPersonByUserName(String user_name) {
        List<UsuarioDTO> userList = usuarioService.findAllUsuarios();
        for (UsuarioDTO usuario : userList) {
            if (usuario.getUserName().equals(user_name)) {
                return usuario;
            }
        }
        return null;
    }

    @GetMapping("/logout")
    public String longOut(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/")
    public String showAdminRegistration(Model model) {
        model.addAttribute("usuarioDTO", new UsuarioDTO());
        return "AdminRegistration";
    }

    @PostMapping("/AdminRegistration")
    public String adminRegistration(@ModelAttribute UsuarioDTO usuario, Model model) {
        if (usuarioService.validateExistUserName(usuario.getUserName())) {
            model.addAttribute("error", "El nombre de usuario ya esta registrado.");
            model.addAttribute("usuarioDTO", usuario);
            return "AdminRegistration";
        }
        usuario.getPersona().setEstado(true);
        PersonaDTO nuevaPersona = personaService.addPersonaInDB(usuario.getPersona());
        usuario.setPersona(nuevaPersona);
        String message = "";
        if ("correo_electronico".equals(nuevaPersona.getNombre())) {
            message = "Correo electrónico ya vinculado a un usuario";
        } else if ("numero_documento".equals(nuevaPersona.getNombre())) {
            message = "Documento de identidad repetido";
        } else if ("Persona_menor".equals(nuevaPersona.getNombre())) {
            message = "La persona es menor de edad";
        }
        if (!message.isEmpty()) {
            model.addAttribute("error", message);
            model.addAttribute("usuarioDTO", new UsuarioDTO());
            return "AdminRegistration";
        }
        String nombreRol = "Administrador";
        Rol rolGuardado = rolService.guardarRolSiNoExiste(nombreRol);
        usuario.setRol(new RolDTO(rolGuardado.getId(), rolGuardado.getNombreRol()));
        usuarioService.addUsuarioInDB(usuario);
        model.addAttribute("success", "Administrador registrado correctamente.");
        model.addAttribute("usuarioDTO", new UsuarioDTO());
        return "AdminRegistration";
    }

    @GetMapping("/dashboardAdm")
    public String showAdministratorOptions(Model model) {
        model.addAttribute("usuarioDTO", new UsuarioDTO());
        return "AdministratorOptions";
    }

    @GetMapping("/dashboardSeller")
    public String showSellerOptions(Model model) {
        model.addAttribute("usuarioDTO", new UsuarioDTO());
        return "SellerOptions";
    }
}
