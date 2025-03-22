package com.aqua.domain;

/**
 * Representa un río como tipo de fuente de agua.
 * Esta clase extiende WaterSource e implementa comportamientos específicos para ríos.
 */
public class River extends WaterSource {
    
    /**
     * Constructor para River
     * @param id Identificador único para el río
     * @param type Tipo de fuente de agua (debe ser "RIVER")
     * @param capacity Capacidad en metros cúbicos
     * @param location Ubicación geográfica
     * @param quality Estado actual de la calidad del agua
     */
    public River(int id, String type, double capacity, String location, String quality) {
        super(id, type, capacity, location, quality);
    }

    @Override
    public void updateQuality(String quality) {
        // Lógica específica de actualización de calidad para ríos
        // Por ejemplo, verificar cambios estacionales, niveles de contaminación, etc.
        setQuality(quality);
    }
} 