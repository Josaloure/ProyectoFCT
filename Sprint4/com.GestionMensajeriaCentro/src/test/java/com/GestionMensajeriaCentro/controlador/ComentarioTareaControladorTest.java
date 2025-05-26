package com.GestionMensajeriaCentro.controlador;

import com.GestionMensajeriaCentro.modelo.dto.ComentarioTareaDTO;
import com.GestionMensajeriaCentro.servicio.ComentarioTareaServicio;
import com.GestionMensajeriaCentro.servicio.TareaServicio;
import com.GestionMensajeriaCentro.servicio.UsuarioServicio;
import com.GestionMensajeriaCentro.modelo.ComentarioTarea;
import com.GestionMensajeriaCentro.modelo.Tarea;
import com.GestionMensajeriaCentro.modelo.Usuario;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ComentarioTareaControladorTest {

    @Mock
    private ComentarioTareaServicio servicio;

    @Mock
    private TareaServicio tareaServicio;

    @Mock
    private UsuarioServicio usuarioServicio;

    @InjectMocks
    private ComentarioTareaControlador controlador;

    @Nested
    class CrearComentario {

        @Test
        void debeCrearComentarioValido() {
            ComentarioTareaDTO dto = new ComentarioTareaDTO();
            dto.setContenido("Comentario");
            dto.setFechaComentario(LocalDateTime.now());
            dto.setIdTarea(1L);
            dto.setIdUsuario(2L);

            when(tareaServicio.findById(1L)).thenReturn(Optional.of(new Tarea()));
            when(usuarioServicio.findById(2L)).thenReturn(Optional.of(new Usuario()));
            when(servicio.save(any())).thenReturn(new ComentarioTarea());

            ResponseEntity<?> respuesta = controlador.crear(dto);

            assertEquals(201, respuesta.getStatusCodeValue());
        }

        @Test
        void debeFallarConTareaInexistente() {
            ComentarioTareaDTO dto = new ComentarioTareaDTO();
            dto.setIdTarea(1L);
            dto.setIdUsuario(2L);

            when(tareaServicio.findById(1L)).thenReturn(Optional.empty());

            ResponseEntity<?> respuesta = controlador.crear(dto);

            assertEquals(400, respuesta.getStatusCodeValue());
        }
    }
    @Nested
    class VistasYListados {

        @Mock
        private org.springframework.ui.Model model;

        @Test
        void vistaComentariosDevuelveListaHTML() {
            when(servicio.findAll()).thenReturn(List.of(new ComentarioTarea()));
            String vista = controlador.vistaComentarios(model);
            assertEquals("comentarioTarea/lista", vista);
            verify(model).addAttribute(eq("comentarios"), anyList());
        }

        @Test
        void mostrarFormularioDevuelveFormulario() {
            String vista = controlador.mostrarFormulario(model);
            assertEquals("comentarioTarea/formulario", vista);
            verify(model).addAttribute(eq("comentarioTarea"), any(ComentarioTarea.class));
        }

        @Test
        void listarDevuelveComentariosJSON() {
            when(servicio.findAll()).thenReturn(List.of(new ComentarioTarea()));
            ResponseEntity<List<ComentarioTarea>> res = controlador.listar();
            assertEquals(200, res.getStatusCodeValue());
            assertFalse(res.getBody().isEmpty());
        }

        @Test
        void obtenerDevuelveComentarioPorId() {
            ComentarioTarea c = new ComentarioTarea();
            when(servicio.findById(1L)).thenReturn(Optional.of(c));
            ResponseEntity<ComentarioTarea> res = controlador.obtener(1L);
            assertEquals(200, res.getStatusCodeValue());
            assertEquals(c, res.getBody());
        }

        @Test
        void obtenerNoEncontrado() {
            when(servicio.findById(1L)).thenReturn(Optional.empty());
            ResponseEntity<ComentarioTarea> res = controlador.obtener(1L);
            assertEquals(404, res.getStatusCodeValue());
        }
    }

    @Nested
    class ActualizarComentario {

        @Test
        void actualizarComentarioValido() {
            ComentarioTareaDTO dto = new ComentarioTareaDTO();
            dto.setContenido("Actualizado");
            dto.setFechaComentario(LocalDateTime.now());
            dto.setIdTarea(1L);
            dto.setIdUsuario(2L);

            when(tareaServicio.findById(1L)).thenReturn(Optional.of(new Tarea()));
            when(usuarioServicio.findById(2L)).thenReturn(Optional.of(new Usuario()));
            when(servicio.save(any())).thenReturn(new ComentarioTarea());

            ResponseEntity<?> res = controlador.actualizar(1L, dto);
            assertEquals(200, res.getStatusCodeValue());
        }

        @Test
        void actualizarComentarioConError() {
            ComentarioTareaDTO dto = new ComentarioTareaDTO();
            dto.setIdTarea(1L);
            dto.setIdUsuario(999L);

            when(tareaServicio.findById(1L)).thenReturn(Optional.of(new Tarea()));
            when(usuarioServicio.findById(999L)).thenThrow(new IllegalArgumentException("Usuario no encontrado"));

            ResponseEntity<?> res = controlador.actualizar(1L, dto);
            assertEquals(400, res.getStatusCodeValue());
        }
    }
    @Nested
    class EliminarComentario {

        @Test
        void eliminarComentarioExistente() {
            when(servicio.deleteById(1L)).thenReturn(true);
            ResponseEntity<String> res = controlador.eliminar(1L);
            assertEquals(200, res.getStatusCodeValue());
            assertTrue(res.getBody().contains("eliminado"));
        }

        @Test
        void eliminarComentarioNoExistente() {
            when(servicio.deleteById(99L)).thenReturn(false);
            ResponseEntity<String> res = controlador.eliminar(99L);
            assertEquals(404, res.getStatusCodeValue());
            assertTrue(res.getBody().contains("No se pudo eliminar"));
        }
    }


}