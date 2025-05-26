package com.GestionMensajeriaCentro.controlador;

import com.GestionMensajeriaCentro.modelo.dto.RegistroRequest;
import com.GestionMensajeriaCentro.servicio.UsuarioServicio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private UsuarioServicio usuarioServicio;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthController authController;

    @Test
    void registrar_usuarioYaExiste() {
        RegistroRequest request = new RegistroRequest();
        request.setEmail("test@example.com");

        when(usuarioServicio.existsByEmail("test@example.com")).thenReturn(true);

        ResponseEntity<String> response = authController.registrar(request);

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void registrar_usuarioNuevo() {
        RegistroRequest request = new RegistroRequest();
        request.setEmail("test@example.com");
        request.setClave("1234");

        when(usuarioServicio.existsByEmail("test@example.com")).thenReturn(false);
        when(passwordEncoder.encode("1234")).thenReturn("hashed");

        ResponseEntity<String> response = authController.registrar(request);

        assertEquals(200, response.getStatusCodeValue());
    }
}