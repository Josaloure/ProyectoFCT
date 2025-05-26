package com.GestionMensajeriaCentro.modelo.dto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MensajeDTO {
    private String titulo;
    private String contenido;
    private Long idTablonCentro;
    private boolean publicado;
    private boolean visibleParaTodos;
}
