package com.GestionMensajeriaCentro.modelo.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TablonProfesorListadoDTO {
    private Long idTablonProfesor;
    private String nombre;
    private String nombreUsuario;
}

