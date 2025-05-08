package com.GestionMensajeriaCentro.com.GestionMensajeriaCentro.modelo.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Class: CategoriaConverter
 * Description: Convierte valores del enum Categoria a su representación en base de datos y viceversa.
 * Date: 2025-05-01
 * @author Jose Alonso Ureña
 */
@Converter(autoApply = false)
public class CategoriaConverter implements AttributeConverter<Categoria, String> {

    @Override
    public String convertToDatabaseColumn(Categoria categoria) {
        switch (categoria) {
            case TUTORÍAS: return "Tutorías";
            case REUNIONES: return "Reuniones";
            case EXÁMENES: return "Exámenes";
            case EVALUACIONES: return "Evaluaciones";
            case EXPULSIONES: return "Expulsiones";
            default: throw new IllegalArgumentException("Categoría desconocida: " + categoria);
        }
    }

    @Override
    public Categoria convertToEntityAttribute(String dbValue) {
        switch (dbValue) {
            case "Tutorías": return Categoria.TUTORÍAS;
            case "Reuniones": return Categoria.REUNIONES;
            case "Exámenes": return Categoria.EXÁMENES;
            case "Evaluaciones": return Categoria.EVALUACIONES;
            case "Expulsiones": return Categoria.EXPULSIONES;
            default: throw new IllegalArgumentException("Valor no válido de categoría: " + dbValue);
        }
    }
}
