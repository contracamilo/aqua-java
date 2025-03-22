package com.aqua.distribution;

import com.aqua.domain.WaterSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementación de la estrategia de distribución equitativa.
 * Esta estrategia distribuye el agua de manera igual entre todos los destinatarios.
 */
public class EquitableDistributionStrategy implements DistributionStrategy {
    
    /**
     * Distribuye el agua de manera equitativa entre todos los destinatarios
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
        int recipients = allocationData.size();
        double amountPerRecipient = availableWater / recipients;
        
        Map<String, Double> result = new HashMap<>();
        for (Map.Entry<String, Double> entry : allocationData.entrySet()) {
            result.put(entry.getKey(), amountPerRecipient);
        }
        
        return result;
    }
} 