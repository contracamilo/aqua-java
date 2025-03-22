package com.aqua.system;

import com.aqua.config.Configuration;
import com.aqua.domain.WaterSource;
import com.aqua.observer.WaterLevelMonitor;
import com.aqua.repository.IWaterSourceRepository;
import com.aqua.report.HistoricalReportGenerator;
import com.aqua.user.User;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

/**
 * Clase principal del sistema de gestión de agua.
 * Esta clase coordina todas las operaciones del sistema y mantiene el estado global.
 */
@Getter
public class WaterManagementSystem {
    
    private final IWaterSourceRepository repository;
    private final WaterLevelMonitor monitor;
    private final HistoricalReportGenerator reportGenerator;
    private final Configuration config;
    private final List<User> users;
    private boolean isRunning;
    
    /**
     * Constructor para WaterManagementSystem
     * @param repository El repositorio de fuentes de agua
     * @param monitor El monitor de nivel de agua
     * @param reportGenerator El generador de reportes
     * @param config La configuración del sistema
     */
    public WaterManagementSystem(
            IWaterSourceRepository repository,
            WaterLevelMonitor monitor,
            HistoricalReportGenerator reportGenerator,
            Configuration config) {
        this.repository = repository;
        this.monitor = monitor;
        this.reportGenerator = reportGenerator;
        this.config = config;
        this.users = new ArrayList<>();
        this.isRunning = false;
    }
    
    /**
     * Inicia el sistema
     */
    public void start() {
        if (!isRunning) {
            isRunning = true;
        }
    }
    
    /**
     * Detiene el sistema
     */
    public void stop() {
        if (isRunning) {
            isRunning = false;
        }
    }
    
    /**
     * Agrega una fuente de agua al sistema
     * @param source La fuente de agua a agregar
     */
    public void addWaterSource(WaterSource source) {
        repository.addWaterSource(source);
        monitor.setWaterSource(source);
    }
    
    /**
     * Actualiza una fuente de agua en el sistema
     * @param source La fuente de agua a actualizar
     */
    public void updateWaterSource(WaterSource source) {
        repository.updateWaterSource(source);
        monitor.setWaterSource(source);
    }
    
    /**
     * Elimina una fuente de agua del sistema
     * @param id El ID de la fuente de agua a eliminar
     */
    public void removeWaterSource(int id) {
        WaterSource source = repository.getWaterSource(id);
        if (source != null) {
            repository.removeWaterSource(id);
            if (monitor.getWaterSource() != null && monitor.getWaterSource().getId() == id) {
                monitor.setWaterSource(null);
            }
        }
    }
    
    /**
     * Genera un reporte del sistema
     * @return El reporte generado
     */
    public String generateReport() {
        return reportGenerator.generateReport();
    }
    
    /**
     * Exporta el reporte en el formato especificado
     * @param format El formato de exportación
     */
    public void exportReport(String format) {
        reportGenerator.exportReport(format);
    }

    /**
     * Agrega un nuevo usuario al sistema
     * @param user El usuario a agregar
     */
    public void addUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo");
        }
        users.add(user);
    }

    /**
     * Elimina un usuario del sistema
     * @param userId El ID del usuario a eliminar
     */
    public void removeUser(int userId) {
        users.removeIf(user -> user.getId() == userId);
    }

    /**
     * Obtiene un usuario por su ID
     * @param userId El ID del usuario a obtener
     * @return El usuario si se encuentra, null en caso contrario
     */
    public User getUser(int userId) {
        return users.stream()
                .filter(user -> user.getId() == userId)
                .findFirst()
                .orElse(null);
    }

    /**
     * Obtiene todos los usuarios del sistema
     * @return Lista de todos los usuarios
     */
    public List<User> getUsers() {
        return new ArrayList<>(users);
    }

    /**
     * Obtiene la configuración actual del sistema
     * @return La configuración del sistema
     */
    public Configuration getConfiguration() {
        return config;
    }

    /**
     * Obtiene el repositorio de fuentes de agua
     * @return El repositorio de fuentes de agua
     */
    public IWaterSourceRepository getWaterSourceRepository() {
        return repository;
    }

    /**
     * Obtiene el monitor de nivel de agua
     * @return El monitor de nivel de agua
     */
    public WaterLevelMonitor getWaterLevelMonitor() {
        return monitor;
    }

    /**
     * Obtiene el generador de reportes
     * @return El generador de reportes
     */
    public HistoricalReportGenerator getReportGenerator() {
        return reportGenerator;
    }

    /**
     * Verifica si el sistema está actualmente en ejecución
     * @return true si el sistema está en ejecución, false en caso contrario
     */
    public boolean isRunning() {
        return isRunning;
    }
} 