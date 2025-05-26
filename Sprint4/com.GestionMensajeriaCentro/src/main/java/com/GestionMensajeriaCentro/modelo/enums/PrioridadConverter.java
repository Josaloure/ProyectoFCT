package com.GestionMensajeriaCentro.modelo.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Class: PrioridadConverter
 * Description: Convierte valores del enum Prioridad a su representación en base de datos y viceversa.
 * Date: 2025-05-01
 * @author Jose Alonso Ureña
 */
@Converter
public class PrioridadConverter implements AttributeConverter<Prioridad, String> {

    @Override
    public String convertToDatabaseColumn(Prioridad prioridad) {
        return switch (prioridad) {
            case ALTA -> "Alta";
            case MEDIA -> "Media";
            case BAJA -> "Baja";
        };
    }

    @Override
    public Prioridad convertToEntityAttribute(String dbValue) {
        return switch (dbValue) {
            case "Alta" -> Prioridad.ALTA;
            case "Media" -> Prioridad.MEDIA;
            case "Baja" -> Prioridad.BAJA;
            default -> throw new IllegalArgumentException("Valor no válido de prioridad: " + dbValue);
        };
    }
}
