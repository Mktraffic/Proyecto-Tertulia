package com.Proyecto.La_Tertulia.model;

import org.springframework.stereotype.Component;

@Component("Vendedor")
public class SellerStrategy implements RedirectionStrategy {

    @Override
    public String redireccionar() {
       return "SellerOptions";
    }
    
}