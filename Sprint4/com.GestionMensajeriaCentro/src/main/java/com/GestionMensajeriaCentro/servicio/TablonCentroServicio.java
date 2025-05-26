/**
 * Class: TablonCentroServicio
 * Description: Interfaz que define las operaciones del servicio relacionadas con la gestión de los tablones del centro.
 * Date: 2025-05-23
 * Author: Jose Alonso Ureña
 */
package com.GestionMensajeriaCentro.servicio;

import com.GestionMensajeriaCentro.modelo.TablonCentro;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz de servicio para la gestión de los tablones del centro.
 * Proporciona métodos para consultar, guardar y eliminar tablones,
 * así como para filtrar por usuario.
 */
public interface TablonCentroServicio {

    /**
     * Obtiene todos los tablones de centro almacenados en la base de datos.
     *
     * @return lista de todos los tablones del centro
     */
    List<TablonCentro> findAll();

    /**
     * Guarda un nuevo tablón de centro en la base de datos.
     *
     * @param tablonCentro el objeto TablonCentro a guardar
     * @return el tablón de centro guardado
     */
    TablonCentro save(TablonCentro tablonCentro);

    /**
     * Busca un tablón de centro por su identificador único (ID).
     *
     * @param id el ID del tablón a buscar
     * @return un Optional que contiene el tablón si existe
     */
    Optional<TablonCentro> findById(Long id);

    /**
     * Elimina un tablón de centro por su ID, si existe.
     *
     * @param id el ID del tablón a eliminar
     * @return true si fue eliminado correctamente, false si no se encontró
     */
    boolean deleteById(Long id);

    /**
     * Obtiene los tablones de centro asociados a un usuario específico.
     *
     * @param idUsuario el ID del usuario
     * @return lista de tablones del centro asociados al usuario
     */
    List<TablonCentro> obtenerPorUsuario(Long idUsuario);
}
