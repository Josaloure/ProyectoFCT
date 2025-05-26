package com.GestionMensajeriaCentro.controlador;

import com.GestionMensajeriaCentro.modelo.Mensaje;
import com.GestionMensajeriaCentro.modelo.TablonCentro;
import com.GestionMensajeriaCentro.modelo.Usuario;
import com.GestionMensajeriaCentro.modelo.dto.MensajeDTO;
import com.GestionMensajeriaCentro.repositorio.TablonCentroRepo;
import com.GestionMensajeriaCentro.repositorio.UsuarioRepo;
import com.GestionMensajeriaCentro.servicio.MensajeServicio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MensajeControladorTest {

    @Mock
    private MensajeServicio mensajeServicio;

    @Mock
    private UsuarioRepo usuarioRepo;

    @Mock
    private TablonCentroRepo tablonCentroRepo;

    @Mock
    private UserDetails userDetails;

    @Mock
    private org.springframework.ui.Model model;

    @InjectMocks
    private MensajeControlador controlador;

    @Test
    void crearMensajeValido() {
        MensajeDTO dto = new MensajeDTO();
        dto.setIdTablonCentro(1L);
        dto.setPublicado(true);
        dto.setVisibleParaTodos(true);

        when(userDetails.getUsername()).thenReturn("admin@test.com");
        when(usuarioRepo.findByEmail("admin@test.com")).thenReturn(Optional.of(new Usuario()));
        when(tablonCentroRepo.findById(1L)).thenReturn(Optional.of(new TablonCentro()));
        when(mensajeServicio.save(any())).thenReturn(new Mensaje());

        ResponseEntity<?> response = controlador.crearMensaje(dto, userDetails);
        assertEquals(201, response.getStatusCodeValue());
    }
    @Test
    void vistaMensajesDevuelveVista() {
        when(mensajeServicio.findAll()).thenReturn(List.of(new Mensaje()));
        String vista = controlador.vistaMensajes(model);
        assertEquals("mensajeLista", vista);
        verify(model).addAttribute(eq("mensajes"), anyList());
    }

    @Test
    void listarMensajesDevuelveJson() {
        when(mensajeServicio.findAll()).thenReturn(List.of(new Mensaje()));
        ResponseEntity<List<Mensaje>> res = controlador.listarMensajes();
        assertEquals(200, res.getStatusCodeValue());
        assertFalse(res.getBody().isEmpty());
    }

    @Test
    void obtenerMensajeExistente() {
        Mensaje mensaje = new Mensaje();
        when(mensajeServicio.findById(1L)).thenReturn(Optional.of(mensaje));
        ResponseEntity<Mensaje> res = controlador.obtenerMensaje(1L);
        assertEquals(200, res.getStatusCodeValue());
        assertEquals(mensaje, res.getBody());
    }

    @Test
    void obtenerMensajeNoExistente() {
        when(mensajeServicio.findById(1L)).thenReturn(Optional.empty());
        ResponseEntity<Mensaje> res = controlador.obtenerMensaje(1L);
        assertEquals(404, res.getStatusCodeValue());
    }

    @Test
    void mostrarFormularioDevuelveVista() {
        String vista = controlador.mostrarFormulario(model);
        assertEquals("mensajes/formulario", vista);
        verify(model).addAttribute(eq("mensaje"), any(Mensaje.class));
    }

    @Test
    void eliminarMensajeExistente() {
        when(mensajeServicio.deleteById(1L)).thenReturn(true);
        ResponseEntity<String> res = controlador.eliminarMensaje(1L);
        assertEquals(200, res.getStatusCodeValue());
    }

    @Test
    void eliminarMensajeNoExistente() {
        when(mensajeServicio.deleteById(1L)).thenReturn(false);
        ResponseEntity<String> res = controlador.eliminarMensaje(1L);
        assertEquals(404, res.getStatusCodeValue());
    }

    @Test
    void listarMensajesPorTablonCentro() {
        when(mensajeServicio.findByTablonCentro(5L)).thenReturn(List.of(new Mensaje()));
        ResponseEntity<List<Mensaje>> res = controlador.listarMensajesPorTablonCentro(5L);
        assertEquals(200, res.getStatusCodeValue());
        assertFalse(res.getBody().isEmpty());
    }
}