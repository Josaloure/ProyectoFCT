package com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.modelo.TablonCentro;
import com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.repositorio.TablonCentroRepo;

/**
 * Class: TablonCentroServicioImpl
 * Description: Implementación del servicio para la gestión de los tablones de centro, interactuando con el repositorio correspondiente.
 * Date: 2025-05-01
 * @author Jose Alonso Ureña
 */
@Service
public class TablonCentroServicioImpl implements TablonCentroServicio {

    private final TablonCentroRepo tablonCentroRepo;

    /**
     * Constructor que inyecta el repositorio de tablones de centro.
     *
     * @param tablonCentroRepo Repositorio para acceder y manipular los tablones de centro.
     */
    public TablonCentroServicioImpl(TablonCentroRepo tablonCentroRepo) {
        this.tablonCentroRepo = tablonCentroRepo;
    }

    /**
     * Recupera todos los tablones de centro almacenados en la base de datos.
     *
     * @return Lista de tablones de centro.
     */
    @Override
    public List<TablonCentro> findAll() {
        return tablonCentroRepo.findAll();
    }

    /**
     * Guarda un nuevo tablón de centro en la base de datos.
     *
     * @param tablonCentro El tablón de centro a guardar.
     * @return El tablón de centro guardado.
     */
    @Override
    public TablonCentro save(TablonCentro tablonCentro) {
        return tablonCentroRepo.save(tablonCentro);
    }

    /**
     * Busca un tablón de centro por su ID.
     *
     * @param id ID del tablón de centro.
     * @return Un Optional con el tablón de centro si existe.
     */
    @Override
    public Optional<TablonCentro> findById(Long id) {
        return tablonCentroRepo.findById(id);
    }

    /**
     * Elimina un tablón de centro por su ID.
     *
     * @param id ID del tablón de centro a eliminar.
     * @return true si el tablón fue eliminado correctamente, false si no se encontró.
     */
    @Override
    public boolean deleteById(Long id) {
        if (tablonCentroRepo.existsById(id)) {
            tablonCentroRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
