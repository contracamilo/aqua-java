package com.aqua.domain;

/**
 * Representa un pozo como tipo de fuente de agua.
 * Esta clase extiende WaterSource e implementa comportamientos específicos para pozos.
 */
public class Well extends WaterSource {
    
    /**
     * Constructor para Well
     * @param id Identificador único para el pozo
     * @param type Tipo de fuente de agua (debe ser "WELL")
     * @param capacity Capacidad en metros cúbicos
     * @param location Ubicación geográfica
     * @param quality Estado actual de la calidad del agua
     */
    public Well(int id, String type, double capacity, String location, String quality) {
        super(id, type, capacity, location, quality);
    }

    @Override
    public void updateQuality(String quality) {
        // Lógica específica de actualización de calidad para pozos
        // Por ejemplo, verificar contaminación del agua subterránea, contenido mineral, etc.
        setQuality(quality);
    }
} 