package com.aqua.alert;

/**
 * Clase para alertas del sistema
 */
public class SystemAlert extends Alert {
    
    /**
     * Constructor para SystemAlert
     * @param message El mensaje de la alerta
     * @param type El tipo de alerta
     */
    public SystemAlert(String message, AlertType type) {
        super(message, type);
    }
} 