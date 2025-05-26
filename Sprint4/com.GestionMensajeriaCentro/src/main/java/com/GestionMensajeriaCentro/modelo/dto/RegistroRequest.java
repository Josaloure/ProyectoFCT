package com.GestionMensajeriaCentro.modelo.dto;

import com.GestionMensajeriaCentro.modelo.enums.Rol;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistroRequest {
    private String nombre;
    private String apellidos;
    private String nick;
    private String email;
    private String clave;
    private Rol rol; // ADMIN, PROFESOR, etc.
    private Boolean usuarioMovil;
}
