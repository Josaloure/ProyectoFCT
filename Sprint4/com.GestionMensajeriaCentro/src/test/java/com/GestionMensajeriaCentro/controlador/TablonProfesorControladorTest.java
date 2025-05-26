package com.GestionMensajeriaCentro.controlador;

import com.GestionMensajeriaCentro.modelo.TablonProfesor;
import com.GestionMensajeriaCentro.modelo.Usuario;
import com.GestionMensajeriaCentro.modelo.dto.TablonProfesorDTO;
import com.GestionMensajeriaCentro.repositorio.UsuarioRepo;
import com.GestionMensajeriaCentro.servicio.TablonProfesorServicio;
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
class TablonProfesorControladorTest {

    @Mock
    private TablonProfesorServicio servicio;

    @Mock
    private UsuarioRepo usuarioRepo;

    @Mock
    private Model model;

    @InjectMocks
    private TablonProfesorControlador controlador;

    @Test
    void crearTablonProfesorCorrectamente() {
        TablonProfesorDTO dto = new TablonProfesorDTO();
        dto.setIdUsuario(1L);
        dto.setNombre("Prueba");

        when(usuarioRepo.findById(1L)).thenReturn(Optional.of(new Usuario()));
        when(servicio.save(any())).thenReturn(new TablonProfesor());

        ResponseEntity<?> response = controlador.crear(dto);
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void crearTablonProfesorConUsuarioInvalido() {
        TablonProfesorDTO dto = new TablonProfesorDTO();
        dto.setIdUsuario(99L);
        when(usuarioRepo.findById(99L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = controlador.crear(dto);
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void actualizarTablonProfesorCorrectamente() {
        TablonProfesorDTO dto = new TablonProfesorDTO();
        dto.setIdUsuario(1L);
        dto.setNombre("Actualizar");

        when(usuarioRepo.findById(1L)).thenReturn(Optional.of(new Usuario()));
        when(servicio.save(any())).thenReturn(new TablonProfesor());

        ResponseEntity<?> response = controlador.actualizar(1L, dto);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void eliminarTablonProfesorExistente() {
        when(servicio.deleteById(1L)).thenReturn(true);
        ResponseEntity<String> response = controlador.eliminar(1L);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void eliminarTablonProfesorNoExiste() {
        when(servicio.deleteById(1L)).thenReturn(false);
        ResponseEntity<String> response = controlador.eliminar(1L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void verTablonExistente() {
        TablonProfesor tablon = new TablonProfesor();
        when(servicio.findById(1L)).thenReturn(Optional.of(tablon));

        String vista = controlador.verTablon(model, 1L);
        assertEquals("tablonListar", vista);
        verify(model).addAttribute("tablon", tablon);
    }

    @Test
    void verTablonNoExiste() {
        when(servicio.findById(1L)).thenReturn(Optional.empty());

        String vista = controlador.verTablon(model, 1L);
        assertEquals("error", vista);
        verify(model).addAttribute("mensaje", "El tablón solicitado no existe.");
    }

    @Test
    void obtenerPorUsuarioDevuelveDTOs() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Profe");

        TablonProfesor tablon = new TablonProfesor();
        tablon.setIdTablonProfesor(1L);
        tablon.setNombre("Tablón 1");
        tablon.setUsuario(usuario);

        when(servicio.findByUsuarioIdUsuario(1L)).thenReturn(List.of(tablon));
        var lista = controlador.obtenerPorUsuario(1L);
        assertEquals(1, lista.getBody().size());
        assertEquals("Tablón 1", lista.getBody().get(0).getNombre());
    }

    @Test
    void consultarTodosDevuelveLista() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Ana");

        TablonProfesor tablon = new TablonProfesor();
        tablon.setIdTablonProfesor(2L);
        tablon.setNombre("General");
        tablon.setUsuario(usuario);

        when(servicio.findAll()).thenReturn(List.of(tablon));
        List<?> lista = controlador.consultarTodos();
        assertEquals(1, lista.size());
    }

    @Test
    void mostrarFormularioDevuelveVista() {
        String vista = controlador.mostrarFormulario(model);
        assertEquals("tablonProfesor/formulario", vista);
        verify(model).addAttribute(eq("tablonProfesor"), any(TablonProfesor.class));
    }

    @Test
    void vistaListaTablonDevuelveVista() {
        String vista = controlador.vistaListaTablon();
        assertEquals("tablonListar", vista);
    }

}