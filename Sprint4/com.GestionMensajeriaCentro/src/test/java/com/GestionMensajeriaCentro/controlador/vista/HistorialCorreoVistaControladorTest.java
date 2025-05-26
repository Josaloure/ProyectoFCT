package com.GestionMensajeriaCentro.controlador.vista;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HistorialCorreoVistaControladorTest {

    private final HistorialCorreoVistaControlador controlador = new HistorialCorreoVistaControlador();

    @Test
    void vistaDevuelveVistaCorreo() {
        String vista = controlador.mostrarVista();
        assertEquals("historialCorreo", vista);
    }
}