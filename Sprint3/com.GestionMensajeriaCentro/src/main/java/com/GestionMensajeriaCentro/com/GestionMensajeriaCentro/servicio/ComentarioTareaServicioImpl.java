package com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.modelo.ComentarioTarea;
import com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.modelo.enums.Rol;
import com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.repositorio.ComentarioTareaRepo;

/**
 * Class: ComentarioTareaServicioImpl
 * Description: Implementación del servicio para la gestión de comentarios en tareas, con validación de roles de usuario.
 * Date: 2025-05-01
 * @author Jose Alonso Ureña
 */
@Service
public class ComentarioTareaServicioImpl implements ComentarioTareaServicio {

    private final ComentarioTareaRepo comentarioTareaRepo;

    /**
     * Constructor que inyecta el repositorio de comentarios.
     *
     * @param comentarioTareaRepo Repositorio de ComentarioTarea.
     */
    public ComentarioTareaServicioImpl(ComentarioTareaRepo comentarioTareaRepo) {
        this.comentarioTareaRepo = comentarioTareaRepo;
    }

    /**
     * Obtiene todos los comentarios de tareas almacenados en la base de datos.
     *
     * @return Lista de comentarios.
     */
    @Override
    public List<ComentarioTarea> findAll() {
        return comentarioTareaRepo.findAll();
    }

    /**
     * Guarda un nuevo comentario en la base de datos. Solo usuarios con rol ADMIN o PROFESOR pueden hacerlo.
     *
     * @param comentarioTarea Comentario a guardar.
     * @return Comentario guardado.
     * @throws IllegalArgumentException si el usuario no tiene un rol permitido.
     */
    @Override
    public ComentarioTarea save(ComentarioTarea comentarioTarea) {
        Rol rolUsuario = comentarioTarea.getUsuario().getRol();

        if (rolUsuario == null || !(rolUsuario.equals(Rol.ADMIN) || rolUsuario.equals(Rol.PROFESOR))) {
            throw new IllegalArgumentException("Solo los usuarios con rol ADMIN o PROFESOR pueden comentar tareas.");
        }

        return comentarioTareaRepo.save(comentarioTarea);
    }

    /**
     * Busca un comentario de tarea por su ID.
     *
     * @param id ID del comentario.
     * @return Optional con el comentario si existe.
     */
    @Override
    public Optional<ComentarioTarea> findById(Long id) {
        return comentarioTareaRepo.findById(id);
    }

    /**
     * Elimina un comentario de tarea por su ID si existe.
     *
     * @param id ID del comentario.
     * @return true si fue eliminado, false si no se encontró.
     */
    @Override
    public boolean deleteById(Long id) {
        if (comentarioTareaRepo.existsById(id)) {
            comentarioTareaRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
