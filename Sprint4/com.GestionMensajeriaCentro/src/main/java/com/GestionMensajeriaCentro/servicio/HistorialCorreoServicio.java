package com.GestionMensajeriaCentro.servicio;

import java.util.List;
import java.util.Optional;

import com.GestionMensajeriaCentro.modelo.HistorialCorreo;

/**
 * Class: HistorialCorreoServicio
 * Description: Interfaz que define las operaciones del servicio relacionadas con el historial de correos enviados.
 * Date: 2025-05-01
 * @author Jose Alonso Ureña
 */
public interface HistorialCorreoServicio {

    /**
     * Obtiene todos los registros del historial de correos.
     *
     * @return Lista de registros de historial de correos.
     */
    List<HistorialCorreo> findAll();

    /**
     * Guarda un nuevo registro en el historial de correos.
     *
     * @param historialCorreo Registro del historial de correo a guardar.
     * @return El historial de correo guardado.
     */
    HistorialCorreo save(HistorialCorreo historialCorreo);

    /**
     * Busca un registro del historial de correo por su ID.
     *
     * @param id ID del historial de correo.
     * @return Un Optional que contiene el historial si existe, o vacío si no se encuentra.
     */
    Optional<HistorialCorreo> findById(Long id);

    /**
     * Elimina un registro del historial de correo por su ID.
     *
     * @param id ID del historial de correo a eliminar.
     * @return true si fue eliminado correctamente, false si no se encontró.
     */
    boolean deleteById(Long id);
}
