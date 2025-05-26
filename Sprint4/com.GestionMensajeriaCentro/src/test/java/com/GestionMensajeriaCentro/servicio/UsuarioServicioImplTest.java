package com.GestionMensajeriaCentro.servicio;

import com.GestionMensajeriaCentro.modelo.Usuario;
import com.GestionMensajeriaCentro.repositorio.UsuarioRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServicioImplTest {

    @Mock
    private UsuarioRepo usuarioRepo;

    @InjectMocks
    private UsuarioServicioImpl usuarioServicio;

    @Test
    void findAll_devuelveListaUsuarios() {
        List<Usuario> lista = List.of(new Usuario());
        when(usuarioRepo.findAll()).thenReturn(lista);

        List<Usuario> resultado = usuarioServicio.findAll();
        assertEquals(1, resultado.size());
    }

    @Test
    void findById_devuelveUsuario() {
        Usuario usuario = new Usuario();
        when(usuarioRepo.findById(1L)).thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado = usuarioServicio.findById(1L);
        assertTrue(resultado.isPresent());
    }

    @Test
    void save_guardaUsuario() {
        Usuario usuario = new Usuario();
        when(usuarioRepo.save(usuario)).thenReturn(usuario);

        Usuario resultado = usuarioServicio.save(usuario);
        assertNotNull(resultado);
    }

    @Test
    void existsByEmail_devuelveBooleano() {
        when(usuarioRepo.existsByEmail("a@b.com")).thenReturn(true);
        assertTrue(usuarioServicio.existsByEmail("a@b.com"));
    }

    @Test
    void findByEmail_devuelveUsuario() {
        Usuario usuario = new Usuario();
        when(usuarioRepo.findByEmail("correo@ejemplo.com")).thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado = usuarioServicio.findByEmail("correo@ejemplo.com");

        assertTrue(resultado.isPresent());
        assertEquals(usuario, resultado.get());
    }

    @Test
    void deleteById_eliminaCuandoExiste() {
        Long id = 1L;
        when(usuarioRepo.existsById(id)).thenReturn(true);

        boolean resultado = usuarioServicio.deleteById(id);

        assertTrue(resultado);
        verify(usuarioRepo).deleteById(id);
    }

    @Test
    void deleteById_noEliminaCuandoNoExiste() {
        Long id = 2L;
        when(usuarioRepo.existsById(id)).thenReturn(false);

        boolean resultado = usuarioServicio.deleteById(id);

        assertFalse(resultado);
        verify(usuarioRepo, never()).deleteById(anyLong());
    }

}