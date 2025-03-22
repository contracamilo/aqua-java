package com.aqua.alert;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * Clase base para las alertas del sistema
 */
@Getter
@Setter
public abstract class Alert {
    
    public enum AlertType {
        INFO,
        WARNING,
        ERROR
    }
    
    private String message;
    private AlertType type;
    private LocalDateTime timestamp;
    
    /**
     * Constructor para Alert
     * @param message El mensaje de la alerta
     * @param type El tipo de alerta
     */
    public Alert(String message, AlertType type) {
        this.message = message;
        this.type = type;
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Gets the alert message
     * @return The alert message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gets the timestamp of the alert
     * @return The timestamp
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - %s", type, message, timestamp);
    }
} 