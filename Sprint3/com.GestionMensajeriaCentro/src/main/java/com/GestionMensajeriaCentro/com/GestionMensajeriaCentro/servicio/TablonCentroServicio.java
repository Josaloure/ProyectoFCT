package com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.servicio;

import java.util.List;
import java.util.Optional;

import com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.modelo.TablonCentro;

/**
 * Class: TablonCentroServicio
 * Description: Interfaz que define las operaciones del servicio relacionadas con la gestión de los tablones del centro.
 * Date: 2025-05-01
 * @author Jose Alonso Ureña
 */
public interface TablonCentroServicio {

    /**
     * Obtiene todos los tablones de centro almacenados en la base de datos.
     *
     * @return Lista de tablones de centro.
     */
    List<TablonCentro> findAll();

    /**
     * Guarda un nuevo tablón de centro.
     *
     * @param tablonCentro El tablón de centro a guardar.
     * @return El tablón de centro guardado.
     */
    TablonCentro save(TablonCentro tablonCentro);

    /**
     * Busca un tablón de centro por su ID.
     *
     * @param id ID del tablón de centro.
     * @return Un Optional con el tablón de centro si existe.
     */
    Optional<TablonCentro> findById(Long id);

    /**
     * Elimina un tablón de centro por su ID.
     *
     * @param id ID del tablón de centro a eliminar.
     * @return true si el tablón fue eliminado correctamente, false si no se encontró.
     */
    boolean deleteById(Long id);
    
    
    List<TablonCentro> obtenerPorUsuario(Long idUsuario);
}
