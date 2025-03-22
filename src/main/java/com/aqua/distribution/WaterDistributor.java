package com.aqua.distribution;

import com.aqua.domain.WaterSource;
import java.util.List;
import java.util.Map;

/**
 * Clase responsable de distribuir el agua entre las diferentes fuentes.
 * Implementa el patrón Strategy para permitir diferentes estrategias de distribución.
 */
public class WaterDistributor {
    
    private DistributionStrategy strategy;

    /**
     * Constructor que inicializa el distribuidor con una estrategia predeterminada
     */
    public WaterDistributor() {
        this.strategy = new EquitableDistributionStrategy();
    }

    /**
     * Establece la estrategia de distribución a utilizar
     * @param strategy La nueva estrategia de distribución
     */
    public void setStrategy(DistributionStrategy strategy) {
        if (strategy == null) {
            throw new IllegalArgumentException("La estrategia no puede ser nula");
        }
        this.strategy = strategy;
    }

    /**
     * Distribuye el agua usando la estrategia actual
     * @param source La fuente de agua desde la cual distribuir
     * @param allocationData Mapa que contiene los requisitos de asignación
     * @return Mapa que contiene las cantidades reales de distribución
     */
    public Map<String, Double> distributeWater(WaterSource source, Map<String, Double> allocationData) {
        if (source == null) {
            throw new IllegalArgumentException("La fuente de agua no puede ser nula");
        }
        if (allocationData == null) {
            throw new IllegalArgumentException("Los datos de asignación no pueden ser nulos");
        }
        return strategy.distribute(source, allocationData);
    }

    /**
     * Calcula la cantidad total de agua disponible para distribuir
     * @param sources Lista de fuentes de agua
     * @return La cantidad total de agua disponible
     */
    public double calculateAvailableWater(List<WaterSource> sources) {
        return sources.stream()
                .mapToDouble(WaterSource::getCurrentLevel)
                .sum();
    }
} 