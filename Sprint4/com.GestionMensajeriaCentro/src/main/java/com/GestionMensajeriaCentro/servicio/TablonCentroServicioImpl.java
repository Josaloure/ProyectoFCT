/**
 * Class: TablonCentroServicioImpl
 * Description: Implementación del servicio para la gestión de los tablones de centro, utilizando el repositorio correspondiente.
 * Date: 2025-05-23
 * Author: Jose Alonso Ureña
 */
package com.GestionMensajeriaCentro.servicio;

import com.GestionMensajeriaCentro.modelo.TablonCentro;
import com.GestionMensajeriaCentro.repositorio.TablonCentroRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementación de {@link TablonCentroServicio} que utiliza {@link TablonCentroRepo}
 * para realizar operaciones CRUD sobre los tablones del centro.
 */
@Service
public class TablonCentroServicioImpl implements TablonCentroServicio {

    private final TablonCentroRepo tablonCentroRepo;

    /**
     * Constructor que inyecta el repositorio de tablones de centro.
     *
     * @param tablonCentroRepo repositorio utilizado para interactuar con la base de datos
     */
    public TablonCentroServicioImpl(TablonCentroRepo tablonCentroRepo) {
        this.tablonCentroRepo = tablonCentroRepo;
    }

    /**
     * Recupera todos los tablones de centro almacenados en la base de datos.
     *
     * @return lista completa de tablones del centro
     */
    @Override
    public List<TablonCentro> findAll() {
        return tablonCentroRepo.findAll();
    }

    /**
     * Guarda un nuevo tablón de centro en la base de datos.
     *
     * @param tablonCentro el objeto a persistir
     * @return el tablón de centro guardado
     */
    @Override
    public TablonCentro save(TablonCentro tablonCentro) {
        return tablonCentroRepo.save(tablonCentro);
    }

    /**
     * Busca un tablón de centro por su identificador único (ID).
     *
     * @param id el ID del tablón buscado
     * @return un Optional que contiene el tablón si existe
     */
    @Override
    public Optional<TablonCentro> findById(Long id) {
        return tablonCentroRepo.findById(id);
    }

    /**
     * Elimina un tablón de centro por su ID si existe en la base de datos.
     *
     * @param id el ID del tablón a eliminar
     * @return true si fue eliminado correctamente, false si no se encontró
     */
    @Override
    public boolean deleteById(Long id) {
        if (tablonCentroRepo.existsById(id)) {
            tablonCentroRepo.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Recupera todos los tablones de centro asociados a un usuario específico.
     *
     * @param idUsuario el ID del usuario al que pertenecen los tablones
     * @return lista de tablones del centro asociados al usuario
     */
    @Override
    public List<TablonCentro> obtenerPorUsuario(Long idUsuario) {
        return tablonCentroRepo.findByUsuarioIdUsuario(idUsuario);
    }
}
