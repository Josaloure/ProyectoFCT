package com.GestionMensajeriaCentro.controlador;

import com.GestionMensajeriaCentro.modelo.TablonCentro;
import com.GestionMensajeriaCentro.modelo.Usuario;
import com.GestionMensajeriaCentro.modelo.dto.TablonCentroDTO;
import com.GestionMensajeriaCentro.repositorio.UsuarioRepo;
import com.GestionMensajeriaCentro.servicio.TablonCentroServicio;
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
class TablonCentroControladorTest {

    @Mock
    private TablonCentroServicio servicio;

    @Mock
    private UsuarioRepo usuarioRepo;

    @Mock
    private Model model;

    @InjectMocks
    private TablonCentroControlador controlador;

    @Test
    void vistaForm() {
        String vista = controlador.mostrarVistaFormulario();
        assertEquals("tablonCentro", vista);
    }

    @Test
    void obtenerTablonExistente() {
        TablonCentro tc = new TablonCentro();
        when(servicio.findById(1L)).thenReturn(Optional.of(tc));
        ResponseEntity<TablonCentro> r = controlador.obtener(1L);
        assertEquals(200, r.getStatusCodeValue());
        assertEquals(tc, r.getBody());
    }

    @Test
    void obtenerTablonNoExiste() {
        when(servicio.findById(1L)).thenReturn(Optional.empty());
        ResponseEntity<TablonCentro> r = controlador.obtener(1L);
        assertEquals(404, r.getStatusCodeValue());
    }

    @Test
    void crearTablonCorrectamente() {
        TablonCentroDTO dto = new TablonCentroDTO();
        dto.setIdUsuario(1L);
        dto.setNombre("Test");
        when(usuarioRepo.findById(1L)).thenReturn(Optional.of(new Usuario()));
        when(servicio.save(any())).thenReturn(new TablonCentro());

        ResponseEntity<?> r = controlador.crear(dto);
        assertEquals(201, r.getStatusCodeValue());
    }

    @Test
    void crearTablonUsuarioInexistente() {
        TablonCentroDTO dto = new TablonCentroDTO();
        dto.setIdUsuario(99L);
        when(usuarioRepo.findById(99L)).thenReturn(Optional.empty());
        ResponseEntity<?> r = controlador.crear(dto);
        assertEquals(400, r.getStatusCodeValue());
    }
    @Test
    void vistaTablonExistenteDevuelveVista() {
        TablonCentro tablon = new TablonCentro();
        when(servicio.findById(1L)).thenReturn(Optional.of(tablon));
        String vista = controlador.vistaTablon(model, 1L);
        assertEquals("tablonCentro/ver", vista);
        verify(model).addAttribute("tablon", tablon);
    }

    @Test
    void vistaTablonInexistenteDevuelveError() {
        when(servicio.findById(1L)).thenReturn(Optional.empty());
        String vista = controlador.vistaTablon(model, 1L);
        assertEquals("error", vista);
        verify(model).addAttribute("mensaje", "El tablón de centro no existe.");
    }

    @Test
    void mostrarFormularioNuevoDevuelveVista() {
        String vista = controlador.mostrarFormulario(model);
        assertEquals("tablonCentro/formulario", vista);
        verify(model).addAttribute(eq("tablonCentro"), any(TablonCentro.class));
    }

    @Test
    void actualizarTablonCorrectamente() {
        TablonCentroDTO dto = new TablonCentroDTO();
        dto.setIdUsuario(1L);
        dto.setNombre("Editado");
        when(usuarioRepo.findById(1L)).thenReturn(Optional.of(new Usuario()));
        when(servicio.save(any())).thenReturn(new TablonCentro());

        ResponseEntity<?> r = controlador.actualizar(1L, dto);
        assertEquals(200, r.getStatusCodeValue());
    }

    @Test
    void actualizarTablonUsuarioInvalido() {
        TablonCentroDTO dto = new TablonCentroDTO();
        dto.setIdUsuario(1L);
        when(usuarioRepo.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<?> r = controlador.actualizar(1L, dto);
        assertEquals(400, r.getStatusCodeValue());
    }

    @Test
    void eliminarTablonExistente() {
        when(servicio.deleteById(1L)).thenReturn(true);
        ResponseEntity<String> r = controlador.eliminar(1L);
        assertEquals(200, r.getStatusCodeValue());
    }

    @Test
    void eliminarTablonInexistente() {
        when(servicio.deleteById(1L)).thenReturn(false);
        ResponseEntity<String> r = controlador.eliminar(1L);
        assertEquals(404, r.getStatusCodeValue());
    }

    @Test
    void obtenerPorUsuarioDevuelveLista() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Pepe");
        TablonCentro tablon = new TablonCentro();
        tablon.setIdTablonCentro(1L);
        tablon.setNombre("Tablón Pepe");
        tablon.setUsuario(usuario);

        when(servicio.obtenerPorUsuario(1L)).thenReturn(List.of(tablon));

        var lista = controlador.obtenerPorUsuario(1L);
        assertEquals(1, lista.size());
        assertEquals("Tablón Pepe", lista.get(0).getNombre());
    }

}