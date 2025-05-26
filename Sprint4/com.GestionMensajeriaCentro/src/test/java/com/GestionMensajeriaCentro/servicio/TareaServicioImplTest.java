package com.GestionMensajeriaCentro.servicio;

import com.GestionMensajeriaCentro.modelo.Tarea;
import com.GestionMensajeriaCentro.repositorio.TablonProfesorRepo;
import com.GestionMensajeriaCentro.repositorio.TareaRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TareaServicioImplTest {

    @Mock
    private TareaRepo tareaRepo;

    @Mock
    private TablonProfesorRepo tablonProfesorRepo;

    @InjectMocks
    private TareaServicioImpl tareaServicio;

    @Test
    void findAll_devuelveLista() {
        when(tareaRepo.findAll()).thenReturn(List.of(new Tarea()));
        List<Tarea> resultado = tareaServicio.findAll();
        assertEquals(1, resultado.size());
    }

    @Test
    void deleteById_eliminaCorrectamente() {
        Long id = 1L;
        when(tareaRepo.existsById(id)).thenReturn(true); // <- ESTA LÍNEA FALTABA
        doNothing().when(tareaRepo).deleteById(id);

        boolean resultado = tareaServicio.deleteById(id);

        assertTrue(resultado);
        verify(tareaRepo).deleteById(id);
    }
    @Test
    void deleteById_noEliminaCuandoNoExiste() {
        Long id = 2L;
        when(tareaRepo.existsById(id)).thenReturn(false);

        boolean resultado = tareaServicio.deleteById(id);

        assertFalse(resultado);
        verify(tareaRepo, never()).deleteById(anyLong());
    }


}