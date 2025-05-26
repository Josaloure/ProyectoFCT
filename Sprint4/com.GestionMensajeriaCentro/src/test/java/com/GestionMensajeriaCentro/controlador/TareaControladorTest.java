package com.GestionMensajeriaCentro.controlador;

import com.GestionMensajeriaCentro.modelo.Tarea;
import com.GestionMensajeriaCentro.modelo.dto.TareaListadoDTO;
import com.GestionMensajeriaCentro.servicio.TareaServicio;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TareaControladorTest {

    @Mock
    private TareaServicio tareaServicio;

    @Mock
    private Model model;

    @InjectMocks
    private TareaControlador controlador;

    @Nested
    class Vista {
        @Test
        void vistaTareasDevuelveNombreVista() {
            when(tareaServicio.findAll()).thenReturn(List.of(new Tarea()));
            String vista = controlador.vistaTareas(model);
            assertEquals("tareaLista", vista);
            verify(model).addAttribute(eq("tareas"), anyList());
        }
    }

    @Nested
    class ObtenerTarea {
        @Test
        void listarPorIdDevuelveTarea() {
            Tarea tarea = new Tarea();
            when(tareaServicio.findById(1L)).thenReturn(Optional.of(tarea));
            ResponseEntity<Tarea> response = controlador.obtenerTarea(1L);
            assertEquals(200, response.getStatusCodeValue());
            assertEquals(tarea, response.getBody());
        }

        @Test
        void listarPorIdNoEncontrado() {
            when(tareaServicio.findById(1L)).thenReturn(Optional.empty());
            ResponseEntity<Tarea> response = controlador.obtenerTarea(1L);
            assertEquals(404, response.getStatusCodeValue());
        }
    }

    @Nested
    class EliminarTarea {
        @Test
        void eliminaTareaExistente() {
            when(tareaServicio.deleteById(1L)).thenReturn(true);
            ResponseEntity<String> response = controlador.eliminarTarea(1L);
            assertEquals(200, response.getStatusCodeValue());
        }

        @Test
        void eliminarTareaNoExiste() {
            when(tareaServicio.deleteById(1L)).thenReturn(false);
            ResponseEntity<String> response = controlador.eliminarTarea(1L);
            assertEquals(404, response.getStatusCodeValue());
        }
    }

    @Nested
    class TareasListado {

        @Test
        void listarTodasLasTareasDevuelveJson() {
            Tarea tarea = new Tarea();
            tarea.setIdTarea(1L);
            tarea.setTitulo("Test");
            tarea.setDescripcion("Desc");
            tarea.setFechaCreacion(LocalDate.from(LocalDateTime.now()));
            tarea.setEstado(com.GestionMensajeriaCentro.modelo.enums.Estado.PENDIENTE);

            when(tareaServicio.findAll()).thenReturn(List.of(tarea));

            ResponseEntity<List<TareaListadoDTO>> res = controlador.listarTareas();
            assertEquals(200, res.getStatusCodeValue());
            assertEquals(1, res.getBody().size());
            assertEquals("Test", res.getBody().get(0).getTitulo());
        }

        @Test
        void mostrarFormularioDevuelveVista() {
            String vista = controlador.mostrarFormulario(model);
            assertEquals("tareas/formulario", vista);
            verify(model).addAttribute(eq("tarea"), any(Tarea.class));
        }

        @Test
        void enviarTareaExterna() {
            com.GestionMensajeriaCentro.modelo.dto.TareaExternaDTO dto =
                    new com.GestionMensajeriaCentro.modelo.dto.TareaExternaDTO();

            ResponseEntity<String> res = controlador.enviarTarea(dto);
            assertEquals(200, res.getStatusCodeValue());
            assertEquals("Enviada", res.getBody());
            verify(tareaServicio).enviarTareaAlServicioExterno(dto);
        }

        @Test
        void importarTareaDesdeExternoDevuelveTarea() {
            com.GestionMensajeriaCentro.modelo.dto.TareaExternaDTO dto =
                    new com.GestionMensajeriaCentro.modelo.dto.TareaExternaDTO();

            Tarea tarea = new Tarea();
            when(tareaServicio.importarTareaExterna(dto, 1L)).thenReturn(tarea);

            ResponseEntity<Tarea> res = controlador.importarTareaDesdeExterno(1L, dto);
            assertEquals(200, res.getStatusCodeValue());
            assertEquals(tarea, res.getBody());
        }

        @Test
        void listarTareasPorTablonProfesorDevuelveLista() {
            Tarea tarea = new Tarea();
            tarea.setIdTarea(1L);
            tarea.setTitulo("Por tablón");
            tarea.setDescripcion("Desc");
            tarea.setFechaCreacion(LocalDate.from(LocalDateTime.now()));
            tarea.setEstado(com.GestionMensajeriaCentro.modelo.enums.Estado.EN_CURSO);

            when(tareaServicio.findByTablonProfesor(1L)).thenReturn(List.of(tarea));
            ResponseEntity<List<TareaListadoDTO>> res = controlador.listarTareasPorTablonProfesor(1L);
            assertEquals(200, res.getStatusCodeValue());
            assertEquals("Por tablón", res.getBody().get(0).getTitulo());
        }

        @Test
        void verDetalleDevuelveVista() {
            Tarea tarea = new Tarea();
            when(tareaServicio.findById(1L)).thenReturn(Optional.of(tarea));
            String vista = controlador.verDetalle(1L, model);
            assertEquals("tareas/detalle", vista);
            verify(model).addAttribute("tarea", tarea);
        }

        @Test
        void verDetalleNoEncontrado() {
            when(tareaServicio.findById(1L)).thenReturn(Optional.empty());
            String vista = controlador.verDetalle(1L, model);
            assertEquals("redirect:/profesor/tareas/vista", vista);
        }
    }

}