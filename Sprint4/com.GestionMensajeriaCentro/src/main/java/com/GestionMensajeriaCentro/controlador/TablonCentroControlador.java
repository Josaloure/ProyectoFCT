package com.GestionMensajeriaCentro.controlador;

import java.util.List;
import java.util.Optional;

import com.GestionMensajeriaCentro.modelo.Usuario;
import com.GestionMensajeriaCentro.modelo.dto.TablonCentroDTO;
import com.GestionMensajeriaCentro.modelo.dto.TablonCentroListadoDTO;
import com.GestionMensajeriaCentro.repositorio.UsuarioRepo;
import com.GestionMensajeriaCentro.modelo.TablonCentro;
import com.GestionMensajeriaCentro.servicio.TablonCentroServicio;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Class: TablonCentroControlador
 * Description: Controlador para la gestión de tablones del centro, incluyendo creación, visualización, actualización, eliminación y consultas asociadas al usuario.
 * Date: 2025-05-01
 * Author: Jose Alonso Ureña
 */
@Controller
@RequestMapping("/admin/tablonCentro")
public class TablonCentroControlador {

    private final TablonCentroServicio servicio;
    private final UsuarioRepo usuarioRepo;

    public TablonCentroControlador(TablonCentroServicio servicio, UsuarioRepo usuarioRepo) {
        this.servicio = servicio;
        this.usuarioRepo = usuarioRepo;
    }

    /**
     * Devuelve la vista principal para la gestión de tablones del centro.
     *
     * @return Nombre del archivo HTML.
     */
    @GetMapping("/vista")
    public String mostrarVistaFormulario() {
        return "tablonCentro";
    }

    /**
     * Muestra la vista con los detalles de un tablón de centro específico.
     *
     * @param model Modelo para la vista.
     * @param id ID del tablón.
     * @return Vista detallada o vista de error si no se encuentra.
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
     * Devuelve un tablón de centro por su ID.
     *
     * @param id ID del tablón.
     * @return Tablón encontrado o error 404.
     */
    @GetMapping("/listarTC/{id}")
    public ResponseEntity<TablonCentro> obtener(@PathVariable Long id) {
        return servicio.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).build());
    }

    /**
     * Devuelve el formulario HTML para la creación de un nuevo tablón.
     *
     * @param model Modelo de la vista.
     * @return Nombre de la vista HTML.
     */
    @GetMapping("/formulario")
    public String mostrarFormulario(Model model) {
        model.addAttribute("tablonCentro", new TablonCentro());
        return "tablonCentro/formulario";
    }

    /**
     * Crea un nuevo tablón de centro a partir de los datos proporcionados.
     *
     * @param dto DTO con los datos del nuevo tablón.
     * @return Objeto creado o mensaje de error.
     */
    @PostMapping("/crear")
    @ResponseBody
    public ResponseEntity<?> crear(@RequestBody TablonCentroDTO dto) {
        try {
            Usuario usuario = usuarioRepo.findById(dto.getIdUsuario())
                    .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

            TablonCentro tablon = TablonCentro.builder()
                    .nombre(dto.getNombre())
                    .usuario(usuario)
                    .build();

            TablonCentro creado = servicio.save(tablon);
            return ResponseEntity.status(201).body(creado);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body("Datos inválidos: " + e.getMessage());
        }
    }

    /**
     * Actualiza un tablón de centro existente.
     *
     * @param id ID del tablón a actualizar.
     * @param dto DTO con los nuevos datos.
     * @return Tablón actualizado o mensaje de error.
     */
    @PutMapping("/actualizar/{id}")
    @ResponseBody
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody TablonCentroDTO dto) {
        try {
            Usuario usuario = usuarioRepo.findById(dto.getIdUsuario())
                    .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

            TablonCentro actualizado = TablonCentro.builder()
                    .idTablonCentro(id)
                    .nombre(dto.getNombre())
                    .usuario(usuario)
                    .build();

            TablonCentro guardado = servicio.save(actualizado);
            return ResponseEntity.ok(guardado);

        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error al actualizar el tablón de centro.");
        }
    }

    /**
     * Elimina un tablón de centro por su ID.
     *
     * @param id ID del tablón a eliminar.
     * @return Mensaje de éxito o error.
     */
    @DeleteMapping("/eliminar/{id}")
    @ResponseBody
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        if (servicio.deleteById(id)) {
            return ResponseEntity.ok("Tablón de Centro con id " + id + " ha sido eliminado correctamente.");
        }
        return ResponseEntity.status(404).body("No se pudo eliminar el Tablón de Centro con id " + id);
    }

    /**
     * Devuelve todos los tablones de centro creados por un usuario específico.
     *
     * @param idUsuario ID del usuario.
     * @return Lista de tablones en formato DTO reducido.
     */
    @GetMapping("/consultarPorUsuario/{idUsuario}")
    @ResponseBody
    public List<TablonCentroListadoDTO> obtenerPorUsuario(@PathVariable Long idUsuario) {
        return servicio.obtenerPorUsuario(idUsuario).stream()
                .map(t -> TablonCentroListadoDTO.builder()
                        .id(t.getIdTablonCentro())
                        .nombre(t.getNombre())
                        .nombreUsuario(t.getUsuario().getNombre())
                        .build())
                .toList();
    }
}
