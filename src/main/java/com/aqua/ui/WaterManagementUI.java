package com.aqua.ui;

import com.aqua.alert.Alert;
import com.aqua.alert.SystemAlert;
import com.aqua.domain.WaterSource;
import com.aqua.domain.River;
import com.aqua.domain.Well;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.time.format.DateTimeFormatter;

/**
 * Clase de interfaz de usuario para el sistema de gestión de agua.
 * Esta clase implementa una GUI basada en Swing siguiendo el patrón MVC como componente de vista.
 */
public class WaterManagementUI extends JFrame {
    
    private WaterManagementController controller;
    private Box waterSourceBox;
    private Box alertBox;
    private JTextArea reportArea;
    private JTabbedPane tabbedPane;
    private Timer simulationTimer;
    private Random random = new Random();

    /**
     * Constructor para WaterManagementUI
     */
    public WaterManagementUI() {
        setTitle("Sistema de Gestión de Agua");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        initializeComponents();
        layoutComponents();
    }

    /**
     * Establece el controlador para esta UI
     * @param controller El controlador a establecer
     */
    public void setController(WaterManagementController controller) {
        this.controller = controller;
    }

    /**
     * Inicializa todos los componentes de la UI
     */
    private void initializeComponents() {
        // Crear menú principal
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Archivo");
        JMenu waterMenu = new JMenu("Agua");
        JMenu reportMenu = new JMenu("Reportes");
        
        // Elementos del menú Archivo
        JMenuItem startItem = new JMenuItem("Iniciar Sistema");
        JMenuItem stopItem = new JMenuItem("Detener Sistema");
        JMenuItem exitItem = new JMenuItem("Salir");
        
        // Elementos del menú Agua
        JMenuItem addSourceItem = new JMenuItem("Agregar Fuente");
        JMenuItem updateSourceItem = new JMenuItem("Actualizar Fuente");
        JMenuItem removeSourceItem = new JMenuItem("Eliminar Fuente");
        
        // Elementos del menú Reportes
        JMenuItem generateReportItem = new JMenuItem("Generar Reporte");
        JMenuItem exportExcelItem = new JMenuItem("Exportar a Excel");
        
        // Agregar elementos a los menús
        fileMenu.add(startItem);
        fileMenu.add(stopItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        
        waterMenu.add(addSourceItem);
        waterMenu.add(updateSourceItem);
        waterMenu.add(removeSourceItem);
        
        reportMenu.add(generateReportItem);
        reportMenu.add(exportExcelItem);
        
        // Agregar menús a la barra de menú
        menuBar.add(fileMenu);
        menuBar.add(waterMenu);
        menuBar.add(reportMenu);
        
        // Crear panel con pestañas
        tabbedPane = new JTabbedPane();
        
        // Crear paneles para cada pestaña
        JPanel waterSourcePanel = createWaterSourcePanel();
        JPanel alertPanel = createAlertPanel();
        JPanel reportPanel = createReportPanel();
        
        // Agregar paneles al panel de pestañas
        tabbedPane.addTab("Fuentes de Agua", waterSourcePanel);
        tabbedPane.addTab("Alertas", alertPanel);
        tabbedPane.addTab("Reportes", reportPanel);
        
        // Agregar barra de menú y panel de pestañas al frame
        setJMenuBar(menuBar);
        add(tabbedPane);
        
        // Agregar listeners de eventos
        startItem.addActionListener(e -> {
            controller.startSystem();
            startSimulation();
        });
        
        stopItem.addActionListener(e -> {
            controller.stopSystem();
            stopSimulation();
        });
        
        exitItem.addActionListener(e -> System.exit(0));
        
        addSourceItem.addActionListener(e -> showAddWaterSourceDialog());
        updateSourceItem.addActionListener(e -> showUpdateWaterSourceDialog());
        removeSourceItem.addActionListener(e -> showRemoveWaterSourceDialog());
        
        generateReportItem.addActionListener(e -> controller.generateReport());
        exportExcelItem.addActionListener(e -> controller.exportReport("EXCEL"));
    }

    /**
     * Crea el panel de fuentes de agua
     * @return El panel de fuentes de agua
     */
    private JPanel createWaterSourcePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Panel superior con título
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        topPanel.add(new JLabel("Fuentes de Agua"), BorderLayout.WEST);
        
        // Panel de fuentes con BoxLayout vertical
        waterSourceBox = Box.createVerticalBox();
        waterSourceBox.setBackground(new Color(250, 250, 250));
        
        // Panel de scroll personalizado
        JScrollPane scrollPane = new JScrollPane(waterSourceBox);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }

