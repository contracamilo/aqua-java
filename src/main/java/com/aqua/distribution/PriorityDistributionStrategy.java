package com.aqua.distribution;

import com.aqua.domain.WaterSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementación de la estrategia de distribución por prioridad.
 * Esta estrategia distribuye el agua según las prioridades asignadas a cada destinatario.
 */
public class PriorityDistributionStrategy implements DistributionStrategy {
    
    /**
     * Distribuye el agua según las prioridades de cada destinatario
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
        
        // Ordenar los destinatarios por prioridad (mayor a menor)
        List<Map.Entry<String, Double>> sortedEntries = new ArrayList<>(allocationData.entrySet());
        sortedEntries.sort((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()));
        
        // Distribuir el agua según las prioridades
        double remainingWater = availableWater;
        for (Map.Entry<String, Double> entry : sortedEntries) {
            double priority = entry.getValue();
            double amount = Math.min(remainingWater, priority);
            result.put(entry.getKey(), amount);
            remainingWater -= amount;
            
            if (remainingWater <= 0) {
                break;
            }
        }
        
        return result;
    }
} 