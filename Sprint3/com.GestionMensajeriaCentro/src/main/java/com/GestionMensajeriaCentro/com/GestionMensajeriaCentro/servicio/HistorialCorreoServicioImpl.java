package com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.modelo.HistorialCorreo;
import com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.repositorio.HistorialCorreoRepo;

/**
 * Class: HistorialCorreoServicioImpl
 * Description: Implementación del servicio para gestionar el historial de correos enviados, interactuando con el repositorio correspondiente.
 * Date: 2025-05-01
 * @author Jose Alonso Ureña
 */
@Service
public class HistorialCorreoServicioImpl implements HistorialCorreoServicio {

    private final HistorialCorreoRepo historialCorreoRepo;

    /**
     * Constructor que inyecta el repositorio de historial de correos.
     *
     * @param historialCorreoRepo Repositorio para acceder y manipular los registros del historial de correos.
     */
    public HistorialCorreoServicioImpl(HistorialCorreoRepo historialCorreoRepo) {
        this.historialCorreoRepo = historialCorreoRepo;
    }

    /**
     * Recupera todos los registros del historial de correos.
     *
     * @return Lista de registros del historial de correos.
     */
    @Override
    public List<HistorialCorreo> findAll() {
        return historialCorreoRepo.findAll();
    }

    /**
     * Guarda un nuevo registro en el historial de correos.
     *
     * @param historialCorreo Registro del historial de correo a guardar.
     * @return El historial de correo guardado.
     */
    @Override
    public HistorialCorreo save(HistorialCorreo historialCorreo) {
        return historialCorreoRepo.save(historialCorreo);
    }

    /**
     * Busca un registro del historial de correo por su ID.
     *
     * @param id ID del historial de correo.
     * @return Un Optional que contiene el historial si existe.
     */
    @Override
    public Optional<HistorialCorreo> findById(Long id) {
        return historialCorreoRepo.findById(id);
    }

    /**
     * Elimina un registro del historial de correo por su ID.
     *
     * @param id ID del historial de correo a eliminar.
     * @return true si el registro fue eliminado correctamente, false si no se encontró.
     */
    @Override
    public boolean deleteById(Long id) {
        if (historialCorreoRepo.existsById(id)) {
            historialCorreoRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
