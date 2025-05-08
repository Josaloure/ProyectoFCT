package com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.controlador;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.modelo.TablonCentro;
import com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.servicio.TablonCentroServicio;

/**
 * Class: TablonCentroControlador
 * Description: Controlador para gestionar los tablones de centro, incluyendo la creación, actualización, eliminación y visualización.
 * Date: 2025-05-01
 * @author Jose Alonso Ureña
 */
@Controller
@RequestMapping("/tablonCentro")
public class TablonCentroControlador {

    private final TablonCentroServicio servicio;

    public TablonCentroControlador(TablonCentroServicio servicio) {
        this.servicio = servicio;
    }

    /**
     * Vista HTML: Muestra un tablón de centro específico por su ID.
     * 
     * @param model El modelo para enviar datos a la vista.
     * @param id El ID del tablón de centro.
     * @return El nombre de la vista que muestra el tablón de centro o un error si no se encuentra.
     */
    @GetMapping("/vista/{id}")
    public String vistaTablon(Model model, @PathVariable Long id) {
        Optional<TablonCentro> tablon = servicio.findById(id);
        if (tablon.isPresent()) {
            model.addAttribute("tablon", tablon.get());
            return "tablonCentro/ver"; 
        } else {
            model.addAttribute("mensaje", "El tablón de centro no existe.");
            return "error";
        }
    }

    /**
     * JSON: Devuelve un tablón de centro específico por su ID.
     * 
     * @param id El ID del tablón de centro.
     * @return ResponseEntity con el tablón encontrado o un error 404 si no se encuentra.
     */
    @GetMapping("/listarTC/{id}")
    public ResponseEntity<TablonCentro> obtener(@PathVariable Long id) {
        return servicio.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).build());
    }

    /**
     * Vista HTML: Muestra el formulario para crear un nuevo tablón de centro.
     * 
     * @param model El modelo para enviar datos a la vista.
     * @return El nombre de la vista HTML con el formulario de creación.
     */
    @GetMapping("/formulario")
    public String mostrarFormulario(Model model) {
        model.addAttribute("tablonCentro", new TablonCentro());
        return "tablonCentro/formulario"; 
    }

    /**
     * JSON: Crea un nuevo tablón de centro a partir de los datos recibidos en formato JSON.
     * 
     * @param tablonCentro El objeto TablonCentro con los datos a crear.
     * @return ResponseEntity con el tablón creado o un error 400 si los datos son inválidos.
     */
    @PostMapping("/crear")
    @ResponseBody
    public ResponseEntity<?> crear(@RequestBody TablonCentro tablonCentro) {
        try {
            TablonCentro creado = servicio.save(tablonCentro);
            return ResponseEntity.status(201).body(creado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body("Datos inválidos: " + e.getMessage());
        }
    }

    /**
     * JSON: Actualiza un tablón de centro existente.
     * 
     * @param id El ID del tablón de centro a actualizar.
     * @param tablonCentro El objeto TablonCentro con los nuevos datos.
     * @return ResponseEntity con el tablón actualizado o un error 400 si ocurre un fallo en la actualización.
     */
    @PutMapping("/actualizar/{id}")
    @ResponseBody
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody TablonCentro tablonCentro) {
        tablonCentro.setIdTablonCentro(id);
        try {
            TablonCentro actualizado = servicio.save(tablonCentro);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error al actualizar el tablón de centro.");
        }
    }

    /**
     * JSON: Elimina un tablón de centro por su ID.
     * 
     * @param id El ID del tablón de centro a eliminar.
     * @return ResponseEntity con un mensaje de éxito o error si no se puede eliminar.
     */
    @DeleteMapping("/eliminar/{id}")
    @ResponseBody
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        if (servicio.deleteById(id)) {
            return ResponseEntity.ok("Tablón de Centro con id " + id + " ha sido eliminado correctamente.");
        }
        return ResponseEntity.status(404).body("No se pudo eliminar el Tablón de Centro con id " + id);
    }
    
    @GetMapping("/consultarPorUsuario/{idUsuario}")
    public List<TablonCentro> obtenerPorUsuario(@PathVariable Long idUsuario) {
        return servicio.obtenerPorUsuario(idUsuario);
    }
}
