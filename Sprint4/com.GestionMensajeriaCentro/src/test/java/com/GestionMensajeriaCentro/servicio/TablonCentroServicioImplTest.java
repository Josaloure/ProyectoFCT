package com.GestionMensajeriaCentro.servicio;

import com.GestionMensajeriaCentro.modelo.TablonCentro;
import com.GestionMensajeriaCentro.repositorio.TablonCentroRepo;
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
class TablonCentroServicioImplTest {

    @Mock
    private TablonCentroRepo repo;

    @InjectMocks
    private TablonCentroServicioImpl servicio;

    @Test
    void findAllDevuelveLista() {
        when(repo.findAll()).thenReturn(List.of(new TablonCentro()));
        assertEquals(1, servicio.findAll().size());
    }

    @Test
    void findByIdDevuelveTablon() {
        when(repo.findById(1L)).thenReturn(Optional.of(new TablonCentro()));
        assertTrue(servicio.findById(1L).isPresent());
    }

    @Test
    void saveGuardaCorrectamente() {
        TablonCentro tc = new TablonCentro();
        when(repo.save(tc)).thenReturn(tc);
        assertNotNull(servicio.save(tc));
    }
    @Test
    void deleteByIdEliminaCuandoExiste() {
        Long id = 1L;
        when(repo.existsById(id)).thenReturn(true);

        boolean resultado = servicio.deleteById(id);

        assertTrue(resultado);
        verify(repo).deleteById(id);
    }

    @Test
    void deleteByIdNoEliminaCuandoNoExiste() {
        Long id = 2L;
        when(repo.existsById(id)).thenReturn(false);

        boolean resultado = servicio.deleteById(id);

        assertFalse(resultado);
        verify(repo, never()).deleteById(anyLong());
    }

    @Test
    void obtenerPorUsuarioDevuelveListaCorrecta() {
        Long idUsuario = 3L;
        TablonCentro tc = new TablonCentro();
        when(repo.findByUsuarioIdUsuario(idUsuario)).thenReturn(List.of(tc));

        List<TablonCentro> resultado = servicio.obtenerPorUsuario(idUsuario);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(tc, resultado.get(0));
    }

}