package com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.modelo.TablonProfesor;
import com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.repositorio.TablonProfesorRepo;

/**
 * Class: TablonProfesorServicioImpl
 * Description: Implementación del servicio para la gestión de los tablones de profesores, interactuando con el repositorio correspondiente.
 * Date: 2025-05-01
 * @author Jose Alonso Ureña
 */
@Service
public class TablonProfesorServicioImpl implements TablonProfesorServicio {

    private final TablonProfesorRepo tablonProfesorRepo;

    /**
     * Constructor que inyecta el repositorio de tablones de profesores.
     *
     * @param tablonProfesorRepo Repositorio para acceder y manipular los tablones de profesores.
     */
    public TablonProfesorServicioImpl(TablonProfesorRepo tablonProfesorRepo) {
        this.tablonProfesorRepo = tablonProfesorRepo;
    }

    /**
     * Recupera todos los tablones de profesores almacenados en la base de datos.
     *
     * @return Lista de tablones de profesores.
     */
    @Override
    public List<TablonProfesor> findAll() {
        return tablonProfesorRepo.findAll();
    }

    /**
     * Guarda un nuevo tablón de profesor en la base de datos.
     *
     * @param tablonProfesor El tablón de profesor a guardar.
     * @return El tablón de profesor guardado.
     */
    @Override
    public TablonProfesor save(TablonProfesor tablonProfesor) {
        return tablonProfesorRepo.save(tablonProfesor);
    }

    /**
     * Busca un tablón de profesor por su ID.
     *
     * @param id ID del tablón de profesor.
     * @return Un Optional con el tablón de profesor si existe.
     */
    @Override
    public Optional<TablonProfesor> findById(Long id) {
        return tablonProfesorRepo.findById(id);
    }

    /**
     * Elimina un tablón de profesor por su ID.
     *
     * @param id ID del tablón de profesor a eliminar.
     * @return true si el tablón fue eliminado correctamente, false si no se encontró.
     */
    @Override
    public boolean deleteById(Long id) {
        if (tablonProfesorRepo.existsById(id)) {
            tablonProfesorRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
