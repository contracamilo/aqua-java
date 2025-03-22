package com.aqua.repository;

import com.aqua.domain.WaterSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementación del repositorio de fuentes de agua.
 * Esta clase maneja el almacenamiento y recuperación de fuentes de agua en memoria.
 */
public class WaterSourceRepositoryImpl implements IWaterSourceRepository {
    
    private final Map<Integer, WaterSource> waterSources;

    /**
     * Constructor que inicializa el mapa de fuentes de agua
     */
    public WaterSourceRepositoryImpl() {
        this.waterSources = new ConcurrentHashMap<>();
    }

    /**
     * Guarda una nueva fuente de agua en el repositorio
     * @param source La fuente de agua a guardar
     * @return La fuente de agua guardada
     */
    @Override
    public void addWaterSource(WaterSource source) {
        if (source == null) {
            throw new IllegalArgumentException("Water source cannot be null");
        }
        waterSources.put(source.getId(), source);
    }

    /**
     * Actualiza una fuente de agua existente
     * @param source La fuente de agua actualizada
     * @return La fuente de agua actualizada
     */
    @Override
    public void updateWaterSource(WaterSource source) {
        if (source == null) {
            throw new IllegalArgumentException("Water source cannot be null");
        }
        if (!waterSources.containsKey(source.getId())) {
            throw new IllegalStateException("Water source with ID " + source.getId() + " does not exist");
        }
        waterSources.put(source.getId(), source);
    }

    /**
     * Elimina una fuente de agua por su ID
     * @param id El ID de la fuente de agua a eliminar
     */
    @Override
    public void removeWaterSource(int id) {
        waterSources.remove(id);
    }

    /**
     * Busca una fuente de agua por su ID
     * @param id El ID de la fuente de agua
     * @return La fuente de agua encontrada, o null si no existe
     */
    @Override
    public WaterSource getWaterSource(int id) {
        return waterSources.get(id);
    }

    /**
     * Obtiene todas las fuentes de agua almacenadas
     * @return Lista de todas las fuentes de agua
     */
    @Override
    public List<WaterSource> listWaterSources() {
        return new ArrayList<>(waterSources.values());
    }
} 