    /**
     * Crea el panel de alertas
     * @return El panel de alertas
     */
    private JPanel createAlertPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Panel superior con título y botón de limpiar
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        topPanel.add(new JLabel("Historial de Alertas"), BorderLayout.WEST);
        
        JButton clearButton = new JButton("Limpiar Alertas");
        clearButton.addActionListener(e -> {
            alertBox.removeAll();
            alertBox.revalidate();
            alertBox.repaint();
        });
        topPanel.add(clearButton, BorderLayout.EAST);
        
        // Panel de alertas con BoxLayout vertical
        alertBox = Box.createVerticalBox();
        alertBox.setBackground(new Color(250, 250, 250));
        
        // Panel de scroll personalizado
        JScrollPane scrollPane = new JScrollPane(alertBox);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Crea el panel de reportes
     * @return El panel de reportes
     */
    private JPanel createReportPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Panel superior con título y botón de exportar
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        topPanel.add(new JLabel("Reportes"), BorderLayout.WEST);
        
        JButton exportButton = new JButton("Exportar a Excel");
        exportButton.addActionListener(e -> controller.exportReport("EXCEL"));
        topPanel.add(exportButton, BorderLayout.EAST);
        
        // Panel de reportes
        reportArea = new JTextArea();
        reportArea.setEditable(false);
        reportArea.setLineWrap(true);
        reportArea.setWrapStyleWord(true);
        reportArea.setBackground(new Color(250, 250, 250));
        reportArea.setFont(new Font("Arial", Font.PLAIN, 12));
        
