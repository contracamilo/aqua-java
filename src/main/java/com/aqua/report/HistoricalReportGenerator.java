package com.aqua.report;

import com.aqua.domain.WaterSource;
import com.aqua.repository.IWaterSourceRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Generador de reportes históricos del sistema de gestión de agua.
 * Esta clase se encarga de generar reportes detallados sobre el estado de las fuentes de agua.
 */
public class HistoricalReportGenerator implements IReportGenerator {
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private final IWaterSourceRepository repository;

    /**
     * Constructor para HistoricalReportGenerator
     * @param repository El repositorio de fuentes de agua
     */
    public HistoricalReportGenerator(IWaterSourceRepository repository) {
        this.repository = repository;
    }

    /**
     * Genera un reporte histórico del sistema
     * @return El reporte generado como una cadena de texto
     */
    @Override
    public String generateReport() {
        StringBuilder report = new StringBuilder();
        report.append("Sistema de Gestión de Agua - Reporte Histórico\n");
        report.append("Generado el: ").append(LocalDateTime.now().format(DATE_FORMATTER)).append("\n\n");
        
        List<WaterSource> sources = repository.listWaterSources();
        report.append("Total de Fuentes de Agua: ").append(sources.size()).append("\n\n");
        
        for (WaterSource source : sources) {
            report.append("ID de Fuente: ").append(source.getId()).append("\n");
            report.append("Tipo: ").append(source.getType()).append("\n");
            report.append("Ubicación: ").append(source.getLocation()).append("\n");
            report.append("Capacidad: ").append(source.getCapacity()).append(" m³\n");
            report.append("Calidad Actual: ").append(source.getQuality()).append("\n");
            report.append("----------------------------------------\n");
        }
        
        return report.toString();
    }

    /**
     * Exporta el reporte en el formato especificado
     * @param format El formato de exportación (solo soporta "EXCEL" por ahora)
     */
    @Override
    public void exportReport(String format) {
        if ("EXCEL".equalsIgnoreCase(format)) {
            exportToExcel();
        } else {
            throw new IllegalArgumentException("Formato no soportado: " + format);
        }
    }
    
    /**
     * Exporta el reporte a formato Excel
     */
    private void exportToExcel() {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Water Sources Report");
            
            // Create header row
            Row headerRow = sheet.createRow(0);
            String[] headers = {"ID", "Type", "Location", "Capacity", "Quality"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }
            
            // Add data rows
            List<WaterSource> sources = repository.listWaterSources();
            int rowNum = 1;
            for (WaterSource source : sources) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(source.getId());
                row.createCell(1).setCellValue(source.getType());
                row.createCell(2).setCellValue(source.getLocation());
                row.createCell(3).setCellValue(source.getCapacity());
                row.createCell(4).setCellValue(source.getQuality());
            }
            
            // Auto-size columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
            
            // Save the workbook
            String fileName = "water_sources_report_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".xlsx";
            try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
                workbook.write(fileOut);
            }
            
        } catch (IOException e) {
            throw new RuntimeException("Failed to generate Excel report", e);
        }
    }
} 