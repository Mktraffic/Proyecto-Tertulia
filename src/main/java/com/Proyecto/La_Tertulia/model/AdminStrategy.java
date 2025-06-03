package com.Proyecto.La_Tertulia.model;

import org.springframework.stereotype.Component;

@Component("Administrador")
public class AdminStrategy implements RedirectionStrategy{

    @Override
    public String redireccionar() {
        return "AdministratorOptions";
    }
}
