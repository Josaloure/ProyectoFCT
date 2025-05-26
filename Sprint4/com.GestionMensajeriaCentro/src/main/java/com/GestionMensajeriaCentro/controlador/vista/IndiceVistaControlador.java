package com.GestionMensajeriaCentro.controlador.vista;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndiceVistaControlador {

    @GetMapping("/indice")
    public String redirigirPorRol(Authentication auth) {
        if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return "indiceAdmin";
        } else if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PROFESOR"))) {
            return "indiceProfe";
        }
        return "error";
    }
}
