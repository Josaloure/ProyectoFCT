package com.GestionMensajeriaCentro.controlador.vista;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/mensajes")
public class MensajeVistaControlador {

    @GetMapping("/crear")
    public String mostrarFormulario() {
        return "altaMensaje";
    }
}

