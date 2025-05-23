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
    public String mostrarLogin(Model model) {
        model.addAttribute("usuarioDTO", new UsuarioDTO());
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
    public String mostrarFormularioRegistroUsuarios(Model model) {
        model.addAttribute("usuarioDTO", new UsuarioDTO());
        return "AdminRegistration";
    }

    @PostMapping("/AdminRegistration")
    public String userRegister(@ModelAttribute UsuarioDTO usuario, Model model) {
        usuario.getPersona().setEstado(true);
        PersonaDTO nuevaPersona = personaService.addPersonaInDB(usuario.getPersona());
        usuario.setPersona(nuevaPersona);
       if("correo_electronico".equals(nuevaPersona.getNombre())){
            model.addAttribute("error", "Correo electronico ya vinculado a un usuario");
        }else if("numero_documento".equals(nuevaPersona.getNombre())){
            model.addAttribute("error", "Documento de identidad repetido");
        }else if("Persona_menor".equals(nuevaPersona.getNombre())){
            model.addAttribute("error", "La persona es menor de edad");
        }else{
            model.addAttribute("success", "Usuario registrado exitosamente");
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