        // Panel de scroll personalizado
        JScrollPane scrollPane = new JScrollPane(reportArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Organiza los componentes en el frame
     */
    private void layoutComponents() {
        // El layout ya está manejado por BorderLayout y JTabbedPane
    }

    /**
     * Muestra un diálogo para agregar una nueva fuente de agua
     */
    private void showAddWaterSourceDialog() {
        JDialog dialog = new JDialog(this, "Agregar Fuente de Agua", true);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Crear campos de entrada con ancho fijo
        JTextField idField = new JTextField(10);
        String[] types = {"RIVER", "WELL"};
        JComboBox<String> typeCombo = new JComboBox<>(types);
        JTextField capacityField = new JTextField(10);
        JTextField locationField = new JTextField(10);
        String[] qualities = {"GOOD", "FAIR", "POOR"};
        JComboBox<String> qualityCombo = new JComboBox<>(qualities);
        
        // Panel para capacidad con unidad
        JPanel capacityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        capacityPanel.add(capacityField);
        capacityPanel.add(new JLabel("m³"));
        
        // Agregar componentes con GridBagLayout
        gbc.gridx = 0; gbc.gridy = 0;
        dialog.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        dialog.add(idField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        dialog.add(new JLabel("Tipo:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        dialog.add(typeCombo, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        dialog.add(new JLabel("Capacidad:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        dialog.add(capacityPanel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        dialog.add(new JLabel("Ubicación:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        dialog.add(locationField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4;
        dialog.add(new JLabel("Calidad:"), gbc);
        gbc.gridx = 1; gbc.gridy = 4;
        dialog.add(qualityCombo, gbc);
        
        // Panel para botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton okButton = new JButton("Aceptar");
        JButton cancelButton = new JButton("Cancelar");
        
        okButton.addActionListener(e -> {
            try {
                // Validar ID
                String idText = idField.getText().trim();
                if (idText.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "El ID no puede estar vacío.");
                    return;
                }
                int id = Integer.parseInt(idText);
                
                // Validar que el ID no exista
                if (controller.getWaterSourceRepository().getWaterSource(id) != null) {
                    JOptionPane.showMessageDialog(dialog, "Ya existe una fuente de agua con ese ID.");
                    return;
                }
                
                // Validar capacidad
                String capacityText = capacityField.getText().trim();
                if (capacityText.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "La capacidad no puede estar vacía.");
                    return;
                }
                double capacity = Double.parseDouble(capacityText);
                if (capacity <= 0) {
                    JOptionPane.showMessageDialog(dialog, "La capacidad debe ser mayor que 0.");
                    return;
                }
                
                // Validar ubicación
                String location = locationField.getText().trim();
                if (location.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "La ubicación no puede estar vacía.");
                    return;
                }
                
                // Obtener tipo y calidad
                String type = (String) typeCombo.getSelectedItem();
                String quality = (String) qualityCombo.getSelectedItem();
                
                // Crear la fuente de agua
                WaterSource source;
                if ("RIVER".equals(type)) {
                    source = new River(id, type, capacity, location, quality);
                } else {
                    source = new Well(id, type, capacity, location, quality);
                }
                
                controller.addWaterSource(source);
                dialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Por favor ingrese números válidos para ID y capacidad.");
            }
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        dialog.add(buttonPanel, gbc);
        
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    /**
     * Muestra un diálogo para actualizar una fuente de agua
     */
    private void showUpdateWaterSourceDialog() {
        // Primero, obtener el ID de la fuente a actualizar
        String idStr = JOptionPane.showInputDialog(this, "Ingrese el ID de la fuente de agua a actualizar:");
        if (idStr == null) return;
        
        try {
            int id = Integer.parseInt(idStr.trim());
            WaterSource source = controller.getWaterSourceRepository().getWaterSource(id);
            if (source == null) {
                JOptionPane.showMessageDialog(this, "No se encontró una fuente de agua con ese ID.");
                return;
            }
            
            // Guardar valores anteriores para comparación
            String previousQuality = source.getQuality();
            double previousLevel = source.getCurrentLevel();
            
            // Crear diálogo de actualización
            JDialog dialog = new JDialog(this, "Actualizar Fuente de Agua", true);
            dialog.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            
            // Crear campos de entrada con valores actuales
            JTextField capacityField = new JTextField(String.format("%.2f", source.getCapacity()), 10);
            JTextField locationField = new JTextField(source.getLocation(), 10);
            JTextField levelField = new JTextField(String.format("%.2f", source.getCurrentLevel()), 10);
            String[] qualities = {"GOOD", "FAIR", "POOR"};
            JComboBox<String> qualityCombo = new JComboBox<>(qualities);
            qualityCombo.setSelectedItem(source.getQuality());
            
            // Panel para capacidad con unidad
            JPanel capacityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            capacityPanel.add(capacityField);
            capacityPanel.add(new JLabel("m³"));
            
            // Panel para nivel con unidad
            JPanel levelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            levelPanel.add(levelField);
            levelPanel.add(new JLabel("m³"));
            
            // Agregar componentes con GridBagLayout
            gbc.gridx = 0; gbc.gridy = 0;
            dialog.add(new JLabel("ID: " + source.getId()), gbc);
            
            gbc.gridx = 0; gbc.gridy = 1;
            dialog.add(new JLabel("Tipo: " + source.getType()), gbc);
            
            gbc.gridx = 0; gbc.gridy = 2;
            dialog.add(new JLabel("Capacidad:"), gbc);
            gbc.gridx = 1; gbc.gridy = 2;
            dialog.add(capacityPanel, gbc);
            
            gbc.gridx = 0; gbc.gridy = 3;
            dialog.add(new JLabel("Nivel actual:"), gbc);
            gbc.gridx = 1; gbc.gridy = 3;
            dialog.add(levelPanel, gbc);
            
            gbc.gridx = 0; gbc.gridy = 4;
            dialog.add(new JLabel("Ubicación:"), gbc);
            gbc.gridx = 1; gbc.gridy = 4;
            dialog.add(locationField, gbc);
            
            gbc.gridx = 0; gbc.gridy = 5;
            dialog.add(new JLabel("Calidad:"), gbc);
            gbc.gridx = 1; gbc.gridy = 5;
            dialog.add(qualityCombo, gbc);
            
            // Panel para botones
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JButton okButton = new JButton("Actualizar");
            JButton cancelButton = new JButton("Cancelar");
            
            okButton.addActionListener(e -> {
                try {
                    // Validar capacidad
                    String capacityText = capacityField.getText().trim();
                    if (capacityText.isEmpty()) {
                        JOptionPane.showMessageDialog(dialog, "La capacidad no puede estar vacía.");
                        return;
                    }
                    double capacity = Double.parseDouble(capacityText);
                    if (capacity <= 0) {
                        JOptionPane.showMessageDialog(dialog, "La capacidad debe ser mayor que 0.");
                        return;
                    }
                    
                    // Validar nivel
                    String levelText = levelField.getText().trim();
                    if (levelText.isEmpty()) {
                        JOptionPane.showMessageDialog(dialog, "El nivel no puede estar vacío.");
                        return;
                    }
                    double level = Double.parseDouble(levelText);
                    if (level < 0) {
                        JOptionPane.showMessageDialog(dialog, "El nivel no puede ser negativo.");
                        return;
                    }
                    if (level > capacity) {
                        JOptionPane.showMessageDialog(dialog, "El nivel no puede ser mayor que la capacidad.");
                        return;
                    }
                    
                    // Validar ubicación
                    String location = locationField.getText().trim();
                    if (location.isEmpty()) {
                        JOptionPane.showMessageDialog(dialog, "La ubicación no puede estar vacía.");
                        return;
                    }
                    
                    // Obtener calidad
                    String quality = (String) qualityCombo.getSelectedItem();
                    
                    // Actualizar la fuente
                    source.setCapacity(capacity);
                    source.setCurrentLevel(level);
                    source.setLocation(location);
                    source.setQuality(quality);
                    
                    // Generar alertas si hay cambios significativos
                    double levelPercentage = (level / capacity) * 100;
                    
                    // Alerta por nivel crítico (menos de 30%)
                    if (levelPercentage < 30) {
                        controller.handleAlert(new SystemAlert(
                            String.format("Nivel crítico de agua en %s ID: %d (%.1f%%)", 
                                source.getType(), source.getId(), levelPercentage),
                            Alert.AlertType.WARNING
                        ));
                    }
                    
                    // Alerta por capacidad crítica (menos de 300 m³)
                    if (capacity < 300) {
                        controller.handleAlert(new SystemAlert(
                            String.format("Capacidad crítica en %s ID: %d (%.1f m³)", 
                                source.getType(), source.getId(), capacity),
                            Alert.AlertType.WARNING
                        ));
                    }
                    
                    // Alerta por deterioro de calidad
                    if (("GOOD".equals(previousQuality) && "FAIR".equals(quality)) ||
                        ("GOOD".equals(previousQuality) && "POOR".equals(quality)) ||
                        ("FAIR".equals(previousQuality) && "POOR".equals(quality))) {
                        
                        controller.handleAlert(new SystemAlert(
                            String.format("Calidad de agua deteriorada en %s ID: %d (%s → %s)", 
                                source.getType(), source.getId(), previousQuality, quality),
                            Alert.AlertType.WARNING
                        ));
                    }
                    
                    // Actualizar la fuente en el sistema
                    controller.updateWaterSource(source);
                    
                    // Actualizar la visualización inmediatamente
                    SwingUtilities.invokeLater(() -> {
                        updateDisplay();
                        dialog.dispose();
                        JOptionPane.showMessageDialog(this, "Fuente de agua actualizada exitosamente.");
                    });
                    
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, "Por favor ingrese un número válido para la capacidad y nivel.");
                }
            });
            
            cancelButton.addActionListener(e -> dialog.dispose());
            
            buttonPanel.add(okButton);
            buttonPanel.add(cancelButton);
            
            gbc.gridx = 0; gbc.gridy = 6;
            gbc.gridwidth = 2;
            dialog.add(buttonPanel, gbc);
            
            dialog.pack();
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese un ID válido (número entero).");
        }
    }

    /**
     * Muestra un diálogo para eliminar una fuente de agua
     */
    private void showRemoveWaterSourceDialog() {
        String idStr = JOptionPane.showInputDialog(this, "Ingrese el ID de la fuente de agua a eliminar:");
        if (idStr == null) return;
        
        try {
            int id = Integer.parseInt(idStr.trim());
            WaterSource source = controller.getWaterSourceRepository().getWaterSource(id);
            
            if (source == null) {
                JOptionPane.showMessageDialog(this, "No se encontró una fuente de agua con ese ID.");
                return;
            }
            
            // Mostrar diálogo de confirmación con detalles de la fuente
            String message = String.format(
                "¿Está seguro que desea eliminar la siguiente fuente de agua?\n\n" +
                "ID: %d\n" +
                "Tipo: %s\n" +
                "Ubicación: %s\n" +
                "Capacidad: %.2f m³\n" +
                "Calidad: %s",
                source.getId(), source.getType(), source.getLocation(), 
                source.getCapacity(), source.getQuality()
            );
            
            int result = JOptionPane.showConfirmDialog(
                this,
                message,
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );
            
            if (result == JOptionPane.YES_OPTION) {
                controller.removeWaterSource(id);
                JOptionPane.showMessageDialog(this, "Fuente de agua eliminada exitosamente.");
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese un ID válido (número entero).");
        }
    }

    /**
     * Muestra una alerta en el área de alertas
     * @param alert La alerta a mostrar
     */
    public void displayAlert(Alert alert) {
        // Actualizar la vista principal primero
        updateDisplay();
        
        // Crear un panel para la alerta
        JPanel alertPanel = createStyledPanel();
        alertPanel.setLayout(new BorderLayout(10, 0));
        alertPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(5, 5, 5, 5),
            BorderFactory.createLineBorder(new Color(200, 200, 200))
        ));
        
        // Crear un label para el icono
        JLabel iconLabel = new JLabel();
        iconLabel.setFont(new Font("Arial", Font.BOLD, 16));
        iconLabel.setPreferredSize(new Dimension(30, 30));
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Configurar colores y estilos según el tipo de alerta
        Color backgroundColor;
        Color borderColor;
        switch (alert.getType()) {
            case INFO:
                iconLabel.setText("ℹ");
                backgroundColor = new Color(230, 240, 255);
                borderColor = new Color(100, 150, 255);
                break;
            case WARNING:
                iconLabel.setText("⚠");
                backgroundColor = new Color(255, 245, 230);
                borderColor = new Color(255, 165, 0);
                break;
            case ERROR:
                iconLabel.setText("❌");
                backgroundColor = new Color(255, 230, 230);
                borderColor = new Color(255, 100, 100);
                break;
            default:
                backgroundColor = new Color(250, 250, 250);
                borderColor = new Color(200, 200, 200);
        }
        
        alertPanel.setBackground(backgroundColor);
        alertPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(5, 5, 5, 5),
            BorderFactory.createLineBorder(borderColor)
        ));
        
        // Crear un panel para el contenido
        JPanel contentPanel = createStyledPanel();
        contentPanel.setLayout(new BorderLayout(5, 5));
        contentPanel.setBackground(backgroundColor);
        
        // Crear un label para el mensaje
        JLabel messageLabel = new JLabel(alert.getMessage());
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        messageLabel.setForeground(new Color(50, 50, 50));
        
        // Crear un label para la fecha
        JLabel dateLabel = new JLabel(alert.getTimestamp().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        dateLabel.setFont(new Font("Arial", Font.ITALIC, 10));
        dateLabel.setForeground(new Color(100, 100, 100));
        
        // Agregar componentes al panel de contenido
        contentPanel.add(messageLabel, BorderLayout.CENTER);
        contentPanel.add(dateLabel, BorderLayout.SOUTH);
        
        // Agregar componentes al panel principal
        alertPanel.add(iconLabel, BorderLayout.WEST);
        alertPanel.add(contentPanel, BorderLayout.CENTER);
        
        // Agregar el panel al Box de alertas
        alertBox.add(alertPanel);
        alertBox.revalidate();
        alertBox.repaint();
        
        // Cambiar a la pestaña de alertas
        tabbedPane.setSelectedIndex(1);
        
        // Hacer scroll hasta la última alerta
        Container parent = alertBox.getParent();
        while (parent != null && !(parent instanceof JScrollPane)) {
            parent = parent.getParent();
        }
        if (parent instanceof JScrollPane) {
            JScrollPane scrollPane = (JScrollPane) parent;
            JScrollBar vertical = scrollPane.getVerticalScrollBar();
            SwingUtilities.invokeLater(() -> vertical.setValue(vertical.getMaximum()));
        }
    }

    /**
     * Muestra un reporte en el área de reportes
     * @param report El reporte a mostrar
     */
    public void showReport(String report) {
        // Formatear el reporte con colores y estilos
        StringBuilder formattedReport = new StringBuilder();
        
        // Agregar encabezado
        formattedReport.append("\u001B[1m=== REPORTE DE GESTIÓN DE AGUA ===\u001B[0m\n\n");
        
        // Procesar el reporte línea por línea
        String[] lines = report.split("\n");
        for (String line : lines) {
            if (line.startsWith("Total de fuentes:")) {
                formattedReport.append("\u001B[1m").append(line).append("\u001B[0m\n");
            } else if (line.startsWith("ID:")) {
                formattedReport.append("\n\u001B[1m").append(line).append("\u001B[0m\n");
            } else if (line.startsWith("Tipo:")) {
                formattedReport.append("\u001B[34m").append(line).append("\u001B[0m\n");
            } else if (line.startsWith("Ubicación:")) {
                formattedReport.append("\u001B[36m").append(line).append("\u001B[0m\n");
            } else if (line.startsWith("Capacidad:")) {
                formattedReport.append("\u001B[32m").append(line).append("\u001B[0m\n");
            } else if (line.startsWith("Calidad:")) {
                String quality = line.substring(8).trim();
                switch (quality) {
                    case "GOOD":
                        formattedReport.append("\u001B[32m"); // Verde
                        break;
                    case "FAIR":
                        formattedReport.append("\u001B[33m"); // Amarillo
                        break;
                    case "POOR":
                        formattedReport.append("\u001B[31m"); // Rojo
                        break;
                    default:
                        formattedReport.append("\u001B[0m"); // Reset
                }
                formattedReport.append(line).append("\u001B[0m\n");
            } else if (line.startsWith("---")) {
                formattedReport.append("\n");
            } else {
                formattedReport.append(line).append("\n");
            }
        }
        
        // Agregar pie de página
        formattedReport.append("\n\u001B[1m=== FIN DEL REPORTE ===\u001B[0m\n");
        
        reportArea.setText(formattedReport.toString());
        tabbedPane.setSelectedIndex(2); // Cambiar a la pestaña de reportes
    }

    /**
     * Actualiza la pantalla con la información actual de las fuentes de agua
     */
    public void updateDisplay() {
        if (controller == null || controller.getWaterSourceRepository() == null) {
            return; // No actualizar si el controlador o el repositorio no están inicializados
        }
        
        SwingUtilities.invokeLater(() -> {
            List<WaterSource> sources = controller.getWaterSourceRepository().listWaterSources();
            waterSourceBox.removeAll();
            
            for (WaterSource source : sources) {
                // Calcular el porcentaje de nivel y asegurar que esté entre 0 y 100
                double currentLevel = Math.max(0, Math.min(source.getCurrentLevel(), source.getCapacity()));
                double levelPercentage = (currentLevel / source.getCapacity()) * 100;
                levelPercentage = Math.max(0, Math.min(levelPercentage, 100));
                
                // Crear panel para la fuente
                JPanel sourcePanel = createStyledPanel();
                sourcePanel.setLayout(new BorderLayout());
                sourcePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                
                // Panel para la información
                JPanel infoPanel = createStyledPanel();
                infoPanel.setLayout(new GridLayout(0, 2, 5, 5));
                
                // ID y Tipo
                infoPanel.add(createStyledLabel("ID:"));
                infoPanel.add(createStyledLabel(String.valueOf(source.getId())));
                infoPanel.add(createStyledLabel("Tipo:"));
                infoPanel.add(createStyledLabel(source.getType()));
                
                // Ubicación
                infoPanel.add(createStyledLabel("Ubicación:"));
                infoPanel.add(createStyledLabel(source.getLocation()));
                
                // Capacidad
                infoPanel.add(createStyledLabel("Capacidad:"));
                infoPanel.add(createStyledLabel(String.format("%.2f m³", source.getCapacity())));
                
                // Nivel actual con barra de progreso
                JPanel levelPanel = createStyledPanel();
                levelPanel.setLayout(new BorderLayout());
                
                JProgressBar progressBar = new JProgressBar(0, 100);
                progressBar.setValue((int) levelPercentage);
                progressBar.setStringPainted(true);
                progressBar.setString(String.format("%.1f%%", levelPercentage));
                progressBar.setForeground(getLevelColor(levelPercentage));
                
                levelPanel.add(createStyledLabel("Nivel actual:"), BorderLayout.WEST);
                levelPanel.add(progressBar, BorderLayout.CENTER);
                
                infoPanel.add(createStyledLabel("Nivel:"));
                infoPanel.add(levelPanel);
                
                // Calidad
                JLabel qualityLabel = createStyledLabel(source.getQuality());
                qualityLabel.setForeground(getQualityColor(source.getQuality()));
                
                infoPanel.add(createStyledLabel("Calidad:"));
                infoPanel.add(qualityLabel);
                
                // Agregar panel de información al panel principal
                sourcePanel.add(infoPanel, BorderLayout.CENTER);
                
                // Agregar separador
                if (sources.indexOf(source) < sources.size() - 1) {
                    sourcePanel.setBorder(BorderFactory.createCompoundBorder(
                        sourcePanel.getBorder(),
                        BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 200, 200))
                    ));
                }
                
                // Agregar el panel al Box de fuentes
                waterSourceBox.add(sourcePanel);
            }
            
            waterSourceBox.revalidate();
            waterSourceBox.repaint();
        });
    }

    /**
     * Obtiene el color para la calidad del agua
     * @param quality La calidad del agua
     * @return El color correspondiente
     */
    private Color getQualityColor(String quality) {
        switch (quality) {
            case "GOOD":
                return new Color(0, 128, 0); // Verde
            case "FAIR":
                return new Color(255, 165, 0); // Amarillo
            case "POOR":
                return new Color(255, 0, 0); // Rojo
            default:
                return Color.BLACK;
        }
    }

    /**
     * Obtiene el color para el nivel de agua
     * @param percentage El porcentaje de nivel
     * @return El color correspondiente
     */
    private Color getLevelColor(double percentage) {
        if (percentage < 20) {
            return new Color(255, 0, 0); // Rojo
        } else if (percentage < 50) {
            return new Color(255, 165, 0); // Amarillo
        } else {
            return new Color(0, 128, 0); // Verde
        }
    }

    /**
     * Crea un panel con estilo común
     * @return El panel con estilo
     */
    private JPanel createStyledPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(250, 250, 250));
        return panel;
    }

