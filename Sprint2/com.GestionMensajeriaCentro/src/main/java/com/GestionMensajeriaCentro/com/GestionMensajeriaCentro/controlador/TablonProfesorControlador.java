package com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.controlador;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.modelo.TablonProfesor;
import com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.servicio.TablonProfesorServicio;

import java.util.Optional;

/**
 * Class: TablonProfesorControlador
 * Description: Controlador encargado de gestionar los tablones de profesor, incluyendo la creación, actualización, eliminación y visualización.
 * Date: 2025-05-01
 * @author Jose Alonso Ureña
 */
@Controller
@RequestMapping("/tablonProfesor")
public class TablonProfesorControlador {

    private final TablonProfesorServicio servicio;

    public TablonProfesorControlador(TablonProfesorServicio servicio) {
        this.servicio = servicio;
    }

    /**
     * Vista HTML: Muestra un tablón de profesor específico por su ID.
     * 
     * @param model El modelo para enviar datos a la vista.
     * @param id El ID del tablón de profesor.
     * @return El nombre de la vista que muestra el tablón de profesor o un error si no se encuentra.
     */
    @GetMapping("/vista/{id}")
    public String verTablon(Model model, @PathVariable Long id) {
        Optional<TablonProfesor> tablon = servicio.findById(id);
        if (tablon.isPresent()) {
            model.addAttribute("tablon", tablon.get());
            return "tablonProfesor/ver"; 
        } else {
            model.addAttribute("mensaje", "El tablón solicitado no existe.");
            return "error";
        }
    }

    /**
     * JSON: Devuelve un tablón de profesor específico por su ID.
     * 
     * @param id El ID del tablón de profesor.
     * @return ResponseEntity con el tablón encontrado o un error 404 si no se encuentra.
     */
    @GetMapping("/listarPorId/{id}")
    public ResponseEntity<TablonProfesor> obtener(@PathVariable Long id) {
        return servicio.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).body(null));
    }

    /**
     * Vista HTML: Muestra el formulario para crear un nuevo tablón de profesor.
     * 
     * @param model El modelo para enviar datos a la vista.
     * @return El nombre de la vista HTML con el formulario de creación.
     */
    @GetMapping("/formulario")
    public String mostrarFormulario(Model model) {
        model.addAttribute("tablonProfesor", new TablonProfesor());
        return "tablonProfesor/formulario"; 
    }

    /**
     * JSON: Crea un nuevo tablón de profesor a partir de los datos recibidos en formato JSON.
     * 
     * @param tablonProfesor El objeto TablonProfesor con los datos a crear.
     * @return ResponseEntity con el tablón creado o un error 400 si los datos son inválidos.
     */
    @PostMapping("/crear")
    @ResponseBody
    public ResponseEntity<?> crear(@RequestBody TablonProfesor tablonProfesor) {
        try {
            TablonProfesor creado = servicio.save(tablonProfesor);
            return ResponseEntity.status(201).body(creado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body("Error en la creación: " + e.getMessage());
        }
    }

    /**
     * JSON: Actualiza un tablón de profesor existente.
     * 
     * @param id El ID del tablón de profesor a actualizar.
     * @param tablonProfesor El objeto TablonProfesor con los nuevos datos.
     * @return ResponseEntity con el tablón actualizado o un error 400 si ocurre un fallo en la actualización.
     */
    @PutMapping("/actualizar/{id}")
    @ResponseBody
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody TablonProfesor tablonProfesor) {
        tablonProfesor.setIdTablonProfesor(id);
        try {
            TablonProfesor actualizado = servicio.save(tablonProfesor);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error al actualizar el tablón");
        }
    }

    /**
     * JSON: Elimina un tablón de profesor por su ID.
     * 
     * @param id El ID del tablón de profesor a eliminar.
     * @return ResponseEntity con un mensaje de éxito o error si no se puede eliminar.
     */
    @DeleteMapping("/eliminar/{id}")
    @ResponseBody
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        if (servicio.deleteById(id)) {
            return ResponseEntity.ok("Tablón de Profesor con id " + id + " ha sido eliminado correctamente.");
        }
        return ResponseEntity.status(404).body("No se pudo eliminar el Tablón de Profesor con id " + id);
    }
}
