package com.Proyecto.La_Tertulia.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.Proyecto.La_Tertulia.model.RedirectionStrategy;

@Service
public class RedirectionService {
    private final Map<String, RedirectionStrategy> strategies;

    public RedirectionService(Map<String, RedirectionStrategy> strategies) {
        this.strategies = strategies;
    }

    public String redirectionUser(String rol) {
        RedirectionStrategy strategy = strategies.get(rol);
        if (strategy != null) {
            return strategy.redireccionar();
        }
        throw new IllegalArgumentException("No hay estrategia de redirección para: " + rol);
    }
}
