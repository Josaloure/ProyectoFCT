package com.GestionMensajeriaCentro.controlador;

import com.GestionMensajeriaCentro.servicio.UsuarioServicio;
import com.GestionMensajeriaCentro.modelo.Usuario;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioControladorTest {

    @Mock
    private UsuarioServicio usuarioServicio;

    @Mock
    private Model model;

    @InjectMocks
    private UsuarioControlador usuarioControlador;

    @Nested
    class VistaUsuarios {
        @Test
        void debeAgregarUsuariosAlModeloYRetornarVista() {
            when(usuarioServicio.findAll()).thenReturn(List.of(new Usuario()));

            String vista = usuarioControlador.vistaUsuarios(model);

            verify(model).addAttribute(eq("usuarios"), anyList());
            assertEquals("usuarios/lista", vista);
        }
    }

    @Nested
    class ObtenerUsuario {
        @Test
        void debeRetornarUsuarioSiExiste() {
            Usuario u = new Usuario();
            when(usuarioServicio.findById(1L)).thenReturn(Optional.of(u));

            ResponseEntity<Usuario> response = usuarioControlador.obtener(1L);

            assertEquals(200, response.getStatusCodeValue());
            assertEquals(u, response.getBody());
        }

        @Test
        void debeRetornar404SiNoExiste() {
            when(usuarioServicio.findById(1L)).thenReturn(Optional.empty());

            ResponseEntity<Usuario> response = usuarioControlador.obtener(1L);

            assertEquals(404, response.getStatusCodeValue());
        }
    }
    @Nested
    class OtrosMetodos {

        @Test
        void listarDevuelveUsuariosJson() {
            when(usuarioServicio.findAll()).thenReturn(List.of(new Usuario()));
            ResponseEntity<List<Usuario>> response = usuarioControlador.listar();
            assertEquals(200, response.getStatusCodeValue());
            assertFalse(response.getBody().isEmpty());
        }

        @Test
        void mostrarFormularioDevuelveVista() {
            String vista = usuarioControlador.mostrarFormulario(model);
            assertEquals("usuarios/formulario", vista);
            verify(model).addAttribute(eq("usuario"), any(Usuario.class));
        }

        @Test
        void actualizarUsuarioExistente() {
            Usuario usuario = new Usuario();
            when(usuarioServicio.findById(1L)).thenReturn(Optional.of(new Usuario()));
            when(usuarioServicio.save(any())).thenReturn(usuario);

            ResponseEntity<?> response = usuarioControlador.actualizar(1L, usuario);
            assertEquals(200, response.getStatusCodeValue());
        }

        @Test
        void actualizarUsuarioNoExistente() {
            Usuario usuario = new Usuario();
            when(usuarioServicio.findById(1L)).thenReturn(Optional.empty());

            ResponseEntity<?> response = usuarioControlador.actualizar(1L, usuario);
            assertEquals(404, response.getStatusCodeValue());
        }

        @Test
        void eliminarUsuarioExistente() {
            when(usuarioServicio.deleteById(1L)).thenReturn(true);
            ResponseEntity<?> response = usuarioControlador.eliminar(1L);
            assertEquals(200, response.getStatusCodeValue());
        }

        @Test
        void eliminarUsuarioNoExistente() {
            when(usuarioServicio.deleteById(1L)).thenReturn(false);
            ResponseEntity<?> response = usuarioControlador.eliminar(1L);
            assertEquals(404, response.getStatusCodeValue());
        }
    }

}