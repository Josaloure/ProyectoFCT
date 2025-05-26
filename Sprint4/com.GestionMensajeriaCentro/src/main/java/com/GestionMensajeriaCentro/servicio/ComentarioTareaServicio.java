package com.GestionMensajeriaCentro.servicio;

import java.util.List;
import java.util.Optional;

import com.GestionMensajeriaCentro.modelo.ComentarioTarea;

/**
 * Class: ComentarioTareaServicio
 * Description: Interfaz que define las operaciones del servicio relacionadas con los comentarios de tareas.
 * Date: 2025-05-01
 * @author Jose Alonso Ureña
 */
public interface ComentarioTareaServicio {

    /**
     * Recupera todos los comentarios de tareas existentes.
     *
     * @return Lista de comentarios de tareas.
     */
    List<ComentarioTarea> findAll();

    /**
     * Guarda un nuevo comentario o actualiza uno existente.
     *
     * @param comentarioTarea Objeto ComentarioTarea a guardar.
     * @return El comentario guardado.
     */
    ComentarioTarea save(ComentarioTarea comentarioTarea);

    /**
     * Busca un comentario por su ID.
     *
     * @param id ID del comentario.
     * @return Optional con el comentario si existe, vacío si no.
     */
    Optional<ComentarioTarea> findById(Long id);

    /**
     * Elimina un comentario de tarea por su ID.
     *
     * @param id ID del comentario a eliminar.
     * @return true si fue eliminado correctamente, false si no se encontró.
     */
    boolean deleteById(Long id);
}
