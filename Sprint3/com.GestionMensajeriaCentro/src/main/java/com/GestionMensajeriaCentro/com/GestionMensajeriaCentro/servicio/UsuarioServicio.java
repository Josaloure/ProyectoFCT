package com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.servicio;

import java.util.List;
import java.util.Optional;

import com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.modelo.Usuario;

/**
 * Class: UsuarioServicio
 * Description: Interfaz que define las operaciones del servicio relacionadas con la gestión de usuarios.
 * Date: 2025-05-01
 * @author Jose Alonso Ureña
 */
public interface UsuarioServicio {

    /**
     * Recupera todos los usuarios almacenados en la base de datos.
     *
     * @return Lista de usuarios.
     */
    List<Usuario> findAll();

    /**
     * Busca un usuario por su ID.
     *
     * @param id ID del usuario.
     * @return Un Optional que contiene el usuario si existe, o vacío si no se encuentra.
     */
    Optional<Usuario> findById(Long id);

    /**
     * Busca un usuario por su dirección de correo electrónico.
     *
     * @param email Dirección de correo electrónico del usuario.
     * @return Un Optional que contiene el usuario si existe, o vacío si no se encuentra.
     */
    Optional<Usuario> findByEmail(String email);

    /**
     * Verifica si existe un usuario con una dirección de correo electrónico específica.
     *
     * @param email Dirección de correo electrónico del usuario.
     * @return true si el usuario existe, false si no.
     */
    boolean existsByEmail(String email);

    /**
     * Guarda un nuevo usuario en la base de datos.
     *
     * @param usuario El usuario a guardar.
     * @return El usuario guardado.
     */
    Usuario save(Usuario usuario);

    /**
     * Elimina un usuario por su ID.
     *
     * @param id ID del usuario a eliminar.
     * @return true si el usuario fue eliminado correctamente, false si no se encontró.
     */
    boolean deleteById(Long id);
}
