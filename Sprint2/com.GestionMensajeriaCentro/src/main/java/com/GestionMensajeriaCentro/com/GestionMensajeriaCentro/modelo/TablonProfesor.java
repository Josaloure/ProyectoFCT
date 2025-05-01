package com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.modelo;

import javax.persistence.*;

import lombok.*;

/**
 * Class: TablonProfesor
 * Description: Representa un tablón de tareas perteneciente a un profesor, asociado a un usuario del sistema.
 * Date: 2025-05-01
 * @author Jose Alonso Ureña
 */
@Entity
@Table(name = "tablon_profesor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TablonProfesor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tablon_profesor")
    private Long idTablonProfesor;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;
}
