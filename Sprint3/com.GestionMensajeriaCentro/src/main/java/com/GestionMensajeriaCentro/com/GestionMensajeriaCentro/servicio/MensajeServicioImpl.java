package com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.modelo.Mensaje;
import com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.modelo.enums.Rol;
import com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.repositorio.MensajeRepo;
import com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.repositorio.TablonCentroRepo;

/**
 * Class: MensajeServicioImpl
 * Description: Implementación del servicio para la gestión de mensajes, con validaciones de rol y existencia de tablón.
 * Date: 2025-05-01
 * @author Jose Alonso Ureña
 */
@Service
public class MensajeServicioImpl implements MensajeServicio {

    private final MensajeRepo mensajeRepo;
    private final TablonCentroRepo tablonCentroRepo;

    /**
     * Constructor que inyecta los repositorios de mensajes y tablones de centro.
     *
     * @param mensajeRepositorio Repositorio para la gestión de mensajes.
     * @param tablonCentroRepositorio Repositorio para la gestión de tablones de centro.
     */
    public MensajeServicioImpl(MensajeRepo mensajeRepositorio,
                               TablonCentroRepo tablonCentroRepositorio) {
        this.mensajeRepo = mensajeRepositorio;
        this.tablonCentroRepo = tablonCentroRepositorio;
    }

    /**
     * Guarda un nuevo mensaje, validando que el publicador tenga el rol ADMIN y que el tablón de centro exista.
     *
     * @param mensaje El mensaje a guardar.
     * @return El mensaje guardado.
     * @throws IllegalArgumentException si el publicador no tiene el rol ADMIN o si el tablón de centro no existe.
     */
    @Override
    public Mensaje save(Mensaje mensaje) {
        // Validar rol
        if (!mensaje.getPublicador().getRol().equals(Rol.ADMIN)) {
            throw new IllegalArgumentException("Solo los administradores pueden publicar mensajes.");
        }

        // Validar que el tablón existe
        Long idTablon = mensaje.getTablonCentro().getIdTablonCentro();
        boolean tablonExiste = tablonCentroRepo.existsById(idTablon);

        if (!tablonExiste) {
            throw new IllegalArgumentException("El tablón de centro especificado no existe.");
        }

        return mensajeRepo.save(mensaje);
    }

    /**
     * Recupera todos los mensajes almacenados en la base de datos.
     *
     * @return Lista de mensajes.
     */
    @Override
    public List<Mensaje> findAll() {
        return mensajeRepo.findAll();
    }

    /**
     * Busca un mensaje por su ID.
     *
     * @param id ID del mensaje.
     * @return Un Optional con el mensaje si existe.
     */
    @Override
    public Optional<Mensaje> findById(Long id) {
        return mensajeRepo.findById(id);
    }

    /**
     * Elimina un mensaje por su ID.
     *
     * @param id ID del mensaje a eliminar.
     * @return true si el mensaje fue eliminado correctamente, false si no se encontró.
     */
    @Override
    public boolean deleteById(Long id) {
        if (mensajeRepo.existsById(id)) {
            mensajeRepo.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Recupera todos los mensajes asociados a un tablón de centro específico.
     *
     * @param idTablonCentro ID del tablón de centro.
     * @return Lista de mensajes publicados en el tablón de centro.
     */
    @Override
    public List<Mensaje> findByTablonCentro(Long idTablonCentro) {
        return mensajeRepo.findByTablonCentroIdTablonCentro(idTablonCentro);
    }
}
