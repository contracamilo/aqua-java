package com.aqua.observer;

import com.aqua.domain.WaterSource;
import com.aqua.alert.Alert;
import com.aqua.alert.SystemAlert;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

/**
 * Monitors water levels and notifies observers of changes.
 * This class implements the Subject part of the Observer pattern.
 */
public class WaterLevelMonitor implements ISubject {
    
    private WaterSource waterSource;
    private List<IObserver> observers;
    private Random random;

    /**
     * Constructor for WaterLevelMonitor
     * @param waterSource The water source to monitor (can be null initially)
     */
    public WaterLevelMonitor(WaterSource waterSource) {
        this.waterSource = waterSource;
        this.observers = new ArrayList<>();
        this.random = new Random();
    }

    /**
     * Sets the water source to monitor
     * @param waterSource The water source to monitor
     */
    public void setWaterSource(WaterSource waterSource) {
        this.waterSource = waterSource;
    }

    /**
     * Gets the water source being monitored
     * @return The water source
     */
    public WaterSource getWaterSource() {
        return waterSource;
    }

    /**
     * Simulates a change in water level
     * This method would typically be called by sensors or other monitoring systems
     */
    public void simulateLevel() {
        if (waterSource == null) return;
        
        double change = (random.nextDouble() - 0.5) * 20; // Cambio aleatorio entre -10 y +10
        double newLevel = Math.max(0, Math.min(waterSource.getCurrentLevel() + change, waterSource.getCapacity()));
        
        // Verificar si el nivel es crítico antes de actualizar
        double levelPercentage = (newLevel / waterSource.getCapacity()) * 100;
        if (levelPercentage < 20) {
            SystemAlert alert = new SystemAlert(
                String.format("Nivel crítico de agua en %s ID: %d (%.1f%%)", 
                    waterSource.getType(), waterSource.getId(), levelPercentage),
                Alert.AlertType.WARNING
            );
            for (IObserver observer : observers) {
                observer.update(alert);
            }
        }
        
        waterSource.setCurrentLevel(newLevel);
        notifyObservers();
    }

    @Override
    public void registerObserver(IObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(IObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        if (waterSource != null) {
            for (IObserver observer : observers) {
                observer.update(waterSource, waterSource.getCurrentLevel());
            }
        }
    }

    /**
     * Gets the current water level
     * @return The current water level
     */
    public double getCurrentLevel() {
        return waterSource.getCurrentLevel();
    }
} 