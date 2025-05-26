package com.GestionMensajeriaCentro.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.GestionMensajeriaCentro.modelo.Tarea;

/**
 * Class: TareaRepo
 * Description: Repositorio JPA para la gestión de tareas, permitiendo consultas por tablón, emisor y receptor.
 * Date: 2025-05-01
 * @author Jose Alonso Ureña
 */
public interface TareaRepo extends JpaRepository<Tarea, Long> {

    /**
     * Recupera todas las tareas asociadas a un tablón de profesor específico.
     *
     * @param id ID del tablón de profesor.
     * @return Lista de tareas vinculadas a ese tablón.
     */
    List<Tarea> findByTablonProfesorIdTablonProfesor(Long id);

    /**
     * Recupera todas las tareas enviadas por un usuario específico.
     *
     * @param idUsuario ID del usuario emisor.
     * @return Lista de tareas donde el usuario es el emisor.
     */
    List<Tarea> findByEmisorIdUsuario(Long idUsuario);

    /**
     * Recupera todas las tareas recibidas por un usuario específico.
     *
     * @param id ID del usuario receptor.
     * @return Lista de tareas donde el usuario es el receptor.
     */
    List<Tarea> findByReceptorIdUsuario(Long id);
}
