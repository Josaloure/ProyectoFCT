package com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.modelo.TablonProfesor;

/**
 * Class: TablonProfesorRepo
 * Description: Repositorio JPA para gestionar los tablones de profesores en el sistema.
 * Date: 2025-05-01
 * @author Jose Alonso Ureña
 */
public interface TablonProfesorRepo extends JpaRepository<TablonProfesor, Long> {

    /**
     * Recupera todos los tablones de profesor asociados a un usuario específico.
     *
     * @param idUsuario ID del usuario (profesor).
     * @return Lista de tablones gestionados por ese profesor.
     */
    List<TablonProfesor> findByUsuarioIdUsuario(Long idUsuario);
}
