package tec.ic660.pagination.presentation.dto;

public class DualSimulationReportDTO {

    private SimulationReportDTO simulationReport1;
    private SimulationReportDTO simulationReport2;

    public DualSimulationReportDTO() {
    }

    // Getters y Setters
    public SimulationReportDTO getSimulationReport1() {
        return simulationReport1;
    }

    public void setSimulationReport1(SimulationReportDTO simulationReport1) {
        this.simulationReport1 = simulationReport1;
    }

    public SimulationReportDTO getSimulationReport2() {
        return simulationReport2;
    }

    public void setSimulationReport2(SimulationReportDTO simulationReport2) {
        this.simulationReport2 = simulationReport2;
    }
}