package com.GestionMensajeriaCentro.controlador;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.GestionMensajeriaCentro.modelo.Usuario;
import com.GestionMensajeriaCentro.servicio.UsuarioServicio;

/**
 * Class: UsuarioControlador
 * Description: Controlador encargado de gestionar los usuarios, incluyendo su creación, actualización, eliminación y visualización tanto en vistas HTML como en formato JSON.
 * Date: 2025-05-01
 * Author: Jose Alonso Ureña
 */
@Controller
@RequestMapping("/usuarios")
public class UsuarioControlador {

    private final UsuarioServicio usuarioServicio;

    public UsuarioControlador(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

    /**
     * Devuelve una vista HTML con la lista de todos los usuarios.
     *
     * @param model Modelo de datos para la vista.
     * @return Nombre del archivo HTML de la vista.
     */
    @GetMapping("/vista")
    public String vistaUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioServicio.findAll());
        return "usuarios/lista";
    }

    /**
     * Devuelve la lista de todos los usuarios en formato JSON.
     *
     * @return Lista de objetos Usuario.
     */
    @GetMapping("/listar")
    @ResponseBody
    public ResponseEntity<List<Usuario>> listar() {
        return ResponseEntity.ok(usuarioServicio.findAll());
    }

    /**
     * Obtiene un usuario específico por su ID.
     *
     * @param id ID del usuario.
     * @return Usuario encontrado o error 404.
     */
    @GetMapping("/listar/{id}")
    public ResponseEntity<Usuario> obtener(@PathVariable Long id) {
        return usuarioServicio.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Devuelve una vista HTML con el formulario para crear un nuevo usuario.
     *
     * @param model Modelo de datos para la vista.
     * @return Nombre del archivo HTML del formulario.
     */
    @GetMapping("/formulario")
    public String mostrarFormulario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuarios/formulario";
    }

    /**
     * Actualiza un usuario existente con nuevos datos.
     *
     * @param id ID del usuario a actualizar.
     * @param usuario Objeto con los datos actualizados.
     * @return Usuario actualizado o mensaje de error si no se encuentra o falla la validación.
     */
    @PutMapping("/actualizar/{id}")
    @ResponseBody
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
        Optional<Usuario> existente = usuarioServicio.findById(id);
        if (existente.isEmpty()) {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        }

        usuario.setIdUsuario(id);
        try {
            Usuario actualizado = usuarioServicio.save(usuario);
            return ResponseEntity.ok(actualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body("Error al actualizar: " + e.getMessage());
        }
    }

    /**
     * Elimina un usuario por su ID.
     *
     * @param id ID del usuario a eliminar.
     * @return Mensaje de éxito o error si no se encuentra el usuario.
     */
    @DeleteMapping("/eliminar/{id}")
    @ResponseBody
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        if (usuarioServicio.deleteById(id)) {
            return ResponseEntity.ok("Usuario eliminado correctamente");
        }
        return ResponseEntity.status(404).body("No se encontró el usuario a eliminar");
    }
}
