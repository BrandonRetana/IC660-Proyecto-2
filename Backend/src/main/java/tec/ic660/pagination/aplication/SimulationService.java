package tec.ic660.pagination.aplication;

import tec.ic660.pagination.domain.algorithms.FIFOAlgorithm;
import tec.ic660.pagination.domain.algorithms.MRUAlgorithm;
import tec.ic660.pagination.domain.algorithms.OptimalAlgorithm;
import tec.ic660.pagination.domain.algorithms.PagingAlgorithm;
import tec.ic660.pagination.domain.algorithms.RandomAlgorithm;
import tec.ic660.pagination.domain.algorithms.SecondChanceAlgorithm;
import tec.ic660.pagination.domain.entity.cpu.SchedulerEntity;
import tec.ic660.pagination.domain.entity.memory.MMUEntity;
import tec.ic660.pagination.domain.entity.memory.PageEntity;
import tec.ic660.pagination.domain.valueObjects.PTR;
import tec.ic660.pagination.infraestructure.InstructionGenerator;
import tec.ic660.pagination.presentation.dto.ConfigRandomDTO;
import tec.ic660.pagination.presentation.dto.SimulationReportDTO;
import tec.ic660.pagination.presentation.dto.TableRawDTO;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SimulationService {

    private MMUEntity mmu;

    private SchedulerEntity scheduler;

    private InstructionGenerator instructionGenerator;

    private PagingAlgorithm algorithm;

    private Queue<String> instructionsQueue;

    private Integer totalMemory;

    private int counterPtr = 0;

    public SimulationService() {
        this.totalMemory = 0;
        this.mmu = new MMUEntity();
        this.scheduler = new SchedulerEntity();
        this.instructionGenerator = new InstructionGenerator();
    }
    /*------------------------------------- Execute instructions -------------------------------------*/

    private void executeNew(String instruction) {
        String[] parts = instruction.substring(4, instruction.length() - 1).split(",");
        int id = Integer.parseInt(parts[0].trim());
        int size = Integer.parseInt(parts[1].trim());
        PTR ptr = this.mmu.newMemory(id, size);
        ptr.setInitialMemory(size);
        this.totalMemory += size;
        this.scheduler.addPtr2Process(id, ptr);
        this.counterPtr++;
        System.out.println(counterPtr);
    }

    private void executeUse(String instruction) {
        int id = Integer.parseInt(instruction.substring(4, instruction.length() - 1));
        PTR ptr = this.scheduler.getPTRbyId(id);
        try {
        this.mmu.useMemory(ptr);
    } catch (Exception e) {
        System.out.println("instructions: "+instruction+"id "+id+"ptr "+ptr);
    }
    }

    private void executeDelete(String instruction) {
        int id = Integer.parseInt(instruction.substring(7, instruction.length() - 1));
        PTR ptr = this.scheduler.getPTRbyId(id);
        this.totalMemory -= ptr.getInitialMemory();
        this.mmu.deleteMemory(ptr);
        this.scheduler.deletePTRInProccess(id);
    }

    private void executeKill(String instruction) {
        int pid = Integer.parseInt(instruction.substring(5, instruction.length() - 1));
        this.mmu.killProcessMemory(pid);
        this.scheduler.killProcess(pid);
    }

    public void executeNextStep() {
        String instruction = instructionsQueue.poll();
        System.out.println(instruction);
        if (instruction.startsWith("new")) {
            this.executeNew(instruction);
        } else if (instruction.startsWith("use")) {
            this.executeUse(instruction);
        } else if (instruction.startsWith("delete")) {
            this.executeDelete(instruction);
        } else if (instruction.startsWith("kill")) {
            this.executeKill(instruction);
        } else {
            System.out.println("Unknown instruction: " + instruction);
        }

    }

    /*------------------------------------- Calculate metrics -------------------------------------*/

    private Integer getInteralFragmentation(Integer totalMemory, Integer numberOfPages) {
        return (numberOfPages * 4096) - totalMemory;

    }

    private double calculatePercentage(double part, double total) {
        if (total > 0) {
            return ((double) part / total) * 100;
        } else {
            return 0.0;
        }
    }

    private List<TableRawDTO> generateJsonTableRawData(List<PageEntity> realMemory, List<PageEntity> virtualMemory) {
        List<TableRawDTO> logicalMemoryData = new LinkedList<>();
        List<PageEntity> logicalMemory = new LinkedList<>(realMemory);
        logicalMemory.addAll(virtualMemory);

        TableRawDTO dto;

        for (int i = 0; i < logicalMemory.size(); i++) {
            PageEntity pageEntity = null;
            try {
                pageEntity = logicalMemory.get(i); // Obtener la entidad de la memoria lógica
                if (pageEntity == null) {
                    continue; // Saltar si la página es nula
                }
                
                // Crear el DTO
                dto = new TableRawDTO();
                
                // Set page ID
                dto.setPageId(pageEntity.getId());
                
                // Set PID
                dto.setPid(this.scheduler.getPTRbyId(pageEntity.getPtrId()).getPid());
                
                // Set logical memory position
                dto.setLAddr(i);
                
                // Set memory real or virtual
                if (pageEntity.isInRealMemory()) {
                    dto.setLoaded("X");
                    dto.setMAddr(pageEntity.getPhysicalAddres());
                } else {
                    dto.setDAddr(pageEntity.getPhysicalAddres());
                }
                
                // Set mark
                dto.setMark(pageEntity.isMarked() ? "X" : "");
                
                // Set time loaded
                dto.setLoadedT("1s");  // Esto parece ser un valor fijo
                
                // Agregar a la lista
                logicalMemoryData.add(dto);
            } catch (Exception e) {
                // Si la entidad no es nula, imprimirla junto con el error
                if (pageEntity != null) {
                    System.out.println("Error al procesar la entidad de página: " + pageEntity.toString());
                }
                System.out.println("Error en la posición " + i + ": " + e.getMessage());
                e.printStackTrace(); // Imprimir el stack trace completo para ayudar a identificar el error
                System.exit(1);
            }
        }

        return logicalMemoryData;
    }

    public Queue<String> setSimulationConfig(ConfigRandomDTO configDTO) {

        Queue<String> randomInstructions = instructionGenerator.generateInstructions(configDTO.getSeed(),
                configDTO.getProcess(), configDTO.getOperations());
        setInstructionsQueue(randomInstructions);

        switch (configDTO.getAlgorithm()) {
            case 1:
                algorithm = new FIFOAlgorithm();
                break;
            case 2:
                algorithm = new SecondChanceAlgorithm();
                break;
            case 3:
                algorithm = new MRUAlgorithm();
                break;
            case 4:
                algorithm = new RandomAlgorithm();
                break;
        }
        this.mmu.setPagingAlgorithm(algorithm);
        return randomInstructions;
    }

    public SimulationReportDTO getSimulationReport() {
        // Table data
        List<TableRawDTO> pageTable = generateJsonTableRawData(this.mmu.getRealMemory(), this.mmu.getVirtualMemory());

        // Process and simulation time
        Integer simulationDuration = this.mmu.getSimulationTime();
        Integer totalProcesses = this.scheduler.getNumberOfProcess();

        // Memory information
        Integer realMemoryUsageInKb = this.mmu.getPagesInMemory() * 4;
        Double realMemoryUsagePercentage = calculatePercentage(realMemoryUsageInKb, 400);
        Integer virtualMemoryUsageInKb = this.mmu.getVirtualMemory().size() * 4;
        Double virtualMemoryUsagePercentage = calculatePercentage(virtualMemoryUsageInKb, realMemoryUsageInKb);

        // Pages information
        Integer internalFragmentation = getInteralFragmentation(this.totalMemory, pageTable.size());
        Integer trashingDuration = this.mmu.getTrashingTime();
        Double trashingPercentage = calculatePercentage(trashingDuration, simulationDuration);
        Integer pagesLoadedInMemory = this.mmu.getPagesInMemory();
        Integer pagesInVirtualMemory = this.mmu.getVirtualMemory().size();

        // Crear el DTO y llenar sus campos
        SimulationReportDTO report = new SimulationReportDTO();
        report.setPageTable(pageTable);
        report.setSimulationDuration(simulationDuration);
        report.setTotalProcesses(totalProcesses);
        report.setRealMemoryUsageInKb(realMemoryUsageInKb);
        report.setRealMemoryUsagePercentage(realMemoryUsagePercentage);
        report.setVirtualMemoryUsageInKb(virtualMemoryUsageInKb);
        report.setVirtualMemoryUsagePercentage(virtualMemoryUsagePercentage);
        report.setInternalFragmentation(internalFragmentation);
        report.setTrashingDuration(trashingDuration);
        report.setTrashingPercentage(trashingPercentage);
        report.setPagesLoadedInMemory(pagesLoadedInMemory);
        report.setPagesInVirtualMemory(pagesInVirtualMemory);

        // Devolver el DTO con toda la información
        return report;
    }

    public Queue<String> getInstructionsQueue() {
        return instructionsQueue;
    }

    public void setInstructionsQueue(Queue<String> instructionsQueue) {
        this.instructionsQueue = instructionsQueue;
    }

    public void setAlgorithm() {
        algorithm = new FIFOAlgorithm();
        this.mmu.setPagingAlgorithm(algorithm);
    }

}
