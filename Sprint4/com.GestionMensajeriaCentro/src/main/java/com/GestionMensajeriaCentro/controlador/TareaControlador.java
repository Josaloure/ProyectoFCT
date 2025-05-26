package com.GestionMensajeriaCentro.controlador;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import com.GestionMensajeriaCentro.modelo.dto.TareaListadoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.GestionMensajeriaCentro.modelo.Tarea;
import com.GestionMensajeriaCentro.modelo.dto.TareaExternaDTO;
import com.GestionMensajeriaCentro.servicio.TareaServicio;

/**
 * Class: TareaControlador
 * Description: Controlador para gestionar las tareas del sistema, incluyendo su visualización, creación, eliminación y filtrado.
 * Date: 2025-05-01
 * @author Jose Alonso Ureña
 */
@Controller
@RequestMapping("/profesor/tareas")
public class TareaControlador {

    private final TareaServicio tareaServicio;

    public TareaControlador(TareaServicio tareaServicio) {
        this.tareaServicio = tareaServicio;
    }

    /**
     * Devuelve una vista HTML con la lista de todas las tareas.
     *
     * @param model Modelo de datos para la vista.
     * @return Nombre del archivo HTML con la lista de tareas.
     */
    @GetMapping("/vista")
    public String vistaTareas(Model model) {
        model.addAttribute("tareas", tareaServicio.findAll());
        return "tareaLista";
    }

    /**
     * Devuelve todas las tareas en formato JSON como una lista de TareaListadoDTO.
     *
     * @return Lista de tareas simplificadas.
     */
    @GetMapping("/listar")
    @ResponseBody
    public ResponseEntity<List<TareaListadoDTO>> listarTareas() {
        List<TareaListadoDTO> dtoList = tareaServicio.findAll().stream()
                .map(t -> TareaListadoDTO.builder()
                        .idTarea(t.getIdTarea())
                        .titulo(t.getTitulo())
                        .descripcion(t.getDescripcion())
                        .estado(String.valueOf(t.getEstado()))
                        .fechaCreacion(t.getFechaCreacion().toString())
                        .build()
                ).toList();

        return ResponseEntity.ok(dtoList);
    }

    /**
     * Devuelve una tarea específica por su ID.
     *
     * @param id ID de la tarea.
     * @return Tarea encontrada o 404 si no existe.
     */
    @GetMapping("/listarPorId/{id}")
    public ResponseEntity<Tarea> obtenerTarea(@PathVariable Long id) {
        Optional<Tarea> tarea = tareaServicio.findById(id);
        return tarea.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).build());
    }

    /**
     * Devuelve una vista HTML con el formulario para crear una nueva tarea.
     *
     * @param model Modelo de datos para la vista.
     * @return Nombre de la vista HTML.
     */
    @GetMapping("/formulario")
    public String mostrarFormulario(Model model) {
        model.addAttribute("tarea", new Tarea());
        return "tareas/formulario";
    }

    /**
     * Envía una tarea al servicio externo desde un DTO recibido por JSON.
     *
     * @param dto Objeto con los datos de la tarea externa.
     * @return Mensaje de confirmación.
     */
    @PostMapping("/enviar-externa")
    public ResponseEntity<String> enviarTarea(@RequestBody TareaExternaDTO dto) {
        tareaServicio.enviarTareaAlServicioExterno(dto);
        return ResponseEntity.ok("Enviada");
    }

    /**
     * Importa una tarea desde un DTO externo y la asocia a un tablón de profesor.
     *
     * @param idTablon ID del tablón de profesor.
     * @param dto Objeto de datos de la tarea externa.
     * @return Tarea creada en el sistema.
     */
    @PostMapping("/importar-externa/{idTablon}")
    public ResponseEntity<Tarea> importarTareaDesdeExterno(
            @PathVariable Long idTablon,
            @RequestBody TareaExternaDTO dto) {

        Tarea tarea = tareaServicio.importarTareaExterna(dto, idTablon);
        return ResponseEntity.ok(tarea);
    }

    /**
     * Elimina una tarea por su ID.
     *
     * @param id ID de la tarea.
     * @return Mensaje de éxito o error.
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
     * Lista las tareas asociadas a un tablón de profesor específico.
     *
     * @param idTablonProfesor ID del tablón de profesor.
     * @return Lista de tareas filtradas.
     */
    @GetMapping("/listarPorTF/{idTablonProfesor}")
    @ResponseBody
    public ResponseEntity<List<TareaListadoDTO>> listarTareasPorTablonProfesor(@PathVariable Long idTablonProfesor) {
        List<TareaListadoDTO> dtoList = tareaServicio.findByTablonProfesor(idTablonProfesor).stream()
                .map(t -> TareaListadoDTO.builder()
                        .idTarea(t.getIdTarea())
                        .titulo(t.getTitulo())
                        .descripcion(t.getDescripcion())
                        .estado(String.valueOf(t.getEstado()))
                        .fechaCreacion(t.getFechaCreacion().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                        .build())
                .toList();

        return ResponseEntity.ok(dtoList);
    }

    /**
     * Devuelve una vista HTML con el detalle de una tarea específica.
     *
     * @param id ID de la tarea.
     * @param model Modelo con los datos de la tarea.
     * @return Vista detalle de la tarea o redirección si no existe.
     */
    @GetMapping("/detalle/{id}")
    public String verDetalle(@PathVariable Long id, Model model) {
        Optional<Tarea> tarea = tareaServicio.findById(id);
        if (tarea.isPresent()) {
            model.addAttribute("tarea", tarea.get());
            return "tareas/detalle";
        }
        return "redirect:/profesor/tareas/vista";
    }
}
