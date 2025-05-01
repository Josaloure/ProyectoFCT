package com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.modelo.HistorialCorreo;
import com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.modelo.enums.TipoNotificacion;

/**
 * Class: HistorialCorreoRepo
 * Description: Repositorio JPA para gestionar el acceso y manipulación del historial de correos enviados.
 * Date: 2025-05-01
 * @author Jose Alonso Ureña
 */
public interface HistorialCorreoRepo extends JpaRepository<HistorialCorreo, Long> {

    /**
     * Recupera todos los registros de historial de correos que coinciden con un tipo específico de notificación.
     *
     * @param tipo Tipo de notificación (TAREA o MENSAJE).
     * @return Lista de entradas del historial con ese tipo de notificación.
     */
    List<HistorialCorreo> findByTipoNotificacion(TipoNotificacion tipo);

    /**
     * Recupera todos los registros de historial de correos enviados a una dirección de correo electrónico específica.
     *
     * @param email Dirección de correo electrónico del destinatario.
     * @return Lista de entradas del historial enviadas a ese correo.
     */
    List<HistorialCorreo> findByDestinatarioEmail(String email);
}
