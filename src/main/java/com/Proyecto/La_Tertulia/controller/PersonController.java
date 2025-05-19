package com.Proyecto.La_Tertulia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Proyecto.La_Tertulia.dto.PersonaDTO;
import com.Proyecto.La_Tertulia.dto.RolDTO;
import com.Proyecto.La_Tertulia.dto.UsuarioDTO;
import com.Proyecto.La_Tertulia.model.Rol;
import com.Proyecto.La_Tertulia.service.PersonaService;
import com.Proyecto.La_Tertulia.service.RolService;
import com.Proyecto.La_Tertulia.service.UsuarioService;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class PersonController {
    @Autowired
    private PersonaService personaService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private RolService rolService;

    @GetMapping("/managePerson")
    public String chargeUsersToManage(Model model, HttpSession session) {
        List<UsuarioDTO> usuarios = usuarioService.findAllUsuarios();
        model.addAttribute("Usuarios", usuarios);
        UsuarioDTO usuarioDTO = (UsuarioDTO) session.getAttribute("usuarioDTO");
        model.addAttribute("usuarioDTO", usuarioDTO);
        return "PeopleManagement";
    }
    @PostMapping("/searchPerson")
    public String postMethodName(@RequestParam("nombrePersona") String nombre,Model model) {
        List<UsuarioDTO> usuarios = usuarioService.findUserByNameOrID(nombre);
         model.addAttribute("Usuarios",usuarios);
        return "PeopleManagement";
    }
    

    @GetMapping("/addPerson")
    public String addPerson(Model model,
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

    @PostMapping("/UserRegistration")
    public String userRegister(@ModelAttribute UsuarioDTO usuario, Model model) {
        if (personaService.findById(usuario.getPersona().getDocumentoIdentidad()).isPresent()) {
            model.addAttribute("error", "El documento de identidad ya está registrado.");
            model.addAttribute("usuarioDTO", usuario);
            return "UserRegistration";
        }
        if (usuarioService.validateExistUserName(usuario.getUserName())) {
            model.addAttribute("error", "El nombre de usuario ya esta registrado.");
            model.addAttribute("usuarioDTO", usuario);
            return "UserRegistration";
        }
        try {
            PersonaDTO nuevaPersona = personaService.addPersonaInDB(usuario.getPersona());
            usuario.setPersona(nuevaPersona);
            String nombreRol = usuario.getRol().getNombreRol();
            Rol rolGuardado = rolService.guardarRolSiNoExiste(nombreRol);

            usuario.setPersona(nuevaPersona);
            usuario.setRol(new RolDTO(rolGuardado.getId(), rolGuardado.getNombreRol()));
            if (nombreRol.equals("Proveedor")) {
                usuario.setUserName(null);
                usuario.setUserPassword(null);
            }
            usuarioService.addUsuarioInDB(usuario);
            model.addAttribute("success", "Usuario registrado correctamente.");
            model.addAttribute("usuarioDTO", new UsuarioDTO());
            return "UserRegistration";
        } catch (Exception e) {
            model.addAttribute("error", "Ocurrió un error inesperado. Inténtalo de nuevo.");
            model.addAttribute("usuarioDTO", usuario);
            return "UserRegistration";
        }
    }
}
