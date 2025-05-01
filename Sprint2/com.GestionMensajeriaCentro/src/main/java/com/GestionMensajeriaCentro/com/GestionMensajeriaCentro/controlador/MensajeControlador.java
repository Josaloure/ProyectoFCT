package com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.controlador;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.modelo.Mensaje;
import com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.servicio.MensajeServicio;

/**
 * Class: MensajeControlador
 * Description: Controlador para gestionar los mensajes, incluyendo su creación, actualización, eliminación y visualización.
 * Date: 2025-05-01
 * @author Jose Alonso Ureña
 */
@Controller
@RequestMapping("/mensajes")
public class MensajeControlador {

    private final MensajeServicio mensajeServicio;

    public MensajeControlador(MensajeServicio mensajeServicio) {
        this.mensajeServicio = mensajeServicio;
    }

    /**
     * Vista HTML: Muestra un listado de todos los mensajes en una página HTML.
     * 
     * @param model El modelo de la vista HTML.
     * @return El nombre de la vista HTML que muestra el listado de mensajes.
     */
    @GetMapping("/vista")
    public String vistaMensajes(Model model) {
        model.addAttribute("mensajes", mensajeServicio.findAll());
        return "mensajes/lista"; 
    }

    /**
     * JSON: Devuelve una lista de todos los mensajes en formato JSON.
     * 
     * @return ResponseEntity con la lista de mensajes en formato JSON.
     */
    @GetMapping("/listar")
    @ResponseBody
    public ResponseEntity<List<Mensaje>> listarMensajes() {
        return ResponseEntity.ok(mensajeServicio.findAll());
    }

    /**
     * Obtener un mensaje específico por su ID.
     * 
     * @param id El ID del mensaje.
     * @return ResponseEntity con el mensaje encontrado o un error 404 si no se encuentra.
     */
    @GetMapping("/listarPorId/{id}")
    public ResponseEntity<Mensaje> obtenerMensaje(@PathVariable Long id) {
        Optional<Mensaje> mensaje = mensajeServicio.findById(id);
        return mensaje.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Vista HTML: Muestra el formulario para crear un nuevo mensaje.
     * 
     * @param model El modelo de la vista HTML.
     * @return El nombre de la vista HTML con el formulario de creación.
     */
    @GetMapping("/formulario")
    public String mostrarFormulario(Model model) {
        model.addAttribute("mensaje", new Mensaje());
        return "mensajes/formulario"; 
    }

    /**
     * JSON: Crea un nuevo mensaje desde los datos recibidos en formato JSON.
     * 
     * @param mensaje El objeto Mensaje con los datos a crear.
     * @return ResponseEntity con el mensaje creado o un error 400 si los datos son inválidos.
     */
    @PostMapping("/crear")
    @ResponseBody
    public ResponseEntity<String> crearMensaje(@RequestBody Mensaje mensaje) {
        try {
            mensajeServicio.save(mensaje);
            return ResponseEntity.status(201).body("Mensaje creado con éxito.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error al crear el mensaje: " + e.getMessage());
        }
    }

    /**
     * JSON: Elimina un mensaje por su ID.
     * 
     * @param id El ID del mensaje a eliminar.
     * @return ResponseEntity con un mensaje de éxito o error si no se puede eliminar.
     */
    @DeleteMapping("/eliminar/{id}")
    @ResponseBody
    public ResponseEntity<String> eliminarMensaje(@PathVariable Long id) {
        if (mensajeServicio.deleteById(id)) {
            return ResponseEntity.ok("Mensaje eliminado con éxito.");
        }
        return ResponseEntity.status(404).body("Mensaje no encontrado.");
    }

    /**
     * JSON: Devuelve todos los mensajes asociados a un tablón de centro específico.
     * 
     * @param idTablonCentro El ID del tablón de centro.
     * @return ResponseEntity con la lista de mensajes asociados a ese tablón de centro.
     */
    @GetMapping("/filtrarPorTC/{idTablonCentro}")
    @ResponseBody
    public ResponseEntity<List<Mensaje>> listarMensajesPorTablonCentro(@PathVariable Long idTablonCentro) {
        return ResponseEntity.ok(mensajeServicio.findByTablonCentro(idTablonCentro));
    }
}
