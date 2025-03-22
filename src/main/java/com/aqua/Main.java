package com.aqua;

import com.aqua.config.Configuration;
import com.aqua.domain.River;
import com.aqua.domain.Well;
import com.aqua.observer.WaterLevelMonitor;
import com.aqua.repository.WaterSourceRepositoryImpl;
import com.aqua.report.HistoricalReportGenerator;
import com.aqua.system.WaterManagementSystem;
import com.aqua.ui.WaterManagementController;
import com.aqua.ui.WaterManagementUI;
import javax.swing.*;

/**
 * Clase principal que inicializa y inicia el sistema de gestión de agua.
 * Esta clase configura todos los componentes necesarios y lanza la interfaz de usuario.
 */
public class Main {
    
    public static void main(String[] args) {
        // Establecer el aspecto visual al predeterminado del sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Crear componentes del sistema
        WaterSourceRepositoryImpl repository = new WaterSourceRepositoryImpl();
        Configuration config = new Configuration();
        WaterLevelMonitor monitor = new WaterLevelMonitor(null); // Se establecerá al agregar fuentes de agua
        HistoricalReportGenerator reportGenerator = new HistoricalReportGenerator(repository);
        
        // Crear e inicializar el sistema
        WaterManagementSystem system = new WaterManagementSystem(
            repository, monitor, reportGenerator, config);
        
        // Crear controlador y UI
        WaterManagementController controller = new WaterManagementController(system);
        WaterManagementUI ui = new WaterManagementUI();
        
        // Agregar datos de ejemplo
        addSampleData(repository);

        // Conectar controlador y UI
        ui.setController(controller);
        controller.setUI(ui);
        
        // Iniciar la UI
        SwingUtilities.invokeLater(() -> {
            ui.setVisible(true);
        });
    }
    
    /**
     * Agrega datos de ejemplo al repositorio
     * @param repository El repositorio al que se agregarán los datos
     */
    private static void addSampleData(WaterSourceRepositoryImpl repository) {
        // Agregar algunas fuentes de agua de ejemplo
        repository.addWaterSource(new River(1, "RIVER", 1000, "Río Norte", "GOOD"));
        repository.addWaterSource(new Well(2, "WELL", 500, "Pozo Sur", "GOOD"));
        repository.addWaterSource(new River(3, "RIVER", 2000, "Río Este", "FAIR"));
    }
} 