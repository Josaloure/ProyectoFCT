package com.GestionMensajeriaCentro.modelo.dto;

import com.GestionMensajeriaCentro.modelo.enums.TipoNotificacion;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class HistorialCorreoDTO {
    private Long idHistorial;
    private LocalDateTime fechaEnvio;
    private String destinatarioEmail;
    private TipoNotificacion tipoNotificacion;
    private Long idTarea;   // ID en vez de la entidad
    private Long idMensaje; // ID en vez de la entidad
}

