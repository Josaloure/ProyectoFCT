package com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.modelo.TablonCentro;

/**
 * Class: TablonCentroRepo
 * Description: Repositorio JPA para acceder y manipular los tablones generales del centro educativo.
 * Date: 2025-05-01
 * @author Jose Alonso Ureña
 */
public interface TablonCentroRepo extends JpaRepository<TablonCentro, Long> {

    /**
     * Obtiene todos los tablones del centro asociados a un usuario específico.
     *
     * @param idUsuario ID del usuario.
     * @return Lista de tablones del centro gestionados por ese usuario.
     */
    List<TablonCentro> findByUsuarioIdUsuario(Long idUsuario);
}
