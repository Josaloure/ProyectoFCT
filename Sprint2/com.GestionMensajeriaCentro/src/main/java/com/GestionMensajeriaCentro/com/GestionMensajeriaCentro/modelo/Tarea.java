package com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.modelo;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.modelo.enums.Categoria;
import com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.modelo.enums.CategoriaConverter;
import com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.modelo.enums.Estado;
import com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.modelo.enums.EstadoConverter;
import com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.modelo.enums.Prioridad;
import com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.modelo.enums.PrioridadConverter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class: Tarea
 * Description: Representa una tarea asignada entre usuarios dentro de un tablón de profesor, incluyendo su prioridad, estado y categoría.
 * Date: 2025-05-01
 * @author Jose Alonso Ureña
 */
@Entity
@Table(name = "tarea")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tarea")
    private Long idTarea;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDate fechaCreacion;

    @Convert(converter = PrioridadConverter.class)
    @Column(name = "prioridad", nullable = false)
    private Prioridad prioridad;

    @Convert(converter = EstadoConverter.class)
    @Column(name = "estado", nullable = false)
    private Estado estado;

    @Convert(converter = CategoriaConverter.class)
    @Column(name = "categoria", nullable = false)
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "id_emisor", nullable = false)
    private Usuario emisor;

    @ManyToOne
    @JoinColumn(name = "id_receptor", nullable = false)
    private Usuario receptor;

    @ManyToOne
    @JoinColumn(name = "id_tablon_profesor", nullable = false)
    private TablonProfesor tablonProfesor;
}
