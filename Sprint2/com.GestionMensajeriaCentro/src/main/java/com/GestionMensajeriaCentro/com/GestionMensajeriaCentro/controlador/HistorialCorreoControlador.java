package com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.controlador;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.modelo.HistorialCorreo;
import com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.servicio.HistorialCorreoServicio;

/**
 * Class: HistorialCorreoControlador
 * Description: Controlador para gestionar el historial de correos enviados, incluyendo su visualización, creación, actualización y eliminación.
 * Date: 2025-05-01
 * @author Jose Alonso Ureña
 */
@Controller
@RequestMapping("/historialCorreos")
public class HistorialCorreoControlador {

    private final HistorialCorreoServicio servicio;

    public HistorialCorreoControlador(HistorialCorreoServicio servicio) {
        this.servicio = servicio;
    }

    /**
     * Vista HTML: Muestra una lista de todos los historiales de correos enviados.
     * 
     * @param model El modelo de la vista HTML.
     * @return El nombre de la vista HTML que muestra el listado de historiales de correos.
     */
    @GetMapping("/vista")
    public String vistaHistorial(Model model) {
        model.addAttribute("correos", servicio.findAll());
        return "historialCorreos/lista"; 
    }

    /**
     * JSON: Devuelve una lista de todos los historiales de correos en formato JSON.
     * 
     * @return ResponseEntity con la lista de historiales de correos en formato JSON.
     */
    @GetMapping("/listar")
    @ResponseBody
    public ResponseEntity<List<HistorialCorreo>> listar() {
        return ResponseEntity.ok(servicio.findAll());
    }

    /**
     * Obtener un historial de correo específico por su ID.
     * 
     * @param id El ID del historial de correo.
     * @return ResponseEntity con el historial de correo encontrado o un error 404 si no se encuentra.
     */
    @GetMapping("/listarPorId/{id}")
    public ResponseEntity<HistorialCorreo> obtener(@PathVariable Long id) {
        return servicio.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).build());
    }

    /**
     * Vista HTML: Muestra el formulario para crear un nuevo historial de correo.
     * 
     * @param model El modelo de la vista HTML.
     * @return El nombre de la vista HTML con el formulario de creación.
     */
    @GetMapping("/formulario")
    public String mostrarFormulario(Model model) {
        model.addAttribute("correo", new HistorialCorreo());
        return "historialCorreos/formulario"; 
    }

    /**
     * JSON: Crea un nuevo historial de correo desde los datos recibidos en formato JSON.
     * 
     * @param historialCorreo El objeto HistorialCorreo con los datos a crear.
     * @return ResponseEntity con el historial de correo creado o un error 400 si los datos son inválidos.
     */
    @PostMapping("/crear")
    @ResponseBody
    public ResponseEntity<?> crear(@RequestBody HistorialCorreo historialCorreo) {
        try {
            HistorialCorreo creado = servicio.save(historialCorreo);
            return ResponseEntity.status(201).body(creado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error al crear historial: " + e.getMessage());
        }
    }

    /**
     * JSON: Actualiza un historial de correo existente.
     * 
     * @param id El ID del historial de correo a actualizar.
     * @param historialCorreo El objeto HistorialCorreo con los nuevos datos.
     * @return ResponseEntity con el historial de correo actualizado o un error 400 si ocurre un fallo en la actualización.
     */
    @PutMapping("/actualizar/{id}")
    @ResponseBody
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody HistorialCorreo historialCorreo) {
        historialCorreo.setIdHistorial(id);
        try {
            HistorialCorreo actualizado = servicio.save(historialCorreo);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al actualizar historial.");
        }
    }

    /**
     * JSON: Elimina un historial de correo por su ID.
     * 
     * @param id El ID del historial de correo a eliminar.
     * @return ResponseEntity con un mensaje de éxito o error si no se puede eliminar.
     */
    @DeleteMapping("/eliminar/{id}")
    @ResponseBody
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        if (servicio.deleteById(id)) {
            return ResponseEntity.ok("Historial de correo con id " + id + " ha sido eliminado correctamente.");
        }
        return ResponseEntity.status(404).body("No se pudo eliminar el historial de correo con id " + id);
    }
}
