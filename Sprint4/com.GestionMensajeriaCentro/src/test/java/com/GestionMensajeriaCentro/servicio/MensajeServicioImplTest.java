package com.GestionMensajeriaCentro.servicio;

import com.GestionMensajeriaCentro.modelo.Mensaje;
import com.GestionMensajeriaCentro.repositorio.MensajeRepo;
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
class MensajeServicioImplTest {

    @Mock
    private MensajeRepo mensajeRepo;

    @InjectMocks
    private MensajeServicioImpl mensajeServicio;

    @Test
    void findAll_devuelveMensajes() {
        when(mensajeRepo.findAll()).thenReturn(List.of(new Mensaje()));
        assertEquals(1, mensajeServicio.findAll().size());
    }

    @Test
    void findById_devuelveMensaje() {
        Mensaje m = new Mensaje();
        when(mensajeRepo.findById(1L)).thenReturn(Optional.of(m));
        assertTrue(mensajeServicio.findById(1L).isPresent());
    }

    @Test
    void deleteById_exitoso() {
        Long id = 1L;

        when(mensajeRepo.existsById(id)).thenReturn(true); // NECESARIO
        doNothing().when(mensajeRepo).deleteById(id);      // OPCIONAL PERO CLARO

        boolean resultado = mensajeServicio.deleteById(id);

        assertTrue(resultado);
        verify(mensajeRepo).deleteById(id);
    }


    @Test
    void findByTablonCentro_devuelveLista() {
        when(mensajeRepo.findByTablonCentroIdTablonCentro(5L)).thenReturn(List.of(new Mensaje()));
        List<Mensaje> resultado = mensajeServicio.findByTablonCentro(5L);
        assertFalse(resultado.isEmpty());
    }
}