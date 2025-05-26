package com.GestionMensajeriaCentro.controlador;

import java.time.LocalDate;
import java.util.List;

import com.GestionMensajeriaCentro.modelo.TablonCentro;
import com.GestionMensajeriaCentro.modelo.Usuario;
import com.GestionMensajeriaCentro.modelo.dto.MensajeDTO;
import com.GestionMensajeriaCentro.repositorio.TablonCentroRepo;
import com.GestionMensajeriaCentro.repositorio.UsuarioRepo;
import com.GestionMensajeriaCentro.modelo.Mensaje;
import com.GestionMensajeriaCentro.servicio.MensajeServicio;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Class: MensajeControlador
 * Description: Controlador para gestionar los mensajes del tablón del centro, incluyendo su visualización, creación, eliminación y filtrado.
 * Date: 2025-05-01
 * Author: Jose Alonso Ureña
 */
@Controller
@RequestMapping("/admin/mensajes")
public class MensajeControlador {

    private final MensajeServicio mensajeServicio;
    private final UsuarioRepo usuarioRepo;
    private final TablonCentroRepo tablonCentroRepo;

    public MensajeControlador(MensajeServicio mensajeServicio, UsuarioRepo usuarioRepo, TablonCentroRepo tablonCentroRepo) {
        this.mensajeServicio = mensajeServicio;
        this.usuarioRepo = usuarioRepo;
        this.tablonCentroRepo = tablonCentroRepo;
    }

    /**
     * Vista HTML: Muestra una lista de todos los mensajes.
     *
     * @param model Modelo de datos para la vista.
     * @return Nombre de la vista HTML.
     */
    @GetMapping("/vista")
    public String vistaMensajes(Model model) {
        model.addAttribute("mensajes", mensajeServicio.findAll());
        return "mensajeLista";
    }

    /**
     * JSON: Devuelve una lista de todos los mensajes.
     *
     * @return Lista de objetos Mensaje en formato JSON.
     */
    @GetMapping("/listar")
    public ResponseEntity<List<Mensaje>> listarMensajes() {
        return ResponseEntity.ok(mensajeServicio.findAll());
    }

    /**
     * JSON: Devuelve un mensaje específico por su ID.
     *
     * @param id ID del mensaje.
     * @return Objeto Mensaje o 404 si no se encuentra.
     */
    @GetMapping("/listarPorId/{id}")
    public ResponseEntity<Mensaje> obtenerMensaje(@PathVariable Long id) {
        return mensajeServicio.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Vista HTML: Muestra el formulario para crear un nuevo mensaje.
     *
     * @param model Modelo para enviar datos a la vista.
     * @return Nombre del archivo HTML.
     */
    @GetMapping("/formulario")
    public String mostrarFormulario(Model model) {
        model.addAttribute("mensaje", new Mensaje());
        return "mensajes/formulario";
    }

    /**
     * JSON: Crea un nuevo mensaje y lo asocia al usuario autenticado.
     *
     * @param dto DTO con los datos del mensaje.
     * @param userDetails Usuario autenticado.
     * @return Mensaje creado o mensaje de error.
     */
    @PostMapping("/crear")
    public ResponseEntity<?> crearMensaje(@RequestBody MensajeDTO dto, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            Usuario publicador = usuarioRepo.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

            TablonCentro tablon = tablonCentroRepo.findById(dto.getIdTablonCentro())
                    .orElseThrow(() -> new IllegalArgumentException("Tablón no encontrado"));

            Mensaje mensaje = Mensaje.builder()
                    .titulo(dto.getTitulo())
                    .contenido(dto.getContenido())
                    .fechaCreacion(LocalDate.now())
                    .tablonCentro(tablon)
                    .publicador(publicador)
                    .publicado(dto.isPublicado())
                    .visibleParaTodos(dto.isVisibleParaTodos())
                    .build();

            Mensaje creado = mensajeServicio.save(mensaje);
            return ResponseEntity.status(201).body(creado);

        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error al crear el mensaje: " + e.getMessage());
        }
    }

    /**
     * JSON: Elimina un mensaje por su ID.
     *
     * @param id ID del mensaje.
     * @return Mensaje de éxito o error.
     */
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarMensaje(@PathVariable Long id) {
        if (mensajeServicio.deleteById(id)) {
            return ResponseEntity.ok("Mensaje eliminado con éxito.");
        }
        return ResponseEntity.status(404).body("Mensaje no encontrado.");
    }

    /**
     * JSON: Devuelve una lista de mensajes asociados a un tablón de centro.
     *
     * @param idTablonCentro ID del tablón de centro.
     * @return Lista de mensajes filtrados por tablón.
     */
    @GetMapping("/filtrarPorTC/{idTablonCentro}")
    public ResponseEntity<List<Mensaje>> listarMensajesPorTablonCentro(@PathVariable Long idTablonCentro) {
        return ResponseEntity.ok(mensajeServicio.findByTablonCentro(idTablonCentro));
    }
}
