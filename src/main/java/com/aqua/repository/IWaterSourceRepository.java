package com.aqua.repository;

import com.aqua.domain.WaterSource;
import java.util.List;

/**
 * Interface defining the contract for water source repository operations.
 * This interface follows the Repository pattern for data access.
 */
public interface IWaterSourceRepository {
    
    /**
     * Adds a new water source to the repository
     * @param source The water source to add
     */
    void addWaterSource(WaterSource source);
    
    /**
     * Updates an existing water source in the repository
     * @param source The water source to update
     */
    void updateWaterSource(WaterSource source);
    
    /**
     * Removes a water source from the repository
     * @param id The ID of the water source to remove
     */
    void removeWaterSource(int id);
    
    /**
     * Retrieves a water source by its ID
     * @param id The ID of the water source to retrieve
     * @return The water source if found, null otherwise
     */
    WaterSource getWaterSource(int id);
    
    /**
     * Lists all water sources in the repository
     * @return A list of all water sources
     */
    List<WaterSource> listWaterSources();
} 