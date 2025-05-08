package com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.modelo.ComentarioTarea;

/**
 * Class: ComentarioTareaRepo
 * Description: Repositorio JPA para gestionar operaciones CRUD sobre los comentarios de tareas.
 * Date: 2025-05-01
 * @author Jose Alonso Ureña
 */
public interface ComentarioTareaRepo extends JpaRepository<ComentarioTarea, Long> {

    /**
     * Busca todos los comentarios asociados a una tarea específica por su ID.
     * 
     * @param idTarea ID de la tarea.
     * @return Lista de comentarios vinculados a esa tarea.
     */
    List<ComentarioTarea> findByTareaIdTarea(Long idTarea);

    /**
     * Busca todos los comentarios realizados por un usuario específico por su ID.
     * 
     * @param idUsuario ID del usuario.
     * @return Lista de comentarios realizados por el usuario.
     */
    List<ComentarioTarea> findByUsuarioIdUsuario(Long idUsuario);
}
