package com.GestionMensajeriaCentro.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.GestionMensajeriaCentro.modelo.Mensaje;

/**
 * Class: MensajeRepo
 * Description: Repositorio JPA para la gestión de mensajes publicados en los tablones del centro.
 * Date: 2025-05-01
 * @author Jose Alonso Ureña
 */
public interface MensajeRepo extends JpaRepository<Mensaje, Long> {

    /**
     * Recupera todos los mensajes asociados a un tablón de centro específico.
     *
     * @param idTablonCentro ID del tablón del centro.
     * @return Lista de mensajes publicados en dicho tablón.
     */
    List<Mensaje> findByTablonCentroIdTablonCentro(Long idTablonCentro);


}
