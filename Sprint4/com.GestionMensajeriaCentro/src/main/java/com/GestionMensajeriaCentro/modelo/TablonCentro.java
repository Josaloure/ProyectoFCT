package com.GestionMensajeriaCentro.modelo;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

/**
 * Class: TablonCentro
 * Description: Representa un tablón general del centro educativo, asociado a un usuario responsable de su gestión.
 * Date: 2025-05-01
 * @author Jose Alonso Ureña
 */
@Entity
@Table(name = "tablon_centro")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TablonCentro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tablon_centro")
    private Long idTablonCentro;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    @JsonBackReference
    private Usuario usuario;
}
