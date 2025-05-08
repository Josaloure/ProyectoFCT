package com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.modelo.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Class: PrioridadConverter
 * Description: Convierte valores del enum Prioridad a su representación en base de datos y viceversa.
 * Date: 2025-05-01
 * @author Jose Alonso Ureña
 */
@Converter(autoApply = false)
public class PrioridadConverter implements AttributeConverter<Prioridad, String> {

    @Override
    public String convertToDatabaseColumn(Prioridad prioridad) {
        switch (prioridad) {
            case ALTA: return "Alta";
            case MEDIA: return "Media";
            case BAJA: return "Baja";
            default: throw new IllegalArgumentException("Prioridad desconocida: " + prioridad);
        }
    }

    @Override
    public Prioridad convertToEntityAttribute(String dbValue) {
        switch (dbValue) {
            case "Alta": return Prioridad.ALTA;
            case "Media": return Prioridad.MEDIA;
            case "Baja": return Prioridad.BAJA;
            default: throw new IllegalArgumentException("Valor no válido de prioridad: " + dbValue);
        }
    }
}
