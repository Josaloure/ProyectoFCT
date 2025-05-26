package com.GestionMensajeriaCentro.controlador;

import com.GestionMensajeriaCentro.modelo.Usuario;
import com.GestionMensajeriaCentro.servicio.UsuarioServicio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfesorControladorTest {

    @Mock
    private UsuarioServicio usuarioServicio;

    @InjectMocks
    private ProfesorControlador controlador;

    @Test
    void obtenerUsuariosDevuelveLista() {
        List<Usuario> lista = List.of(new Usuario());
        when(usuarioServicio.findAll()).thenReturn(lista);

        List<Usuario> resultado = controlador.obtenerUsuarios();

        assertEquals(lista, resultado);
        verify(usuarioServicio).findAll();
    }
}