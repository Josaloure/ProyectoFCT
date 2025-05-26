package com.GestionMensajeriaCentro.modelo.dto;


import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ComentarioTareaDTO {
    private String contenido;
    private Long idTarea;
    private Long idUsuario;
    private LocalDateTime fechaComentario;
}
