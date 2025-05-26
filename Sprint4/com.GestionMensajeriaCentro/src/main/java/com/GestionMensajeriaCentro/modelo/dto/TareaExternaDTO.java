package com.GestionMensajeriaCentro.modelo.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TareaExternaDTO {
    private String titulo;
    private String descripcion;
    private String estado;
    private String categoria;
    private String prioridad;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaLimite;
    private Long receptorId; // <- CAMBIO AQUI
}
