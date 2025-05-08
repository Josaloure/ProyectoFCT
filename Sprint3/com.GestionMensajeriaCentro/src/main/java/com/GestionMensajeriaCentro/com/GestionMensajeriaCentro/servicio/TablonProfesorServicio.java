package com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.servicio;

import java.util.List;
import java.util.Optional;

import com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.modelo.TablonProfesor;

/**
 * Class: TablonProfesorServicio
 * Description: Interfaz que define las operaciones del servicio relacionadas con la gestión de los tablones de profesores.
 * Date: 2025-05-01
 * @author Jose Alonso Ureña
 */
public interface TablonProfesorServicio {

    /**
     * Obtiene todos los tablones de profesor almacenados en la base de datos.
     *
     * @return Lista de tablones de profesor.
     */
    List<TablonProfesor> findAll();

    /**
     * Guarda un nuevo tablón de profesor en la base de datos.
     *
     * @param tablonProfesor El tablón de profesor a guardar.
     * @return El tablón de profesor guardado.
     */
    TablonProfesor save(TablonProfesor tablonProfesor);

    /**
     * Busca un tablón de profesor por su ID.
     *
     * @param id ID del tablón de profesor.
     * @return Un Optional con el tablón de profesor si existe.
     */
    Optional<TablonProfesor> findById(Long id);

    /**
     * Elimina un tablón de profesor por su ID.
     *
     * @param id ID del tablón de profesor a eliminar.
     * @return true si el tablón fue eliminado correctamente, false si no se encontró.
     */
    boolean deleteById(Long id);
}
