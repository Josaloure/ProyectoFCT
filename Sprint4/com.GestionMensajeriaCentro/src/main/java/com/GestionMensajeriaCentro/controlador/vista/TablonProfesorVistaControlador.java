package com.GestionMensajeriaCentro.controlador.vista;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profesor")
public class TablonProfesorVistaControlador {

    @GetMapping("/tablon")
    public String mostrarVista() {
        return "tablonProfesor";
    }

}
