package com.aqua.distribution;

import com.aqua.domain.WaterSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementación de la estrategia de distribución justa.
 * Esta estrategia distribuye el agua considerando tanto las necesidades como la capacidad de cada destinatario.
 */
public class FairDistributionStrategy implements DistributionStrategy {
    
    /**
     * Distribuye el agua de manera justa considerando necesidades y capacidades
     * @param source La fuente de agua desde la cual distribuir
     * @param allocationData Mapa que contiene los requisitos de asignación
     * @return Mapa que contiene las cantidades reales de distribución
     */
    @Override
    public Map<String, Double> distribute(WaterSource source, Map<String, Double> allocationData) {
        if (source == null || allocationData == null || allocationData.isEmpty()) {
            throw new IllegalArgumentException("La fuente de agua y los datos de asignación no pueden ser nulos o vacíos");
        }
        
        double availableWater = source.getCurrentLevel();
        Map<String, Double> result = new HashMap<>();
        
        // Calcular el total de necesidades
        double totalNeeds = allocationData.values().stream()
                .mapToDouble(Double::doubleValue)
                .sum();
        
        // Si no hay agua disponible, retornar distribución vacía
        if (availableWater <= 0) {
            return result;
        }
        
        // Distribuir el agua proporcionalmente a las necesidades
        for (Map.Entry<String, Double> entry : allocationData.entrySet()) {
            double need = entry.getValue();
            double proportion = need / totalNeeds;
            double amount = availableWater * proportion;
            
            // Asegurar que cada destinatario reciba al menos un mínimo
            amount = Math.max(amount, availableWater * 0.1); // Mínimo 10% del agua disponible
            
            result.put(entry.getKey(), amount);
        }
        
        return result;
    }
} 