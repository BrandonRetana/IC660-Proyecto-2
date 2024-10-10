package tec.ic660.pagination.aplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tec.ic660.pagination.domain.entity.cpu.SchedulerEntity;
import tec.ic660.pagination.domain.entity.memory.MMUEntity;
import tec.ic660.pagination.domain.valueObjects.PTR;

import java.util.Queue;

@Service
public class SimulationService {
    @Autowired
    private MMUEntity mmu;

    @Autowired
    private SchedulerEntity scheduler;

    private Queue<String> instructionsQueue;

    private void executeNew(String instruction) {
        String[] parts = instruction.substring(4, instruction.length() - 1).split(", ");
        int id = Integer.parseInt(parts[0]);
        int size = Integer.parseInt(parts[1]);
        PTR ptr = this.mmu.newMemory(id, size);
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
        this.mmu.deleteMemory(ptr);
        this.scheduler.deletePTRInProccess(id);
    }

    private void executeKill(String instruction) {
        int pid = Integer.parseInt(instruction.substring(5, instruction.length() - 1));
        this.mmu.killProcessMemory(pid);
        this.scheduler.killProcess(pid);
    }

    public SimulationService() {
    }

    public void executeNextStep() {
        for (String instruction : instructionsQueue) {
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
    }

    public Queue<String> getInstructionsQueue() {
        return instructionsQueue;
    }

    public void setInstructionsQueue(Queue<String> instructionsQueue) {
        this.instructionsQueue = instructionsQueue;
    }

}
