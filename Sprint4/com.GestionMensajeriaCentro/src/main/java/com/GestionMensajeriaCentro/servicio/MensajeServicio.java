package com.GestionMensajeriaCentro.servicio;

import java.util.List;
import java.util.Optional;

import com.GestionMensajeriaCentro.modelo.Mensaje;

/**
 * Class: MensajeServicio
 * Description: Interfaz que define las operaciones del servicio relacionadas con el manejo de mensajes.
 * Date: 2025-05-01
 * @author Jose Alonso Ureña
 */
public interface MensajeServicio {

    /**
     * Guarda un nuevo mensaje.
     *
     * @param mensaje El mensaje a guardar.
     * @return El mensaje guardado.
     */
    Mensaje save(Mensaje mensaje);

    /**
     * Recupera todos los mensajes almacenados en la base de datos.
     *
     * @return Lista de mensajes.
     */
    List<Mensaje> findAll();

    /**
     * Busca un mensaje por su ID.
     *
     * @param id ID del mensaje.
     * @return Un Optional con el mensaje si existe.
     */
    Optional<Mensaje> findById(Long id);

    /**
     * Elimina un mensaje por su ID.
     *
     * @param id ID del mensaje a eliminar.
     * @return true si el mensaje fue eliminado correctamente, false si no se encontró.
     */
    boolean deleteById(Long id);

    /**
     * Recupera todos los mensajes asociados a un tablón de centro específico.
     *
     * @param idTablonCentro ID del tablón de centro.
     * @return Lista de mensajes publicados en el tablón de centro.
     */
    List<Mensaje> findByTablonCentro(Long idTablonCentro);
}
