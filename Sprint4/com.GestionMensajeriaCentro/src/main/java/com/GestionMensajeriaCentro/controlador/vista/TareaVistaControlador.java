package com.GestionMensajeriaCentro.controlador.vista;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profesor/tareas")
public class TareaVistaControlador {

    @GetMapping("/crear")
    public String mostrarVistaTareas() {
        return "altaTarea";
    }

    @GetMapping("/lista")
    public String mostrarVistaListaTareas() {
        return "tareas/tareaLista";
    }
}
