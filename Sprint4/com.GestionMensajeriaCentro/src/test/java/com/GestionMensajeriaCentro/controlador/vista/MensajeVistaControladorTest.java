package com.GestionMensajeriaCentro.controlador.vista;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MensajeVistaControladorTest {

    private final MensajeVistaControlador controlador = new MensajeVistaControlador();

    @Test
    void vistaDevuelveVistaMensaje() {
        String vista = controlador.mostrarFormulario();
        assertEquals("altaMensaje", vista);
    }
}