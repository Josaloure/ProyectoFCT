package com.GestionMensajeriaCentro.servicio;

import com.GestionMensajeriaCentro.modelo.ComentarioTarea;
import com.GestionMensajeriaCentro.repositorio.ComentarioTareaRepo;
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
class ComentarioTareaServicioImplTest {

    @Mock
    private ComentarioTareaRepo comentarioRepo;

    @InjectMocks
    private ComentarioTareaServicioImpl comentarioServicio;

    @Test
    void findAllDevuelveLista() {
        when(comentarioRepo.findAll()).thenReturn(List.of(new ComentarioTarea()));
        assertEquals(1, comentarioServicio.findAll().size());
    }

    @Test
    void findByIdDevuelveComentario() {
        ComentarioTarea c = new ComentarioTarea();
        when(comentarioRepo.findById(1L)).thenReturn(Optional.of(c));
        assertTrue(comentarioServicio.findById(1L).isPresent());
    }
    @Test
    void deleteByIdCorrecto() {
        Long id = 1L;
        when(comentarioRepo.existsById(id)).thenReturn(true); // ← NECESARIO
        doNothing().when(comentarioRepo).deleteById(id);       // ← OPCIONAL PERO CLARO

        boolean resultado = comentarioServicio.deleteById(id);

        assertTrue(resultado);
        verify(comentarioRepo).deleteById(id);
    }

}