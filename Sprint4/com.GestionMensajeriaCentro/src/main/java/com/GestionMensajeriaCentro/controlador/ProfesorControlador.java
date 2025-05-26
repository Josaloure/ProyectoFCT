package com.GestionMensajeriaCentro.controlador;

import com.GestionMensajeriaCentro.modelo.Usuario;
import com.GestionMensajeriaCentro.servicio.UsuarioServicio;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Class: ProfesorControlador
 * Description: Controlador que expone operaciones específicas para el perfil de profesor, como la obtención de usuarios disponibles.
 * Date: 2025-05-01
 * Author: Jose Alonso Ureña
 */
@RestController
@RequestMapping("/api/profesor")
public class ProfesorControlador {

    private final UsuarioServicio usuarioServicio;

    public ProfesorControlador(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

    /**
     * Devuelve una lista de todos los usuarios registrados.
     *
     * @return Lista de objetos Usuario.
     */
    @GetMapping("/usuarios")
    public List<Usuario> obtenerUsuarios() {
        return usuarioServicio.findAll();
    }
}
