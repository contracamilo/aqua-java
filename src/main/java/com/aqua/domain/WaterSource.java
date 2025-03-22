package com.aqua.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * Clase base que representa una fuente de agua en el sistema.
 * Esta clase contiene atributos y comportamientos comunes para todos los tipos de fuentes de agua.
 */
@Getter
@Setter
public abstract class WaterSource {
    private final int id;
    private final String type;
    private double capacity;
    private String location;
    private String quality;
    private double currentLevel;

    /**
     * Constructor para WaterSource
     * @param id Identificador único para la fuente de agua
     * @param type Tipo de fuente de agua
     * @param capacity Capacidad en metros cúbicos
     * @param location Ubicación geográfica
     * @param quality Estado actual de la calidad del agua
     */
    protected WaterSource(int id, String type, double capacity, String location, String quality) {
        this.id = id;
        this.type = type;
        this.capacity = capacity;
        this.location = location;
        this.quality = quality;
        this.currentLevel = capacity * 0.5; // Inicialmente al 50% de la capacidad
    }

    /**
     * Actualiza el estado de calidad de la fuente de agua
     * @param quality Nuevo estado de calidad
     */
    public abstract void updateQuality(String quality);

    public double getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(double currentLevel) {
        this.currentLevel = Math.max(0, Math.min(currentLevel, capacity));
    }
} 