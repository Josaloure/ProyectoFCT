/**
 * Class: TablonProfesorServicioImpl
 * Description: Implementación del servicio para la gestión de los tablones de profesores, interactuando con el repositorio correspondiente.
 * Date: 2025-05-23
 * Author: Jose Alonso Ureña
 */
package com.GestionMensajeriaCentro.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.GestionMensajeriaCentro.modelo.TablonProfesor;
import com.GestionMensajeriaCentro.repositorio.TablonProfesorRepo;

/**
 * Implementación de {@link TablonProfesorServicio} que utiliza {@link TablonProfesorRepo}
 * para acceder a los datos de los tablones de profesores.
 */
@Service
public class TablonProfesorServicioImpl implements TablonProfesorServicio {

    private final TablonProfesorRepo tablonProfesorRepo;

    /**
     * Constructor que inyecta el repositorio de tablones de profesores.
     *
     * @param tablonProfesorRepo repositorio para acceder y manipular los tablones de profesores
     */
    public TablonProfesorServicioImpl(TablonProfesorRepo tablonProfesorRepo) {
        this.tablonProfesorRepo = tablonProfesorRepo;
    }

    /**
     * Recupera todos los tablones de profesores almacenados en la base de datos.
     *
     * @return lista de todos los tablones de profesores
     */
    @Override
    public List<TablonProfesor> findAll() {
        return tablonProfesorRepo.findAll();
    }

    /**
     * Guarda un nuevo tablón de profesor en la base de datos.
     *
     * @param tablonProfesor el objeto TablonProfesor a guardar
     * @return el tablón de profesor guardado
     */
    @Override
    public TablonProfesor save(TablonProfesor tablonProfesor) {
        return tablonProfesorRepo.save(tablonProfesor);
    }

    /**
     * Busca un tablón de profesor por su identificador único (ID).
     *
     * @param id el ID del tablón de profesor
     * @return un Optional que contiene el tablón si existe
     */
    @Override
    public Optional<TablonProfesor> findById(Long id) {
        return tablonProfesorRepo.findById(id);
    }

    /**
     * Elimina un tablón de profesor por su ID, si existe.
     *
     * @param id el ID del tablón a eliminar
     * @return true si fue eliminado correctamente, false si no se encontró
     */
    @Override
    public boolean deleteById(Long id) {
        if (tablonProfesorRepo.existsById(id)) {
            tablonProfesorRepo.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Recupera todos los tablones asociados a un usuario específico.
     *
     * @param idUsuario el ID del usuario propietario de los tablones
     * @return lista de tablones del profesor vinculados al usuario
     */
    @Override
    public List<TablonProfesor> findByUsuarioIdUsuario(Long idUsuario) {
        return tablonProfesorRepo.findByUsuarioIdUsuario(idUsuario);
    }
}
