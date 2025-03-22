package com.aqua.report;

/**
 * Interface for report generation.
 * This interface defines the contract for different types of report generators.
 */
public interface IReportGenerator {
    
    /**
     * Generates a report
     * @return The generated report as a String
     */
    String generateReport();
    
    /**
     * Exports the report in a specific format
     * @param format The format to export the report in (e.g., "PDF", "EXCEL")
     */
    void exportReport(String format);
} 