    /**
     * Crea un label con estilo común
     * @param text El texto del label
     * @return El label con estilo
     */
    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 12));
        return label;
    }

    /**
     * Inicia la simulación de cambios en los niveles de agua
     */
    private void startSimulation() {
        if (simulationTimer != null) {
            simulationTimer.stop();
        }
        
        simulationTimer = new Timer(5000, e -> {
            List<WaterSource> sources = controller.getWaterSourceRepository().listWaterSources();
            if (sources.isEmpty()) {
                return; // No hay fuentes para simular
            }
            
            for (WaterSource source : sources) {
                // Simular cambio aleatorio en el nivel de agua (-5% a +5%)
                double change = (random.nextDouble() * 0.1 - 0.05) * source.getCapacity();
                double newLevel = source.getCurrentLevel() + change;
                
                // Asegurar que el nivel esté entre 0 y la capacidad
                newLevel = Math.max(0, Math.min(newLevel, source.getCapacity()));
                source.setCurrentLevel(newLevel);
                
                // Calcular porcentaje para las alertas
                double percentage = (newLevel / source.getCapacity()) * 100;
                
                // Generar alertas basadas en el nivel (menos de 30%)
                if (percentage < 30) {
                    controller.handleAlert(new SystemAlert(
                        String.format("Nivel crítico de agua en %s ID: %d (%.1f%%)", 
                            source.getType(), source.getId(), percentage),
                        Alert.AlertType.WARNING
                    ));
                }
                
                // Generar alerta por capacidad crítica (menos de 300 m³)
                if (source.getCapacity() < 300) {
                    controller.handleAlert(new SystemAlert(
                        String.format("Capacidad crítica en %s ID: %d (%.1f m³)", 
                            source.getType(), source.getId(), source.getCapacity()),
                        Alert.AlertType.WARNING
                    ));
                }
                
                // Simular cambios en la calidad (5% de probabilidad)
                if (random.nextDouble() < 0.05) {
                    String[] qualities = {"GOOD", "FAIR", "POOR"};
                    String newQuality = qualities[random.nextInt(qualities.length)];
                    String oldQuality = source.getQuality();
                    source.setQuality(newQuality);
                    
                    // Generar alerta solo si la calidad empeora
                    if (("GOOD".equals(oldQuality) && "FAIR".equals(newQuality)) ||
                        ("GOOD".equals(oldQuality) && "POOR".equals(newQuality)) ||
                        ("FAIR".equals(oldQuality) && "POOR".equals(newQuality))) {
                        
                        controller.handleAlert(new SystemAlert(
                            String.format("Calidad de agua deteriorada en %s ID: %d (%s → %s)", 
                                source.getType(), source.getId(), oldQuality, newQuality),
                            Alert.AlertType.WARNING
                        ));
                    }
                }
            }
            
            // Actualizar la visualización
            updateDisplay();
        });
        
        simulationTimer.start();
        controller.handleAlert(new SystemAlert(
            "Simulación de niveles de agua iniciada",
            Alert.AlertType.INFO
        ));
    }

    /**
     * Detiene la simulación de cambios en los niveles de agua
     */
    private void stopSimulation() {
        if (simulationTimer != null) {
            simulationTimer.stop();
            simulationTimer = null;
            
            // Notificar al usuario que la simulación se ha detenido
            controller.handleAlert(new SystemAlert(
                "Simulación de niveles de agua detenida",
                Alert.AlertType.INFO
            ));
            
            // Actualizar la visualización una última vez
            updateDisplay();
        }
    }
} 