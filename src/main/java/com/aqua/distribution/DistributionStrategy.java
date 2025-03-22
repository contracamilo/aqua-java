package com.aqua.distribution;

import com.aqua.domain.WaterSource;
import java.util.Map;

/**
 * Interfaz que define el contrato para las estrategias de distribución de agua.
 * Implementa el patrón Strategy para permitir diferentes algoritmos de distribución.
 */
public interface DistributionStrategy {
    
    /**
     * Distribuye el agua según la estrategia implementada
     * @param source La fuente de agua desde la cual distribuir
     * @param allocationData Mapa que contiene los requisitos de asignación
     * @return Mapa que contiene las cantidades reales de distribución
     */
    Map<String, Double> distribute(WaterSource source, Map<String, Double> allocationData);
} 