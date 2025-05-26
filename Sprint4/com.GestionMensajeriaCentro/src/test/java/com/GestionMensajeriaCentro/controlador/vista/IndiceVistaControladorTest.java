package com.GestionMensajeriaCentro.controlador.vista;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IndiceVistaControladorTest {

    private final IndiceVistaControlador controlador = new IndiceVistaControlador();

    @Test
    void debeRedirigirAIndiceAdmin() {
        Authentication auth = mock(Authentication.class);
        when(auth.getAuthorities()).thenReturn((List) Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));

        String vista = controlador.redirigirPorRol(auth);
        assertEquals("indiceAdmin", vista);
    }

    @Test
    void debeRedirigirAIndiceProfe() {
        Authentication auth = mock(Authentication.class);
        when(auth.getAuthorities()).thenReturn((List) Arrays.asList(new SimpleGrantedAuthority("ROLE_PROFESOR")));

        String vista = controlador.redirigirPorRol(auth);
        assertEquals("indiceProfe", vista);
    }

    @Test
    void debeRedirigirAErrorSiNoTieneRol() {
        Authentication auth = mock(Authentication.class);
        when(auth.getAuthorities()).thenReturn((List) Arrays.asList());

        String vista = controlador.redirigirPorRol(auth);
        assertEquals("error", vista);
    }
}
