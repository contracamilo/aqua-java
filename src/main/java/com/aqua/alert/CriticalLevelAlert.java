package com.aqua.alert;

import com.aqua.domain.WaterSource;
import com.aqua.observer.IObserver;
import java.util.Date;

/**
 * Alerta para niveles críticos de agua
 */
public class CriticalLevelAlert extends Alert implements IObserver {
    
    private final double level;
    private final double threshold;
    
    /**
     * Constructor para CriticalLevelAlert
     * @param message El mensaje de la alerta
     * @param level El nivel actual de agua
     * @param threshold El umbral crítico
     */
    public CriticalLevelAlert(String message, double level, double threshold) {
        super(message, AlertType.WARNING);
        this.level = level;
        this.threshold = threshold;
    }
    
    /**
     * Obtiene el nivel de agua
     * @return El nivel de agua
     */
    public double getLevel() {
        return level;
    }
    
    /**
     * Obtiene el umbral crítico
     * @return El umbral crítico
     */
    public double getThreshold() {
        return threshold;
    }

    @Override
    public void update(WaterSource source, double currentLevel) {
        double threshold = source.getCapacity() * 0.2; // 20% of capacity
        if (currentLevel < threshold) {
            // In a real implementation, this would trigger notifications
            // such as sending emails, SMS, or updating a dashboard
            System.out.println("CRITICAL ALERT: Water level in " + source.getType() + 
                             " at " + source.getLocation() + " is critically low!");
        }
    }
    
    @Override
    public void update(Alert alert) {
        // No action needed for this type of alert
    }
} 