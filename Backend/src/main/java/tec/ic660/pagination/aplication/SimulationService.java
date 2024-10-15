package tec.ic660.pagination.aplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tec.ic660.pagination.domain.algorithms.FIFOAlgorithm;
import tec.ic660.pagination.domain.algorithms.MRUAlgorithm;
import tec.ic660.pagination.domain.algorithms.PagingAlgorithm;
import tec.ic660.pagination.domain.algorithms.RandomAlgorithm;
import tec.ic660.pagination.domain.algorithms.SecondChanceAlgorithm;
import tec.ic660.pagination.domain.entity.cpu.SchedulerEntity;
import tec.ic660.pagination.domain.entity.memory.MMUEntity;
import tec.ic660.pagination.domain.entity.memory.PageEntity;
import tec.ic660.pagination.domain.valueObjects.PTR;
import tec.ic660.pagination.infraestructure.InstructionGenerator;
import tec.ic660.pagination.presentation.dto.ConfigRandomDTO;
import tec.ic660.pagination.presentation.dto.TableRawDTO;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Service
public class SimulationService {
    @Autowired
    private MMUEntity mmu;

    @Autowired
    private SchedulerEntity scheduler;

    @Autowired
    private InstructionGenerator instructionGenerator;

    private PagingAlgorithm algorithm;

    private Queue<String> instructionsQueue;

    private Integer totalMemory;

    public SimulationService() {
        this.totalMemory = 0;
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
    }

    private void executeUse(String instruction) {
        int id = Integer.parseInt(instruction.substring(4, instruction.length() - 1));
        PTR ptr = this.scheduler.getPTRbyId(id);
        this.mmu.useMemory(ptr);
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

    public List<TableRawDTO> getTableData() {
        List<TableRawDTO> totalPages = generateJsonTableRawData(this.mmu.getRealMemory(), this.mmu.getVirtualMemory());
        Integer fragmentation = getInteralFragmentation(this.totalMemory, totalPages.size());

        return totalPages;
    }

    private List<TableRawDTO> generateJsonTableRawData(List<PageEntity> realMemory, List<PageEntity> virtualMemory) {
        List<TableRawDTO> logicalMemoryData = new LinkedList<>();
        List<PageEntity> logicalMemory = new LinkedList<>(realMemory);
        logicalMemory.addAll(virtualMemory);

        TableRawDTO dto;

        for (int i = 0; i < logicalMemory.size(); i++) {
            PageEntity pageEntity = logicalMemory.get(i);
            if (pageEntity == null) {
                continue;
            }
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
            }
            if (pageEntity.isInRealMemory()) {
                dto.setMAddr(pageEntity.getPhysicalAddres());
            } else {
                dto.setDAddr(pageEntity.getPhysicalAddres());
            }
            // Set mark
            dto.setMark("");
            if (pageEntity.isMarked()) {
                dto.setMark("X");
            }
            // Set time loaded
            dto.setLoadedT("1s");
            logicalMemoryData.add(dto);
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

    public Queue<String> getInstructionsQueue() {
        return instructionsQueue;
    }

    public void setInstructionsQueue(Queue<String> instructionsQueue) {
        this.instructionsQueue = instructionsQueue;
    }
}
