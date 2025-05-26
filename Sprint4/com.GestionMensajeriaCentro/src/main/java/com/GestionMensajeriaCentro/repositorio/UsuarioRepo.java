package com.GestionMensajeriaCentro.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.GestionMensajeriaCentro.modelo.Usuario;

import java.util.Optional;

/**
 * Class: UsuarioRepo
 * Description: Repositorio JPA para gestionar operaciones relacionadas con los usuarios del sistema.
 * Date: 2025-05-01
 * @author Jose Alonso Ureña
 */
public interface UsuarioRepo extends JpaRepository<Usuario, Long> {

    /**
     * Busca un usuario por su dirección de correo electrónico.
     *
     * @param email Dirección de correo electrónico.
     * @return Un Optional que contiene el usuario si se encuentra, o vacío si no existe.
     */
    Optional<Usuario> findByEmail(String email);

    /**
     * Verifica si existe un usuario registrado con una dirección de correo electrónico específica.
     *
     * @param email Dirección de correo electrónico.
     * @return true si el usuario existe, false en caso contrario.
     */
    boolean existsByEmail(String email);
}
