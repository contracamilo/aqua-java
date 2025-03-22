package com.aqua.ui;

import com.aqua.alert.Alert;
import com.aqua.domain.WaterSource;
import com.aqua.repository.IWaterSourceRepository;
import com.aqua.system.WaterManagementSystem;

/**
 * Controlador para la interfaz de usuario del sistema de gestión de agua.
 * Esta clase actúa como intermediario entre la UI y el sistema principal.
 */
public class WaterManagementController {
    
    private final WaterManagementSystem system;
    private WaterManagementUI ui;
    
    /**
     * Constructor para WaterManagementController
     * @param system El sistema de gestión de agua
     */
    public WaterManagementController(WaterManagementSystem system) {
        this.system = system;
    }
    
    /**
     * Establece la UI para este controlador
     * @param ui La UI a establecer
     */
    public void setUI(WaterManagementUI ui) {
        this.ui = ui;
        if (ui != null && system != null && system.getWaterSourceRepository() != null) {
            updateDisplay();
        }
    }
    
    /**
     * Inicia el sistema
     */
    public void startSystem() {
        system.start();
        updateDisplay();
    }
    
    /**
     * Detiene el sistema
     */
    public void stopSystem() {
        system.stop();
        updateDisplay();
    }
    
    /**
     * Agrega una fuente de agua al sistema
     * @param source La fuente de agua a agregar
     */
    public void addWaterSource(WaterSource source) {
        system.addWaterSource(source);
        updateDisplay();
    }
    
    /**
     * Actualiza una fuente de agua en el sistema
     * @param source La fuente de agua a actualizar
     */
    public void updateWaterSource(WaterSource source) {
        system.updateWaterSource(source);
        updateDisplay();
    }
    
    /**
     * Elimina una fuente de agua del sistema
     * @param id El ID de la fuente de agua a eliminar
     */
    public void removeWaterSource(int id) {
        system.removeWaterSource(id);
        updateDisplay();
    }
    
    /**
     * Genera un reporte del sistema
     */
    public void generateReport() {
        String report = system.generateReport();
        ui.showReport(report);
    }
    
    /**
     * Exporta el reporte en el formato especificado
     * @param format El formato de exportación
     */
    public void exportReport(String format) {
        system.exportReport(format);
    }
    
    /**
     * Maneja una alerta del sistema
     * @param alert La alerta a manejar
     */
    public void handleAlert(Alert alert) {
        ui.displayAlert(alert);
    }
    
    /**
     * Obtiene el repositorio de fuentes de agua
     * @return El repositorio de fuentes de agua
     */
    public IWaterSourceRepository getWaterSourceRepository() {
        return system.getWaterSourceRepository();
    }
    
    /**
     * Actualiza la visualización de la UI
     */
    private void updateDisplay() {
        if (ui != null) {
            ui.updateDisplay();
        }
    }
} 