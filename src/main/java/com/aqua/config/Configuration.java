package com.aqua.config;

import lombok.Getter;
import lombok.Setter;

/**
 * Gestiona los parámetros de configuración del sistema.
 * Esta clase mantiene varios umbrales y configuraciones utilizados en todo el sistema.
 */
@Getter
@Setter
public class Configuration {
    
    private double criticalWaterLevelThreshold;
    private double contaminationThreshold;
    
    /**
     * Constructor por defecto con valores predeterminados
     */
    public Configuration() {
        this.criticalWaterLevelThreshold = 0.2; // 20% de la capacidad
        this.contaminationThreshold = 0.8; // 80% de nivel de contaminación
    }
    
    /**
     * Constructor con valores personalizados
     * @param criticalWaterLevelThreshold El umbral para niveles críticos de agua
     * @param contaminationThreshold El umbral para niveles de contaminación
     */
    public Configuration(double criticalWaterLevelThreshold, double contaminationThreshold) {
        if (criticalWaterLevelThreshold < 0 || criticalWaterLevelThreshold > 1) {
            throw new IllegalArgumentException("El umbral de nivel crítico de agua debe estar entre 0 y 1");
        }
        if (contaminationThreshold < 0 || contaminationThreshold > 1) {
            throw new IllegalArgumentException("El umbral de contaminación debe estar entre 0 y 1");
        }
        
        this.criticalWaterLevelThreshold = criticalWaterLevelThreshold;
        this.contaminationThreshold = contaminationThreshold;
    }
    
    /**
     * Establece el umbral de nivel crítico de agua
     * @param threshold El nuevo valor del umbral (entre 0 y 1)
     */
    public void setCriticalWaterLevelThreshold(double threshold) {
        if (threshold < 0 || threshold > 1) {
            throw new IllegalArgumentException("El umbral de nivel crítico de agua debe estar entre 0 y 1");
        }
        this.criticalWaterLevelThreshold = threshold;
    }
    
    /**
     * Establece el umbral de contaminación
     * @param threshold El nuevo valor del umbral (entre 0 y 1)
     */
    public void setContaminationThreshold(double threshold) {
        if (threshold < 0 || threshold > 1) {
            throw new IllegalArgumentException("El umbral de contaminación debe estar entre 0 y 1");
        }
        this.contaminationThreshold = threshold;
    }
} 