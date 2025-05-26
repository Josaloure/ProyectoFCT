package com.GestionMensajeriaCentro.servicio;

import com.GestionMensajeriaCentro.modelo.HistorialCorreo;
import com.GestionMensajeriaCentro.repositorio.HistorialCorreoRepo;
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
class HistorialCorreoServicioImplTest {

    @Mock
    private HistorialCorreoRepo historialCorreoRepo;

    @InjectMocks
    private HistorialCorreoServicioImpl historialCorreoServicio;

    @Test
    void findAllDevuelveLista() {
        when(historialCorreoRepo.findAll()).thenReturn(List.of(new HistorialCorreo()));
        assertEquals(1, historialCorreoServicio.findAll().size());
    }

    @Test
    void findByIdDevuelveHistorial() {
        HistorialCorreo h = new HistorialCorreo();
        when(historialCorreoRepo.findById(1L)).thenReturn(Optional.of(h));
        assertTrue(historialCorreoServicio.findById(1L).isPresent());
    }

    @Test
    void saveGuardaHistorial() {
        HistorialCorreo h = new HistorialCorreo();
        when(historialCorreoRepo.save(h)).thenReturn(h);
        assertEquals(h, historialCorreoServicio.save(h));
    }

    @Test
    void deleteByIdCorrecto() {
        Long id = 1L;

        when(historialCorreoRepo.existsById(id)).thenReturn(true);
        doNothing().when(historialCorreoRepo).deleteById(id);

        boolean resultado = historialCorreoServicio.deleteById(id);

        assertTrue(resultado);
        verify(historialCorreoRepo).deleteById(id);
    }

}