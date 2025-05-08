package com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.controlador;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.modelo.ComentarioTarea;
import com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.servicio.ComentarioTareaServicio;

/**
 * Class: ComentarioTareaControlador
 * Description: Controlador para gestionar los comentarios de tareas, incluyendo su creación, actualización, eliminación y visualización.
 * Date: 2025-05-01
 * @author Jose Alonso Ureña
 */
@Controller
@RequestMapping("/comentariosTarea")
public class ComentarioTareaControlador {

    private final ComentarioTareaServicio servicio;

    public ComentarioTareaControlador(ComentarioTareaServicio servicio) {
        this.servicio = servicio;
    }

    /**
     * Vista HTML: Muestra un listado de todos los comentarios de tarea en una página HTML.
     * 
     * @param model El modelo de la vista HTML.
     * @return El nombre de la vista HTML que muestra el listado de comentarios.
     */
    @GetMapping("/vista")
    public String vistaComentarios(Model model) {
        model.addAttribute("comentarios", servicio.findAll());
        return "comentarioTarea/lista"; // /templates/comentariosTarea/lista.html
    }

    /**
     * JSON: Devuelve una lista de todos los comentarios de tarea en formato JSON.
     * 
     * @return ResponseEntity con la lista de comentarios en formato JSON.
     */
    @GetMapping("/listar")
    @ResponseBody
    public ResponseEntity<List<ComentarioTarea>> listar() {
        return ResponseEntity.ok(servicio.findAll());
    }

    /**
     * Obtener un comentario de tarea específico por su ID.
     * 
     * @param id El ID del comentario de tarea.
     * @return ResponseEntity con el comentario encontrado o un error 404 si no se encuentra.
     */
    @GetMapping("/listarPorId/{id}")
    public ResponseEntity<ComentarioTarea> obtener(@PathVariable Long id) {
        return servicio.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).build());
    }

    /**
     * Vista HTML: Muestra el formulario para crear un nuevo comentario de tarea.
     * 
     * @param model El modelo de la vista HTML.
     * @return El nombre de la vista HTML con el formulario de creación.
     */
    @GetMapping("/formulario")
    public String mostrarFormulario(Model model) {
        model.addAttribute("comentarioTarea", new ComentarioTarea());
        return "comentarioTarea/formulario"; // /templates/comentariosTarea/formulario.html
    }

    /**
     * JSON: Crea un nuevo comentario de tarea desde los datos recibidos en formato JSON.
     * 
     * @param comentarioTarea El objeto ComentarioTarea con los datos a crear.
     * @return ResponseEntity con el comentario creado o un error 403 si el acceso está denegado.
     */
    @PostMapping("/crear")
    @ResponseBody
    public ResponseEntity<?> crear(@RequestBody ComentarioTarea comentarioTarea) {
        try {
            ComentarioTarea creado = servicio.save(comentarioTarea);
            return ResponseEntity.status(201).body(creado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(403).body("Acceso denegado: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error al crear el comentario");
        }
    }

    /**
     * JSON: Actualiza un comentario de tarea existente.
     * 
     * @param id El ID del comentario de tarea a actualizar.
     * @param comentarioTarea El objeto ComentarioTarea con los nuevos datos.
     * @return ResponseEntity con el comentario actualizado o un error 400 si ocurre un fallo en la actualización.
     */
    @PutMapping("/actualizar/{id}")
    @ResponseBody
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody ComentarioTarea comentarioTarea) {
        comentarioTarea.setIdComentario(id);
        try {
            ComentarioTarea actualizado = servicio.save(comentarioTarea);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error al actualizar el comentario");
        }
    }

    /**
     * JSON: Elimina un comentario de tarea por su ID.
     * 
     * @param id El ID del comentario de tarea a eliminar.
     * @return ResponseEntity con un mensaje de éxito o error si no se puede eliminar.
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
