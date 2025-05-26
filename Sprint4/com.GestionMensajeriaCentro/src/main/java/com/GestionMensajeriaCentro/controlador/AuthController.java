package com.GestionMensajeriaCentro.controlador;

import com.GestionMensajeriaCentro.modelo.Usuario;
import com.GestionMensajeriaCentro.modelo.dto.RegistroRequest;
import com.GestionMensajeriaCentro.servicio.UsuarioServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * Class: AuthController
 * Description: Controlador para gestionar la autenticación de usuarios, incluyendo el registro.
 * Date: 2025-05-01
 * @author Jose Alonso Ureña
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioServicio usuarioServicio;
    private final PasswordEncoder passwordEncoder;

    /**
     * Registra un nuevo usuario en el sistema si el email no está ya registrado.
     *
     * @param request Objeto con los datos necesarios para el registro del usuario.
     * @return ResponseEntity con un mensaje de éxito o de error si el email ya existe.
     */
    @PostMapping("/registro")
    public ResponseEntity<String> registrar(@RequestBody RegistroRequest request) {
        if (usuarioServicio.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body(" El email ya está registrado.");
        }

        Usuario nuevoUsuario = Usuario.builder()
                .email(request.getEmail())
                .clave(passwordEncoder.encode(request.getClave()))
                .nombre(request.getNombre())
                .apellidos(request.getApellidos())
                .nick(request.getNick())
                .rol(request.getRol())
                .usuarioMovil(request.getUsuarioMovil())
                .build();

        usuarioServicio.save(nuevoUsuario);
        return ResponseEntity.ok(" Usuario registrado correctamente.");
    }
}
