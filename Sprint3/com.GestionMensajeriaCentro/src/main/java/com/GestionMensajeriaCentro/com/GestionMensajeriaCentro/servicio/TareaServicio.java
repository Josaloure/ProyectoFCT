package com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.servicio;

import java.util.List;
import java.util.Optional;

import com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.modelo.Tarea;

/**
 * Class: TareaServicio
 * Description: Interfaz que define las operaciones del servicio relacionadas con la gestión de tareas.
 * Date: 2025-05-01
 * @author Jose Alonso Ureña
 */
public interface TareaServicio {

    /**
     * Guarda una nueva tarea en la base de datos.
     *
     * @param tarea La tarea a guardar.
     * @return La tarea guardada.
     */
    Tarea save(Tarea tarea);

    /**
     * Recupera todas las tareas almacenadas en la base de datos.
     *
     * @return Lista de tareas.
     */
    List<Tarea> findAll();

    /**
     * Busca una tarea por su ID.
     *
     * @param id ID de la tarea.
     * @return Un Optional que contiene la tarea si existe, o vacío si no.
     */
    Optional<Tarea> findById(Long id);

    /**
     * Elimina una tarea por su ID.
     *
     * @param id ID de la tarea a eliminar.
     * @return true si la tarea fue eliminada correctamente, false si no se encontró.
     */
    boolean deleteById(Long id);

    /**
     * Recupera todas las tareas asociadas a un tablón de profesor específico.
     *
     * @param idTablonProfesor ID del tablón de profesor.
     * @return Lista de tareas vinculadas a ese tablón de profesor.
     */
    List<Tarea> findByTablonProfesor(Long idTablonProfesor);
}
