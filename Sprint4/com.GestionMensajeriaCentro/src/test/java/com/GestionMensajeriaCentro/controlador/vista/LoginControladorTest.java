package com.GestionMensajeriaCentro.controlador.vista;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LoginControladorTest {

    private final LoginControlador controlador = new LoginControlador();

    @Test
    void loginDevuelveVista() {
        String vista = controlador.mostrarLogin();
        assertEquals("login", vista);
    }
}