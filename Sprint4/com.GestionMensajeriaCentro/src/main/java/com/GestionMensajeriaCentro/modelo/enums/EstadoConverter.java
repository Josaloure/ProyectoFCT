package com.GestionMensajeriaCentro.modelo.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Class: EstadoConverter
 * Description: Convierte valores del enum Estado a su representación en base de datos y viceversa.
 * Date: 2025-05-01
 * @author Jose Alonso Ureña
 */
@Converter(autoApply = false)
public class EstadoConverter implements AttributeConverter<Estado, String> {

    @Override
    public String convertToDatabaseColumn(Estado estado) {
        switch (estado) {
            case PENDIENTE: return "Pendiente";
            case EN_CURSO: return "En curso";
            case FINALIZADA: return "Finalizada";
            default: throw new IllegalArgumentException("Estado desconocido: " + estado);
        }
    }

    @Override
    public Estado convertToEntityAttribute(String dbValue) {
        switch (dbValue) {
            case "Pendiente": return Estado.PENDIENTE;
            case "En curso": return Estado.EN_CURSO;
            case "Finalizada": return Estado.FINALIZADA;
            default: throw new IllegalArgumentException("Valor no válido de estado: " + dbValue);
        }
    }
}
