/**
 * Class: TareaServicio
 * Description: Interfaz que define las operaciones del servicio relacionadas con la gestión de tareas.
 * Date: 2025-05-23
 * Author: Jose Alonso Ureña
 */
package com.GestionMensajeriaCentro.servicio;

import java.util.List;
import java.util.Optional;

import com.GestionMensajeriaCentro.modelo.Tarea;
import com.GestionMensajeriaCentro.modelo.dto.TareaExternaDTO;

/**
 * Interfaz de servicio para la gestión de tareas.
 * Define operaciones CRUD y funcionalidades de integración con servicios externos.
 */
public interface TareaServicio {

    /**
     * Guarda una nueva tarea en la base de datos.
     *
     * @param tarea la tarea a guardar
     * @return la tarea guardada
     */
    Tarea save(Tarea tarea);

    /**
     * Recupera todas las tareas almacenadas en la base de datos.
     *
     * @return lista de tareas existentes
     */
    List<Tarea> findAll();

    /**
     * Busca una tarea por su identificador único (ID).
     *
     * @param id el ID de la tarea a buscar
     * @return un Optional que contiene la tarea si existe, o vacío si no
     */
    Optional<Tarea> findById(Long id);

    /**
     * Exporta una tarea al tablón del profesor asociado.
     *
     * @param tarea la tarea a exportar
     */
    void exportarTareaATablon(Tarea tarea);

    /**
     * Elimina una tarea de la base de datos utilizando su ID.
     *
     * @param id el ID de la tarea a eliminar
     * @return true si la tarea fue eliminada correctamente, false si no se encontró
     */
    boolean deleteById(Long id);

    /**
     * Recupera todas las tareas asociadas a un tablón de profesor específico.
     *
     * @param idTablonProfesor ID del tablón del profesor
     * @return lista de tareas asociadas al tablón
     */
    List<Tarea> findByTablonProfesor(Long idTablonProfesor);

    /**
     * Importa una tarea desde un sistema externo y la asocia a un tablón de profesor.
     *
     * @param externaDTO los datos de la tarea externa
     * @param idTablonProfesor ID del tablón donde se va a asociar la tarea
     * @return la tarea creada a partir de los datos externos
     */
    Tarea importarTareaExterna(TareaExternaDTO externaDTO, Long idTablonProfesor);

    /**
     * Envía una tarea a un servicio externo (por ejemplo, para integrarse con otra aplicación).
     *
     * @param tareaDTO los datos de la tarea que se van a enviar
     */
    void enviarTareaAlServicioExterno(TareaExternaDTO tareaDTO);
}
