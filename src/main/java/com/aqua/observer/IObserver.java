package com.aqua.observer;

import com.aqua.domain.WaterSource;
import com.aqua.alert.Alert;

/**
 * Interfaz para el patrón Observer.
 * Las implementaciones de esta interfaz serán notificadas de cambios en los niveles de agua y alertas.
 */
public interface IObserver {
    
    /**
     * Llamado cuando el nivel de agua de una fuente cambia
     * @param source La fuente de agua que cambió
     * @param currentLevel El nuevo nivel actual
     */
    void update(WaterSource source, double currentLevel);
    
    /**
     * Llamado cuando se genera una alerta
     * @param alert La alerta que fue generada
     */
    void update(Alert alert);
} 