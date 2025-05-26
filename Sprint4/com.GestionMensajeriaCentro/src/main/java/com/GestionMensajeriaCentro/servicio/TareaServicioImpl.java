/**
 * Class: TareaServicioImpl
 * Description: Implementación del servicio de gestión de tareas. Maneja operaciones de persistencia,
 *              importación, exportación y filtrado de tareas.
 * Date: 2025-05-23
 * Author: Jose Alonso Ureña
 */
package com.GestionMensajeriaCentro.servicio;

import com.GestionMensajeriaCentro.modelo.Tarea;
import com.GestionMensajeriaCentro.modelo.Usuario;
import com.GestionMensajeriaCentro.modelo.dto.TareaExternaDTO;
import com.GestionMensajeriaCentro.modelo.enums.Categoria;
import com.GestionMensajeriaCentro.modelo.enums.Estado;
import com.GestionMensajeriaCentro.modelo.enums.Prioridad;
import com.GestionMensajeriaCentro.repositorio.TareaRepo;
import com.GestionMensajeriaCentro.modelo.TablonProfesor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementación de {@link TareaServicio} que interactúa con el repositorio de tareas
 * y otros servicios relacionados como tablones y usuarios.
 */
@Service
public class TareaServicioImpl implements TareaServicio {

    private final TareaRepo tareaRepo;
    private final TablonProfesorServicio tablonProfesorServicio;
    private final UsuarioServicio usuarioServicio;

    /**
     * Constructor que inyecta los repositorios y servicios necesarios.
     */
    public TareaServicioImpl(TareaRepo tareaRepo, TablonProfesorServicio tablonProfesorServicio, UsuarioServicio usuarioServicio) {
        this.tareaRepo = tareaRepo;
        this.tablonProfesorServicio = tablonProfesorServicio;
        this.usuarioServicio = usuarioServicio;
    }

    /**
     * Recupera todas las tareas almacenadas.
     */
    @Override
    public List<Tarea> findAll() {
        return tareaRepo.findAll();
    }

    /**
     * Busca una tarea por ID. Método actualmente no implementado.
     */
    @Override
    public Optional<Tarea> findById(Long id) {
        return Optional.empty();
    }

    /**
     * Guarda una nueva tarea en la base de datos.
     */
    @Override
    public Tarea save(Tarea tarea) {
        return tareaRepo.save(tarea);
    }

    /**
     * Crea una nueva tarea a partir de datos externos (DTO) y la asocia a un tablón.
     * Asigna automáticamente al emisor según el usuario autenticado.
     */
    @Override
    public Tarea importarTareaExterna(TareaExternaDTO dto, Long idTablonProfesor) {
        // Buscar el tablón asociado
        TablonProfesor tablon = tablonProfesorServicio.findById(idTablonProfesor)
                .orElseThrow(() -> new IllegalArgumentException("Tablón no encontrado"));

        // Obtener el email del usuario autenticado
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        // Buscar emisor y receptor
        Usuario emisor = usuarioServicio.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Emisor no encontrado con email: " + email));

        Usuario receptor = usuarioServicio.findById(dto.getReceptorId())
                .orElseThrow(() -> new RuntimeException("Receptor no encontrado con ID: " + dto.getReceptorId()));

        // Crear nueva tarea a partir de los datos del DTO
        Tarea tarea = new Tarea();
        tarea.setTitulo(dto.getTitulo());
        tarea.setDescripcion(dto.getDescripcion());
        tarea.setEstado(Estado.valueOf(dto.getEstado()));
        tarea.setFechaCreacion(dto.getFechaCreacion().toLocalDate());
        tarea.setFechaLimite(dto.getFechaLimite() != null ? dto.getFechaLimite().toLocalDate() : null);
        tarea.setCategoria(Categoria.valueOf(dto.getCategoria()));
        tarea.setPrioridad(Prioridad.valueOf(dto.getPrioridad()));
        tarea.setTablonProfesor(tablon);
        tarea.setEmisor(emisor);
        tarea.setReceptor(receptor);

        return tareaRepo.save(tarea);
    }

    /**
     * Método pendiente de implementación para enviar tareas al servicio externo.
     */
    @Override
    public void enviarTareaAlServicioExterno(TareaExternaDTO tareaDTO) {
        // Implementación futura
    }

    /**
     * Exporta una tarea a su tablón de profesor asociado. Lógica de negocio pendiente.
     */
    @Override
    public void exportarTareaATablon(Tarea tarea) {
        // Obtener el tablón de la tarea
        TablonProfesor tablon = tarea.getTablonProfesor();
        // Lógica de exportación (por implementar)
    }

    /**
     * Elimina una tarea por su ID si existe.
     */
    @Override
    public boolean deleteById(Long id) {
        if (tareaRepo.existsById(id)) {
            tareaRepo.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Recupera las tareas asociadas a un tablón de profesor.
     * Actualmente retorna una lista vacía.
     */
    @Override
    public List<Tarea> findByTablonProfesor(Long idTablonProfesor) {
        return List.of(); // Implementación futura
    }
}
