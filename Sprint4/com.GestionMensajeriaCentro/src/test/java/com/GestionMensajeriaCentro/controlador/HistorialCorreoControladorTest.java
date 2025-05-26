package com.GestionMensajeriaCentro.controlador;

import com.GestionMensajeriaCentro.modelo.HistorialCorreo;
import com.GestionMensajeriaCentro.modelo.dto.HistorialCorreoDTO;
import com.GestionMensajeriaCentro.servicio.HistorialCorreoServicio;
import com.GestionMensajeriaCentro.servicio.MensajeServicio;
import com.GestionMensajeriaCentro.servicio.TareaServicio;
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
class HistorialCorreoControladorTest {

    @Mock
    private HistorialCorreoServicio servicio;

    @Mock
    private TareaServicio tareaServicio;

    @Mock
    private MensajeServicio mensajeServicio;

    @Mock
    private Model model;

    @InjectMocks
    private HistorialCorreoControlador controlador;

    @Test
    void vistaHistorialMuestraVista() {
        String vista = controlador.vistaHistorial(model);
        assertEquals("historialCorreos/lista", vista);
        verify(model).addAttribute(eq("correos"), any());
    }

    @Test
    void listarPorIdDevuelveHistorial() {
        HistorialCorreo h = new HistorialCorreo();
        when(servicio.findById(1L)).thenReturn(Optional.of(h));
        ResponseEntity<HistorialCorreo> r = controlador.obtener(1L);
        assertEquals(200, r.getStatusCodeValue());
        assertEquals(h, r.getBody());
    }

    @Test
    void listarPorIdNoExiste() {
        when(servicio.findById(1L)).thenReturn(Optional.empty());
        ResponseEntity<HistorialCorreo> r = controlador.obtener(1L);
        assertEquals(404, r.getStatusCodeValue());
    }

    @Test
    void eliminarHistorialExitoso() {
        when(servicio.deleteById(1L)).thenReturn(true);
        ResponseEntity<String> r = controlador.eliminar(1L);
        assertEquals(200, r.getStatusCodeValue());
    }

    @Test
    void eliminarHistorialNoExiste() {
        when(servicio.deleteById(1L)).thenReturn(false);
        ResponseEntity<String> r = controlador.eliminar(1L);
        assertEquals(404, r.getStatusCodeValue());
    }
    @Nested
    class MetodosRestantes {

        @Test
        void listarDevuelveListaJSON() {
            when(servicio.findAll()).thenReturn(List.of(new HistorialCorreo()));
            ResponseEntity<List<HistorialCorreo>> res = controlador.listar();
            assertEquals(200, res.getStatusCodeValue());
            assertFalse(res.getBody().isEmpty());
        }

        @Test
        void mostrarFormularioDevuelveVista() {
            String vista = controlador.mostrarFormulario(model);
            assertEquals("historialCorreos/formulario", vista);
            verify(model).addAttribute(eq("correo"), any(HistorialCorreo.class));
        }

        @Test
        void crearCorreoExitosamente() {
            HistorialCorreoDTO dto = new HistorialCorreoDTO();
            dto.setDestinatarioEmail("test@correo.com");
            dto.setTipoNotificacion(null);
            dto.setFechaEnvio(java.time.LocalDateTime.now());

            HistorialCorreo correo = new HistorialCorreo();
            when(servicio.save(any())).thenReturn(correo);

            ResponseEntity<?> res = controlador.crear(dto);
            assertEquals(201, res.getStatusCodeValue());
        }

        @Test
        void crearCorreoFalla() {
            HistorialCorreoDTO dto = new HistorialCorreoDTO();
            when(servicio.save(any())).thenThrow(new RuntimeException("Error"));

            ResponseEntity<?> res = controlador.crear(dto);
            assertEquals(400, res.getStatusCodeValue());
        }

        @Test
        void actualizarCorreoExitosamente() {
            HistorialCorreoDTO dto = new HistorialCorreoDTO();
            dto.setDestinatarioEmail("test@correo.com");
            dto.setFechaEnvio(java.time.LocalDateTime.now());

            when(servicio.save(any())).thenReturn(new HistorialCorreo());
            ResponseEntity<?> res = controlador.actualizar(1L, dto);
            assertEquals(200, res.getStatusCodeValue());
        }

        @Test
        void actualizarCorreoFalla() {
            HistorialCorreoDTO dto = new HistorialCorreoDTO();
            when(servicio.save(any())).thenThrow(new RuntimeException("Error"));

            ResponseEntity<?> res = controlador.actualizar(1L, dto);
            assertEquals(400, res.getStatusCodeValue());
        }
    }

}