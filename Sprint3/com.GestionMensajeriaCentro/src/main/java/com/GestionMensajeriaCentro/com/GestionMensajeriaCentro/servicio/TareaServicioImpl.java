package com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.modelo.Tarea;
import com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.repositorio.TareaRepo;

/**
 * Class: TareaServicioImpl
 * Description: Implementación del servicio para la gestión de tareas, interactuando con el repositorio correspondiente.
 * Date: 2025-05-01
 * @author Jose Alonso Ureña
 */
@Service
public class TareaServicioImpl implements TareaServicio {

    private final TareaRepo tareaRepo;

    /**
     * Constructor que inyecta el repositorio de tareas.
     *
     * @param tareaRepo Repositorio para acceder y manipular las tareas.
     */
    public TareaServicioImpl(TareaRepo tareaRepo) {
        this.tareaRepo = tareaRepo;
    }

    /**
     * Guarda una nueva tarea en la base de datos.
     *
     * @param tarea La tarea a guardar.
     * @return La tarea guardada.
     */
    @Override
    public Tarea save(Tarea tarea) {
        return tareaRepo.save(tarea);
    }

    /**
     * Recupera todas las tareas almacenadas en la base de datos.
     *
     * @return Lista de tareas.
     */
    @Override
    public List<Tarea> findAll() {
        return tareaRepo.findAll();
    }

    /**
     * Busca una tarea por su ID.
     *
     * @param id ID de la tarea.
     * @return Un Optional que contiene la tarea si existe.
     */
    @Override
    public Optional<Tarea> findById(Long id) {
        return tareaRepo.findById(id);
    }

    /**
     * Elimina una tarea por su ID.
     *
     * @param id ID de la tarea a eliminar.
     * @return true si la tarea fue eliminada correctamente, false si no se encontró.
     */
    @Override
    public boolean deleteById(Long id) {
        if (tareaRepo.existsById(id)) {
            tareaRepo.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Recupera todas las tareas asociadas a un tablón de profesor específico.
     *
     * @param idTablonProfesor ID del tablón de profesor.
     * @return Lista de tareas vinculadas a ese tablón de profesor.
     */
    @Override
    public List<Tarea> findByTablonProfesor(Long idTablonProfesor) {
        return tareaRepo.findByTablonProfesorIdTablonProfesor(idTablonProfesor);
    }
}
