package com.GestionMensajeriaCentro.controlador.vista;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TareaVistaControladorTest {

    private final TareaVistaControlador controlador = new TareaVistaControlador();

    @Test
    void vistaAltaDevuelveVistaCorrecta() {
        String vista = controlador.mostrarVistaTareas();
        assertEquals("altaTarea", vista);
    }

    @Test
    void vistaListaDevuelveVistaCorrecta() {
        String vista = controlador.mostrarVistaListaTareas(); // <- cambio aquí
        assertEquals("tareas/tareaLista", vista);
    }
}
