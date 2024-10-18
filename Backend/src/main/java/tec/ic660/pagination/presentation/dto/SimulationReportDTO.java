package tec.ic660.pagination.presentation.dto;

import java.util.List;

public class SimulationReportDTO {

    public SimulationReportDTO() {}

    // Datos de la tabla de páginas
    private List<TableRawDTO> pageTable;

    // Información del proceso y el tiempo de simulación
    private Integer simulationDuration;  // Tiempo total de la simulación en unidades
    private Integer totalProcesses;      // Número total de procesos

    // Información de la memoria
    private Integer realMemoryUsageInKb;         // Memoria real utilizada en KB
    private Float realMemoryUsagePercentage;    // Porcentaje de uso de la memoria real
    private Integer virtualMemoryUsageInKb;      // Memoria virtual utilizada en KB
    private Float virtualMemoryUsagePercentage; // Porcentaje de uso de la memoria virtual

    // Información sobre las páginas y la memoria
    private Integer internalFragmentation;       // Fragmentación interna en la memoria
    private Integer trashingDuration;            // Tiempo total de trashing
    private Float trashingPercentage;           // Porcentaje de tiempo en trashing
    private Integer pagesLoadedInMemory;         // Número de páginas cargadas en la memoria real
    private Integer pagesInVirtualMemory;        // Número de páginas en la memoria virtual
    public List<TableRawDTO> getPageTable() {
        return pageTable;
    }
    public void setPageTable(List<TableRawDTO> pageTable) {
        this.pageTable = pageTable;
    }
    public Integer getSimulationDuration() {
        return simulationDuration;
    }
    public void setSimulationDuration(Integer simulationDuration) {
        this.simulationDuration = simulationDuration;
    }
    public Integer getTotalProcesses() {
        return totalProcesses;
    }
    public void setTotalProcesses(Integer totalProcesses) {
        this.totalProcesses = totalProcesses;
    }
    public Integer getRealMemoryUsageInKb() {
        return realMemoryUsageInKb;
    }
    public void setRealMemoryUsageInKb(Integer realMemoryUsageInKb) {
        this.realMemoryUsageInKb = realMemoryUsageInKb;
    }
    public Float getRealMemoryUsagePercentage() {
        return realMemoryUsagePercentage;
    }
    public void setRealMemoryUsagePercentage(Float realMemoryUsagePercentage) {
        this.realMemoryUsagePercentage = realMemoryUsagePercentage;
    }
    public Integer getVirtualMemoryUsageInKb() {
        return virtualMemoryUsageInKb;
    }
    public void setVirtualMemoryUsageInKb(Integer virtualMemoryUsageInKb) {
        this.virtualMemoryUsageInKb = virtualMemoryUsageInKb;
    }
    public Float getVirtualMemoryUsagePercentage() {
        return virtualMemoryUsagePercentage;
    }
    public void setVirtualMemoryUsagePercentage(Float virtualMemoryUsagePercentage) {
        this.virtualMemoryUsagePercentage = virtualMemoryUsagePercentage;
    }
    public Integer getInternalFragmentation() {
        return internalFragmentation;
    }
    public void setInternalFragmentation(Integer internalFragmentation) {
        this.internalFragmentation = internalFragmentation;
    }
    public Integer getTrashingDuration() {
        return trashingDuration;
    }
    public void setTrashingDuration(Integer trashingDuration) {
        this.trashingDuration = trashingDuration;
    }
    public Float getTrashingPercentage() {
        return trashingPercentage;
    }
    public void setTrashingPercentage(Float trashingPercentage) {
        this.trashingPercentage = trashingPercentage;
    }
    public Integer getPagesLoadedInMemory() {
        return pagesLoadedInMemory;
    }
    public void setPagesLoadedInMemory(Integer pagesLoadedInMemory) {
        this.pagesLoadedInMemory = pagesLoadedInMemory;
    }
    public Integer getPagesInVirtualMemory() {
        return pagesInVirtualMemory;
    }
    public void setPagesInVirtualMemory(Integer pagesInVirtualMemory) {
        this.pagesInVirtualMemory = pagesInVirtualMemory;
    }

    

}