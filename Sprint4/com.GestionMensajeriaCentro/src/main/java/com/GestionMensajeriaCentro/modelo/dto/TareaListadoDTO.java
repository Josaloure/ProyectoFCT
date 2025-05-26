package com.GestionMensajeriaCentro.modelo.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TareaListadoDTO {
    private Long idTarea;
    private String titulo;
    private String descripcion;
    private String estado;
    private String fechaCreacion;
}
