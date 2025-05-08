package com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.modelo.Usuario;
import com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.repositorio.UsuarioRepo;

/**
 * Class: UsuarioServicioImpl
 * Description: Implementación del servicio para la gestión de usuarios, interactuando con el repositorio correspondiente.
 * Date: 2025-05-01
 * @author Jose Alonso Ureña
 */
@Service
public class UsuarioServicioImpl implements UsuarioServicio {

    private final UsuarioRepo usuarioRepo;

    /**
     * Constructor que inyecta el repositorio de usuarios.
     *
     * @param usuarioRepo Repositorio para acceder y manipular los usuarios.
     */
    public UsuarioServicioImpl(UsuarioRepo usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    /**
     * Recupera todos los usuarios almacenados en la base de datos.
     *
     * @return Lista de usuarios.
     */
    @Override
    public List<Usuario> findAll() {
        return usuarioRepo.findAll();
    }

    /**
     * Busca un usuario por su ID.
     *
     * @param id ID del usuario.
     * @return Un Optional que contiene el usuario si existe, o vacío si no se encuentra.
     */
    @Override
    public Optional<Usuario> findById(Long id) {
        return usuarioRepo.findById(id);
    }

    /**
     * Busca un usuario por su dirección de correo electrónico.
     *
     * @param email Dirección de correo electrónico del usuario.
     * @return Un Optional que contiene el usuario si existe, o vacío si no se encuentra.
     */
    @Override
    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepo.findByEmail(email);
    }

    /**
     * Verifica si existe un usuario con una dirección de correo electrónico específica.
     *
     * @param email Dirección de correo electrónico del usuario.
     * @return true si el usuario existe, false si no.
     */
    @Override
    public boolean existsByEmail(String email) {
        return usuarioRepo.existsByEmail(email);
    }

    /**
     * Guarda un nuevo usuario en la base de datos.
     *
     * @param usuario El usuario a guardar.
     * @return El usuario guardado.
     */
    @Override
    public Usuario save(Usuario usuario) {
        return usuarioRepo.save(usuario);
    }

    /**
     * Elimina un usuario por su ID.
     *
     * @param id ID del usuario a eliminar.
     * @return true si el usuario fue eliminado correctamente, false si no se encontró.
     */
    @Override
    public boolean deleteById(Long id) {
        if (usuarioRepo.existsById(id)) {
            usuarioRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
