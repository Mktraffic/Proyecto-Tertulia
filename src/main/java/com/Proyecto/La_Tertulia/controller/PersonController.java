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
        UsuarioDTO usuarioDTO = (UsuarioDTO) session.getAttribute("usuario");
        model.addAttribute("usuarioDTO", usuarioDTO);
        return "PeopleManagement";
    }

    @PostMapping("/searchPerson")
    public String searchUser(@RequestParam("nombrePersona") String nombre, Model model) {
        List<UsuarioDTO> usuarios = usuarioService.findUserByNameOrID(nombre);
        if (usuarios.isEmpty()) {
            usuarios = usuarioService.findAllUsuarios();
            model.addAttribute("error", "Usuario no registrado");
        } else {
            model.addAttribute("success", "Usuario encontrado");
        }
        model.addAttribute("Usuarios", usuarios);
        return "PeopleManagement";
    }

    @GetMapping("/addPerson")
    public String addPerson(Model model) {
        model.addAttribute("usuarioDTO", new UsuarioDTO());
        return "UserRegistration";
    }

    @PostMapping("/UserRegistration")
    public String userRegister(@ModelAttribute UsuarioDTO usuario, Model model) {
        if (usuario.getUserName() != null || !usuario.getRol().getNombreRol().equals("Proveedor")) {
            if (usuarioService.validateExistUserName(usuario.getUserName())) {
                model.addAttribute("error", "El nombre de usuario ya esta registrado.");
                model.addAttribute("usuarioDTO", usuario);
                return "UserRegistration";
            }
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
            return "UserRegistration";
        }
        String nombreRol = usuario.getRol().getNombreRol();
        Rol rolGuardado = rolService.guardarRolSiNoExiste(nombreRol);
        usuario.setPersona(nuevaPersona);
        usuario.setRol(new RolDTO(rolGuardado.getId(), rolGuardado.getNombreRol()));
        usuarioService.addUsuarioInDB(usuario);
        model.addAttribute("success", "Usuario registrado correctamente.");
        model.addAttribute("usuarioDTO", new UsuarioDTO());
        return "UserRegistration";
    }

    @PostMapping("/updatePerson")
    public String updatePerson(@ModelAttribute UsuarioDTO usuario, Model model) {
        System.out.println("\n \n \nEntra a modificarUsuario");
        UsuarioDTO usuarioDTO=usuarioService.findUserByName(usuario.getUserName());
        usuarioDTO.getRol().setNombreRol(usuario.getRol().getNombreRol());
        usuarioDTO.setUserPassword(usuario.getUserPassword());
        System.out.println("\n \n \n Revisa modificacion de persona");
        System.out.println("\n \n \n \nNombre persona"+usuario.getPersona().getNombre());
        PersonaDTO personaDTO = personaService.updatePersona(usuario.getPersona());

        
        String message = "";
        if ("correo_electronico".equals(personaDTO.getNombre())) {
            message = "Correo electrónico ya vinculado a un usuario";
        } 
        if (!message.isEmpty()) {
            model.addAttribute("error", message);
            model.addAttribute("usuarioDTO", new UsuarioDTO());
            return "UserRegistration";
        }else{
            model.addAttribute("sucess", "Persona modificada correctamente");
        }
        return "redirect:/managePerson";
    }
}
