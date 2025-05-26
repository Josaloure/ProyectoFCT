package com.GestionMensajeriaCentro.controlador.vista;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controlador de vistas para el historial de correos.
 * Renderiza la página Thymeleaf al acceder a /correo.
 */
@Controller
@RequestMapping("/correo")
public class HistorialCorreoVistaControlador {

    @GetMapping
    public String mostrarVista() {
        return "historialCorreo";
    }

}
