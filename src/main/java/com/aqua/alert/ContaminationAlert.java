package com.aqua.alert;

import com.aqua.domain.WaterSource;
import com.aqua.observer.IObserver;
import java.util.Date;

/**
 * Alerta para contaminación del agua
 */
public class ContaminationAlert extends Alert implements IObserver {
    
    private final String quality;
    private final String previousQuality;
    
    /**
     * Constructor para ContaminationAlert
     * @param message El mensaje de la alerta
     * @param quality La calidad actual del agua
     * @param previousQuality La calidad anterior del agua
     */
    public ContaminationAlert(String message, String quality, String previousQuality) {
        super(message, AlertType.WARNING);
        this.quality = quality;
        this.previousQuality = previousQuality;
    }
    
    /**
     * Obtiene la calidad actual del agua
     * @return La calidad actual del agua
     */
    public String getQuality() {
        return quality;
    }
    
    /**
     * Obtiene la calidad anterior del agua
     * @return La calidad anterior del agua
     */
    public String getPreviousQuality() {
        return previousQuality;
    }

    @Override
    public void update(WaterSource source, double currentLevel) {
        if (quality.equals(source.getQuality())) {
            // In a real implementation, this would trigger notifications
            // such as sending emails, SMS, or updating a dashboard
            System.out.println("CONTAMINATION ALERT: Water in " + source.getType() + 
                             " at " + source.getLocation() + " is contaminated!");
        }
    }
    
    @Override
    public void update(Alert alert) {
        // No action needed for this type of alert
    }
} 