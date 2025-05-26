package com.GestionMensajeriaCentro.controlador;

import com.GestionMensajeriaCentro.modelo.Usuario;
import com.GestionMensajeriaCentro.modelo.dto.TablonProfesorDTO;
import com.GestionMensajeriaCentro.modelo.dto.TablonProfesorListadoDTO;
import com.GestionMensajeriaCentro.repositorio.UsuarioRepo;
import com.GestionMensajeriaCentro.modelo.TablonProfesor;
import com.GestionMensajeriaCentro.servicio.TablonProfesorServicio;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Class: TablonProfesorControlador
 * Description: Controlador para la gestión de los tablones del profesor, permitiendo crear, listar, actualizar, eliminar y visualizar tablones en vistas HTML o JSON.
 * Date: 2025-05-01
 * Author: Jose Alonso Ureña
 */
@Controller
@RequestMapping("/profesor/tablonProfesor")
public class TablonProfesorControlador {

    private final TablonProfesorServicio servicio;
    private final UsuarioRepo usuarioRepo;

    public TablonProfesorControlador(TablonProfesorServicio servicio, UsuarioRepo usuarioRepo) {
        this.servicio = servicio;
        this.usuarioRepo = usuarioRepo;
    }

    /**
     * Devuelve una vista con los detalles de un tablón específico.
     *
     * @param model Modelo de la vista.
     * @param id ID del tablón.
     * @return Vista detallada del tablón o mensaje de error.
     */
    @GetMapping("/vista/{id}")
    public String verTablon(Model model, @PathVariable Long id) {
        Optional<TablonProfesor> tablon = servicio.findById(id);
        if (tablon.isPresent()) {
            model.addAttribute("tablon", tablon.get());
            return "tablonListar";
        } else {
            model.addAttribute("mensaje", "El tablón solicitado no existe.");
            return "error";
        }
    }

    /**
     * Devuelve una lista de tablones asociados a un usuario específico.
     *
     * @param idUsuario ID del usuario.
     * @return Lista de TablonProfesorListadoDTO.
     */
    @GetMapping("/listarPorUsuario/{idUsuario}")
    public ResponseEntity<List<TablonProfesorListadoDTO>> obtenerPorUsuario(@PathVariable Long idUsuario) {
        List<TablonProfesor> tablones = servicio.findByUsuarioIdUsuario(idUsuario);

        List<TablonProfesorListadoDTO> resultado = tablones.stream().map(tablon ->
                TablonProfesorListadoDTO.builder()
                        .idTablonProfesor(tablon.getIdTablonProfesor())
                        .nombre(tablon.getNombre())
                        .nombreUsuario(tablon.getUsuario().getNombre())
                        .build()
        ).toList();

        return ResponseEntity.ok(resultado);
    }

    /**
     * Devuelve una lista de todos los tablones del profesor.
     *
     * @return Lista de TablonProfesorListadoDTO.
     */
    @GetMapping("/consultarTodos")
    @ResponseBody
    public List<TablonProfesorListadoDTO> consultarTodos() {
        List<TablonProfesor> tablones = servicio.findAll();

        return tablones.stream().map(t -> TablonProfesorListadoDTO.builder()
                .idTablonProfesor(t.getIdTablonProfesor())
                .nombre(t.getNombre())
                .nombreUsuario(t.getUsuario().getNombre())
                .build()
        ).toList();
    }

    /**
     * Devuelve la vista con el formulario para crear un nuevo tablón.
     *
     * @param model Modelo de la vista.
     * @return Nombre de la vista del formulario.
     */
    @GetMapping("/formulario")
    public String mostrarFormulario(Model model) {
        model.addAttribute("tablonProfesor", new TablonProfesor());
        return "tablonProfesor/formulario";
    }

    /**
     * Crea un nuevo tablón de profesor.
     *
     * @param dto DTO con los datos del nuevo tablón.
     * @return Objeto creado o mensaje de error.
     */
    @PostMapping("/crear")
    public ResponseEntity<?> crear(@RequestBody TablonProfesorDTO dto) {
        try {
            Usuario usuario = usuarioRepo.findById(dto.getIdUsuario())
                    .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

            TablonProfesor tablon = TablonProfesor.builder()
                    .nombre(dto.getNombre())
                    .usuario(usuario)
                    .build();

            TablonProfesor creado = servicio.save(tablon);
            return ResponseEntity.status(201).body(creado);

        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error al crear el tablón del profesor: " + e.getMessage());
        }
    }

    /**
     * Actualiza un tablón existente.
     *
     * @param id ID del tablón.
     * @param dto DTO con los nuevos datos.
     * @return Tablón actualizado o mensaje de error.
     */
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody TablonProfesorDTO dto) {
        try {
            Usuario usuario = usuarioRepo.findById(dto.getIdUsuario())
                    .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

            TablonProfesor actualizado = TablonProfesor.builder()
                    .idTablonProfesor(id)
                    .nombre(dto.getNombre())
                    .usuario(usuario)
                    .build();

            TablonProfesor guardado = servicio.save(actualizado);
            return ResponseEntity.ok(guardado);

        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error al actualizar el tablón: " + e.getMessage());
        }
    }

    /**
     * Elimina un tablón de profesor por su ID.
     *
     * @param id ID del tablón a eliminar.
     * @return Mensaje de éxito o error.
     */
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        if (servicio.deleteById(id)) {
            return ResponseEntity.ok("Tablón de Profesor con id " + id + " ha sido eliminado correctamente.");
        }
        return ResponseEntity.status(404).body("No se pudo eliminar el Tablón de Profesor con id " + id);
    }

    /**
     * Devuelve la vista principal del listado de tablones.
     *
     * @return Nombre de la vista del listado.
     */
    @GetMapping("/lista")
    public String vistaListaTablon() {
        return "tablonListar";
    }
}
