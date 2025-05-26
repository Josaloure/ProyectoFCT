package com.GestionMensajeriaCentro.modelo;

import java.util.List;

import javax.persistence.*;


import com.GestionMensajeriaCentro.modelo.enums.Rol;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

/**
 * Class: Usuario
 * Description: Representa un usuario del sistema de mensajería del centro educativo, incluyendo sus datos personales y relaciones con tablones.
 * Date: 2025-05-01
 * @author Jose Alonso Ureña
 */
@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "nick", nullable = false)
    private String nick;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellidos", nullable = false)
    private String apellidos;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "clave", nullable = false)
    private String clave;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false)
    private Rol rol;

    @Column(name = "usuario_movil")
    private Boolean usuarioMovil;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<TablonCentro> tablonesCentro;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<TablonProfesor> tablonesProfesor;
}
