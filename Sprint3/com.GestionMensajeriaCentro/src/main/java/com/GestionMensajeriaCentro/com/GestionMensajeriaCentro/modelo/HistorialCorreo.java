package com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.modelo;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.modelo.enums.TipoNotificacion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class: HistorialCorreo
 * Description: Registra el historial de correos enviados, incluyendo información del destinatario, tipo de notificación y entidades relacionadas.
 * Date: 2025-05-01
 * @author Jose Alonso Ureña
 */
@Entity
@Table(name = "historial_correo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistorialCorreo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historial")
    private Long idHistorial;

    @Column(name = "fecha_envio", nullable = false)
    private LocalDateTime fechaEnvio;

    @Column(name = "destinatario_email", nullable = false)
    private String destinatarioEmail;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_notificacion", nullable = false)
    private TipoNotificacion tipoNotificacion;

    @ManyToOne
    @JoinColumn(name = "id_tarea")
    private Tarea tarea;

    @ManyToOne
    @JoinColumn(name = "id_mensaje")
    private Mensaje mensaje;
}
