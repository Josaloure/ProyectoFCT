package com.GestionMensajeriaCentro.servicio;

import com.GestionMensajeriaCentro.modelo.TablonProfesor;
import com.GestionMensajeriaCentro.repositorio.TablonProfesorRepo;
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
class TablonProfesorServicioImplTest {

    @Mock
    private TablonProfesorRepo repo;

    @InjectMocks
    private TablonProfesorServicioImpl servicio;

    @Test
    void findAllDevuelveLista() {
        when(repo.findAll()).thenReturn(List.of(new TablonProfesor()));
        assertEquals(1, servicio.findAll().size());
    }

    @Test
    void findByIdDevuelveTablon() {
        when(repo.findById(1L)).thenReturn(Optional.of(new TablonProfesor()));
        assertTrue(servicio.findById(1L).isPresent());
    }

    @Test
    void saveGuardaCorrectamente() {
        TablonProfesor tp = new TablonProfesor();
        when(repo.save(tp)).thenReturn(tp);
        assertNotNull(servicio.save(tp));
    }

    @Test
    void deleteById_eliminaCuandoExiste() {
        Long id = 1L;
        when(repo.existsById(id)).thenReturn(true);

        boolean resultado = servicio.deleteById(id);

        assertTrue(resultado);
        verify(repo).deleteById(id);
    }

    @Test
    void deleteById_noEliminaCuandoNoExiste() {
        Long id = 2L;
        when(repo.existsById(id)).thenReturn(false);

        boolean resultado = servicio.deleteById(id);

        assertFalse(resultado);
        verify(repo, never()).deleteById(anyLong());
    }

    @Test
    void findByUsuarioIdUsuario_devuelveListaCorrecta() {
        Long idUsuario = 3L;
        TablonProfesor tp = new TablonProfesor();
        when(repo.findByUsuarioIdUsuario(idUsuario)).thenReturn(List.of(tp));

        List<TablonProfesor> resultado = servicio.findByUsuarioIdUsuario(idUsuario);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(tp, resultado.get(0));
    }

}