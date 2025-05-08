package com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.controlador;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.modelo.Usuario;
import com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.servicio.UsuarioServicio;

/**
 * Class: UsuarioControlador
 * Description: Controlador encargado de gestionar los usuarios, incluyendo su creación, actualización, eliminación y visualización tanto en vistas HTML como en formato JSON.
 * Date: 2025-05-01
 * @uthor Jose Alonso Ureña
 */
@Controller
@RequestMapping("/usuarios")
public class UsuarioControlador {

    private final UsuarioServicio usuarioServicio;

    public UsuarioControlador(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

    /**
     * Vista HTML: Muestra una lista de todos los usuarios en una página HTML.
     * 
     * @param model El modelo de la vista HTML.
     * @return El nombre de la vista HTML que muestra la lista de usuarios.
     */
    @GetMapping("/vista")
    public String vistaUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioServicio.findAll());
        return "usuarios/lista"; 
    }

    /**
     * JSON: Devuelve una lista de todos los usuarios en formato JSON.
     * 
     * @return ResponseEntity con la lista de usuarios en formato JSON.
     */
    @GetMapping("/listar")
    @ResponseBody
    public ResponseEntity<List<Usuario>> listar() {
        return ResponseEntity.ok(usuarioServicio.findAll());
    }

    /**
     * Obtiene un usuario específico por su ID.
     * 
     * @param id El ID del usuario.
     * @return ResponseEntity con el usuario o un error 404 si no se encuentra.
     */
    @GetMapping("/listar/{id}")
    public ResponseEntity<Usuario> obtener(@PathVariable Long id) {
        return usuarioServicio.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Vista HTML: Muestra el formulario para crear un nuevo usuario.
     * 
     * @param model El modelo de la vista HTML.
     * @return El nombre de la vista HTML con el formulario.
     */
    @GetMapping("/formulario")
    public String mostrarFormulario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuarios/formulario"; 
    }

    /**
     * Crea un nuevo usuario desde un formulario o datos JSON.
     * 
     * @param usuario El objeto usuario a crear.
     * @return ResponseEntity con el usuario creado o un error 400 si los datos son inválidos.
     */
    @PostMapping("/crear")
    @ResponseBody
    public ResponseEntity<?> crear(@RequestBody Usuario usuario) {
        try {
            Usuario creado = usuarioServicio.save(usuario);
            return ResponseEntity.status(201).body(creado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Datos inválidos: " + e.getMessage());
        }
    }

    /**
     * Actualiza un usuario existente.
     * 
     * @param id El ID del usuario a actualizar.
     * @param usuario El objeto usuario con los nuevos datos.
     * @return ResponseEntity con el usuario actualizado o un error 404 si no se encuentra el usuario.
     */
    @PutMapping("/actualizar/{id}")
    @ResponseBody
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
        Optional<Usuario> existente = usuarioServicio.findById(id);
        if (!existente.isPresent()) {
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
     * @param id El ID del usuario a eliminar.
     * @return ResponseEntity con un mensaje de éxito o un error 404 si no se encuentra el usuario.
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
