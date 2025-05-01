package com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.controlador;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.modelo.Tarea;
import com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.servicio.TareaServicio;

/**
 * Class: TareaControlador
 * Description: Controlador para gestionar las tareas del sistema, incluyendo su visualización, creación, eliminación y filtrado.
 * Date: 2025-05-01
 * @author Jose Alonso Ureña
 */
@Controller
@RequestMapping("/tareas")
public class TareaControlador {

    private final TareaServicio tareaServicio;

    public TareaControlador(TareaServicio tareaServicio) {
        this.tareaServicio = tareaServicio;
    }

    /**
     * Vista HTML: Muestra la lista de todas las tareas.
     * 
     * @param model El modelo que contiene los datos para la vista.
     * @return El nombre de la vista que muestra la lista de tareas.
     */
    @GetMapping("/vista")
    public String vistaTareas(Model model) {
        model.addAttribute("tareas", tareaServicio.findAll());
        return "tareas/lista"; 
    }

    /**
     * JSON: Devuelve la lista de todas las tareas en formato JSON.
     * 
     * @return ResponseEntity con la lista de tareas en formato JSON.
     */
    @GetMapping("/listar")
    @ResponseBody
    public ResponseEntity<List<Tarea>> listarTareas() {
        return ResponseEntity.ok(tareaServicio.findAll());
    }
    
    /**
     * Obtiene una tarea específica por su ID.
     * 
     * @param id El ID de la tarea.
     * @return ResponseEntity con la tarea encontrada o un error 404 si no se encuentra.
     */
    @GetMapping("/listarPorId/{id}")
    public ResponseEntity<Tarea> obtenerTarea(@PathVariable Long id) {
        Optional<Tarea> tarea = tareaServicio.findById(id);
        return tarea.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.status(404).build());
    }
    
    /**
     * Vista HTML: Muestra el formulario para crear una nueva tarea.
     * 
     * @param model El modelo para enviar datos a la vista.
     * @return El nombre de la vista HTML con el formulario de creación.
     */
    @GetMapping("/formulario")
    public String mostrarFormulario(Model model) {
        model.addAttribute("tarea", new Tarea());
        return "tareas/formulario";
    }

    /**
     * JSON: Crea una nueva tarea desde los datos recibidos en el cuerpo de la solicitud.
     * 
     * @param tarea El objeto tarea con los datos para crear la tarea.
     * @return ResponseEntity con el mensaje de éxito o error.
     */
    @PostMapping("/crear")
    @ResponseBody
    public ResponseEntity<String> crearTarea(@RequestBody Tarea tarea) {
        try {
            tareaServicio.save(tarea);
            return ResponseEntity.status(201).body("Tarea creada con éxito.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body("Error al crear la tarea: " + e.getMessage());
        }
    }

    /**
     * JSON: Elimina una tarea por su ID.
     * 
     * @param id El ID de la tarea a eliminar.
     * @return ResponseEntity con el mensaje de éxito o error.
     */
    @DeleteMapping("/eliminar/{id}")
    @ResponseBody
    public ResponseEntity<String> eliminarTarea(@PathVariable Long id) {
        if (tareaServicio.deleteById(id)) {
            return ResponseEntity.ok("Tarea eliminada con éxito.");
        }
        return ResponseEntity.status(404).body("Tarea no encontrada.");
    }

    /**
     * JSON: Devuelve una lista de tareas asociadas a un tablón de profesor específico.
     * 
     * @param idTablonProfesor El ID del tablón del profesor.
     * @return ResponseEntity con la lista de tareas asociadas a ese tablón de profesor.
     */
    @GetMapping("/listarPorTF/{idTablonProfesor}")
    @ResponseBody
    public ResponseEntity<List<Tarea>> listarTareasPorTablonProfesor(@PathVariable Long idTablonProfesor) {
        return ResponseEntity.ok(tareaServicio.findByTablonProfesor(idTablonProfesor));
    }
}
