package com.GestionMensajeriaCentro.controlador.vista;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TablonProfesorVistaControladorTest {

    private final TablonProfesorVistaControlador controlador = new TablonProfesorVistaControlador();

    @Test
    void vistaDevuelveVistaTablonProfesor() {
        String vista = controlador.mostrarVista();
        assertEquals("tablonProfesor", vista);
    }
}