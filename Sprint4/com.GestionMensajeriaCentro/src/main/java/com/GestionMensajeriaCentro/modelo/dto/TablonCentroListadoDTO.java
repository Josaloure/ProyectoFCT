package com.GestionMensajeriaCentro.modelo.dto;



import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TablonCentroListadoDTO {
    private Long id;
    private String nombre;
    private String nombreUsuario;
}

