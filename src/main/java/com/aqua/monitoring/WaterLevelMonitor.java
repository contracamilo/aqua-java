package com.aqua.monitoring;

import com.aqua.config.Configuration;
import com.aqua.domain.WaterSource;
import com.aqua.observer.IObserver;
import com.aqua.alert.Alert;
import com.aqua.alert.CriticalLevelAlert;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase responsable de monitorear los niveles de agua en las fuentes.
 * Implementa el patrón Observer para notificar cambios en los niveles.
 */
public class WaterLevelMonitor {
    
    private final List<IObserver> observers;
    private final Configuration config;
    
    /**
     * Constructor que inicializa el monitor con la configuración proporcionada
     * @param config La configuración del sistema
     */
    public WaterLevelMonitor(Configuration config) {
        this.observers = new ArrayList<>();
        this.config = config;
    }
    
    /**
     * Agrega un nuevo observador al monitor
     * @param observer El observador a agregar
     */
    public void addObserver(IObserver observer) {
        if (observer == null) {
            throw new IllegalArgumentException("El observador no puede ser nulo");
        }
        observers.add(observer);
    }
    
    /**
     * Elimina un observador del monitor
     * @param observer El observador a eliminar
     */
    public void removeObserver(IObserver observer) {
        observers.remove(observer);
    }
    
    /**
     * Verifica el nivel de agua de una fuente y notifica si es crítico
     * @param source La fuente de agua a verificar
     */
    public void checkWaterLevel(WaterSource source) {
        if (source == null) {
            throw new IllegalArgumentException("La fuente de agua no puede ser nula");
        }
        
        double levelPercentage = source.getCurrentLevel() / source.getCapacity();
        
        if (levelPercentage <= config.getCriticalWaterLevelThreshold()) {
            String message = String.format("Nivel crítico de agua en %s ID: %d (%.1f%%)", 
                source.getType(), source.getId(), levelPercentage * 100);
            notifyObservers(new CriticalLevelAlert(message, levelPercentage, config.getCriticalWaterLevelThreshold()));
        }
    }
    
    /**
     * Notifica a todos los observadores sobre un cambio
     * @param alert La alerta a notificar
     */
    private void notifyObservers(Alert alert) {
        for (IObserver observer : observers) {
            observer.update(alert);
        }
    }
} 