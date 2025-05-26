package com.GestionMensajeriaCentro.modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class: Mensaje
 * Description: Representa un mensaje publicado en un tablón del centro, incluyendo su contenido, fechas y estado de publicación.
 * Date: 2025-05-01
 * @author Jose Alonso Ureña
 */
@Entity
@Table(name = "mensaje")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mensaje")
    private Long idMensaje;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "contenido", nullable = false)
    private String contenido;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDate fechaCreacion;

    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDate.now();
    }

    @Column(name = "fecha_publicacion")
    private LocalDate fechaPublicacion;

    @Column(name = "publicado", nullable = false)
    private boolean publicado;

    @Column(name = "visible_para_todos", nullable = false)
    private boolean visibleParaTodos;

    @ManyToOne
    @JoinColumn(name = "id_publicador", nullable = false)
    private Usuario publicador;

    @ManyToOne
    @JoinColumn(name = "id_tablon_centro", nullable = false)
    private TablonCentro tablonCentro;
}
