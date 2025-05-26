/**
 * Class: TablonProfesorServicio
 * Description: Interfaz que define las operaciones del servicio relacionadas con la gestión de los tablones de profesores.
 * Date: 2025-05-23
 * Author: Jose Alonso Ureña
 */
package com.GestionMensajeriaCentro.servicio;

import java.util.List;
import java.util.Optional;

import com.GestionMensajeriaCentro.modelo.TablonProfesor;

/**
 * Interfaz de servicio para la gestión de tablones de profesor.
 * Define operaciones de persistencia, consulta y eliminación.
 */
public interface TablonProfesorServicio {

    /**
     * Obtiene todos los tablones de profesor almacenados en la base de datos.
     *
     * @return lista de todos los tablones de profesor
     */
    List<TablonProfesor> findAll();

    /**
     * Guarda un nuevo tablón de profesor en la base de datos.
     *
     * @param tablonProfesor el objeto TablonProfesor a guardar
     * @return el tablón de profesor guardado
     */
    TablonProfesor save(TablonProfesor tablonProfesor);

    /**
     * Busca un tablón de profesor por su identificador único (ID).
     *
     * @param id el ID del tablón a buscar
     * @return un Optional que contiene el tablón si existe
     */
    Optional<TablonProfesor> findById(Long id);

    /**
     * Elimina un tablón de profesor de la base de datos por su ID.
     *
     * @param id el ID del tablón a eliminar
     * @return true si el tablón fue eliminado correctamente, false si no se encontró
     */
    boolean deleteById(Long id);

    /**
     * Recupera los tablones de profesor asociados a un usuario concreto.
     *
     * @param idUsuario el ID del usuario al que pertenecen los tablones
     * @return lista de tablones del profesor asociados al usuario
     */
    List<TablonProfesor> findByUsuarioIdUsuario(Long idUsuario);
}
