package com.GestionMensajeriaCentro.controlador;

import java.time.LocalDateTime;
import java.util.List;

import com.GestionMensajeriaCentro.modelo.Tarea;
import com.GestionMensajeriaCentro.modelo.Usuario;
import com.GestionMensajeriaCentro.modelo.dto.ComentarioTareaDTO;
import com.GestionMensajeriaCentro.servicio.ComentarioTareaServicio;
import com.GestionMensajeriaCentro.servicio.TareaServicio;
import com.GestionMensajeriaCentro.servicio.UsuarioServicio;
import com.GestionMensajeriaCentro.modelo.ComentarioTarea;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Class: ComentarioTareaControlador
 * Description: Controlador para gestionar los comentarios de tareas, incluyendo su creación, actualización, eliminación y visualización.
 * Date: 2025-05-01
 * @author Jose Alonso Ureña
 */
@RestController
@RequestMapping("/profesor/comentariosTarea")
public class ComentarioTareaControlador {

    private final ComentarioTareaServicio servicio;
    private final TareaServicio tareaServicio;
    private final UsuarioServicio usuarioServicio;

    public ComentarioTareaControlador(ComentarioTareaServicio servicio, TareaServicio tareaServicio, UsuarioServicio usuarioServicio) {
        this.servicio = servicio;
        this.tareaServicio = tareaServicio;
        this.usuarioServicio = usuarioServicio;
    }

    /**
     * Devuelve una vista HTML con el listado de todos los comentarios de tarea.
     *
     * @param model El modelo de datos para la vista.
     * @return Nombre del archivo HTML con la lista de comentarios.
     */
    @GetMapping("/vista")
    public String vistaComentarios(Model model) {
        model.addAttribute("comentarios", servicio.findAll());
        return "comentarioTarea/lista";
    }

    /**
     * Devuelve todos los comentarios en formato JSON.
     *
     * @return Lista de ComentarioTarea.
     */
    @GetMapping("/listar")
    @ResponseBody
    public ResponseEntity<List<ComentarioTarea>> listar() {
        return ResponseEntity.ok(servicio.findAll());
    }

    /**
     * Devuelve un comentario de tarea según su ID.
     *
     * @param id ID del comentario.
     * @return Comentario encontrado o error 404.
     */
    @GetMapping("/listarPorId/{id}")
    public ResponseEntity<ComentarioTarea> obtener(@PathVariable Long id) {
        return servicio.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).build());
    }

    /**
     * Devuelve una vista HTML con el formulario para crear un nuevo comentario.
     *
     * @param model El modelo de datos para la vista.
     * @return Nombre del archivo HTML del formulario.
     */
    @GetMapping("/formulario")
    public String mostrarFormulario(Model model) {
        model.addAttribute("comentarioTarea", new ComentarioTarea());
        return "comentarioTarea/formulario";
    }

    /**
     * Crea un nuevo comentario de tarea a partir de un DTO.
     *
     * @param dto Datos del nuevo comentario.
     * @return Comentario creado o mensaje de error.
     */
    @PostMapping("/crear")
    public ResponseEntity<?> crear(@RequestBody ComentarioTareaDTO dto) {
        try {
            ComentarioTarea comentario = new ComentarioTarea();
            comentario.setContenido(dto.getContenido());
            comentario.setFechaComentario(dto.getFechaComentario());

            Tarea tarea = tareaServicio.findById(dto.getIdTarea())
                    .orElseThrow(() -> new IllegalArgumentException("Tarea no encontrada"));
            Usuario usuario = usuarioServicio.findById(dto.getIdUsuario())
                    .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

            comentario.setTarea(tarea);
            comentario.setUsuario(usuario);

            ComentarioTarea creado = servicio.save(comentario);
            return ResponseEntity.status(201).body(creado);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error al crear el comentario: " + e.getMessage());
        }
    }

    /**
     * Mapea un DTO de comentario a una entidad ComentarioTarea.
     *
     * @param dto DTO recibido.
     * @return ComentarioTarea con datos asignados.
     */
    private ComentarioTarea mapearDTO(ComentarioTareaDTO dto) {
        Tarea tarea = tareaServicio.findById(dto.getIdTarea())
                .orElseThrow(() -> new IllegalArgumentException("Tarea no encontrada"));
        Usuario usuario = usuarioServicio.findById(dto.getIdUsuario())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        return ComentarioTarea.builder()
                .contenido(dto.getContenido())
                .fechaComentario(dto.getFechaComentario() != null ? dto.getFechaComentario() : LocalDateTime.now())
                .tarea(tarea)
                .usuario(usuario)
                .build();
    }

    /**
     * Actualiza un comentario existente con nuevos datos.
     *
     * @param id  ID del comentario a actualizar.
     * @param dto DTO con los nuevos datos del comentario.
     * @return Comentario actualizado o mensaje de error.
     */
    @PutMapping("/actualizar/{id}")
    @ResponseBody
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody ComentarioTareaDTO dto) {
        try {
            ComentarioTarea comentario = mapearDTO(dto);
            comentario.setIdComentario(id);
            ComentarioTarea actualizado = servicio.save(comentario);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error al actualizar el comentario");
        }
    }

    /**
     * Elimina un comentario de tarea por su ID.
     *
     * @param id ID del comentario a eliminar.
     * @return Mensaje de éxito o error.
     */
    @DeleteMapping("/eliminar/{id}")
    @ResponseBody
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        if (servicio.deleteById(id)) {
            return ResponseEntity.ok("Comentario de tarea con id " + id + " ha sido eliminado correctamente.");
        }
        return ResponseEntity.status(404).body("No se pudo eliminar el comentario de tarea con id " + id);
    }
}
