package com.GestionMensajeriaCentro.controlador;

import java.util.List;

import com.GestionMensajeriaCentro.modelo.dto.HistorialCorreoDTO;
import com.GestionMensajeriaCentro.servicio.MensajeServicio;
import com.GestionMensajeriaCentro.servicio.TareaServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.GestionMensajeriaCentro.modelo.HistorialCorreo;
import com.GestionMensajeriaCentro.servicio.HistorialCorreoServicio;

/**
 * Class: HistorialCorreoControlador
 * Description: Controlador para gestionar el historial de correos enviados, incluyendo su visualización, creación, actualización y eliminación.
 * Date: 2025-05-01
 * @author Jose Alonso Ureña
 */
@RestController
@RequestMapping("/historialCorreos")
public class HistorialCorreoControlador {

    private final HistorialCorreoServicio servicio;
    private final TareaServicio tareaServicio;
    private final MensajeServicio mensajeServicio;

    public HistorialCorreoControlador(HistorialCorreoServicio servicio, TareaServicio tareaServicio, MensajeServicio mensajeServicio) {
        this.servicio = servicio;
        this.tareaServicio = tareaServicio;
        this.mensajeServicio = mensajeServicio;
    }

    /**
     * Devuelve una vista HTML con el listado de todos los historiales de correos.
     *
     * @param model Modelo que contiene los datos para la vista.
     * @return Nombre de la vista HTML.
     */
    @GetMapping("/vista")
    public String vistaHistorial(Model model) {
        model.addAttribute("correos", servicio.findAll());
        return "historialCorreos/lista";
    }

    /**
     * Devuelve todos los historiales de correos en formato JSON.
     *
     * @return Lista de historiales.
     */
    @GetMapping("/listar")
    @ResponseBody
    public ResponseEntity<List<HistorialCorreo>> listar() {
        return ResponseEntity.ok(servicio.findAll());
    }

    /**
     * Devuelve un historial de correo según su ID.
     *
     * @param id ID del historial.
     * @return Historial encontrado o error 404.
     */
    @GetMapping("/listarPorId/{id}")
    public ResponseEntity<HistorialCorreo> obtener(@PathVariable Long id) {
        return servicio.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).build());
    }

    /**
     * Devuelve una vista HTML con el formulario para crear un nuevo historial.
     *
     * @param model Modelo que contiene los datos para la vista.
     * @return Nombre de la vista HTML.
     */
    @GetMapping("/formulario")
    public String mostrarFormulario(Model model) {
        model.addAttribute("correo", new HistorialCorreo());
        return "historialCorreos/formulario";
    }

    /**
     * Mapea un objeto DTO a la entidad HistorialCorreo.
     *
     * @param dto DTO recibido del cliente.
     * @return Entidad HistorialCorreo.
     */
    private HistorialCorreo mapearDTO(HistorialCorreoDTO dto) {
        return HistorialCorreo.builder()
                .fechaEnvio(dto.getFechaEnvio())
                .destinatarioEmail(dto.getDestinatarioEmail())
                .tipoNotificacion(dto.getTipoNotificacion())
                .tarea(dto.getIdTarea() != null ? tareaServicio.findById(dto.getIdTarea()).orElse(null) : null)
                .mensaje(dto.getIdMensaje() != null ? mensajeServicio.findById(dto.getIdMensaje()).orElse(null) : null)
                .build();
    }

    /**
     * Crea un nuevo historial de correo a partir de los datos proporcionados.
     *
     * @param dto DTO con los datos del nuevo historial.
     * @return Historial creado o mensaje de error.
     */
    @PostMapping("/crear")
    @ResponseBody
    public ResponseEntity<?> crear(@RequestBody HistorialCorreoDTO dto) {
        try {
            HistorialCorreo historial = mapearDTO(dto);
            HistorialCorreo creado = servicio.save(historial);
            return ResponseEntity.status(201).body(creado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear historial: " + e.getMessage());
        }
    }

    /**
     * Actualiza un historial de correo existente.
     *
     * @param id  ID del historial a actualizar.
     * @param dto DTO con los nuevos datos.
     * @return Historial actualizado o mensaje de error.
     */
    @PutMapping("/actualizar/{id}")
    @ResponseBody
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody HistorialCorreoDTO dto) {
        try {
            HistorialCorreo historial = mapearDTO(dto);
            historial.setIdHistorial(id);
            HistorialCorreo actualizado = servicio.save(historial);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al actualizar historial.");
        }
    }

    /**
     * Elimina un historial de correo por su ID.
     *
     * @param id ID del historial a eliminar.
     * @return Mensaje de éxito o error.
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
