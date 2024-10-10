package tec.ic660.pagination.aplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tec.ic660.pagination.domain.entity.cpu.SchedulerEntity;
import tec.ic660.pagination.domain.entity.memory.MMUEntity;
import java.util.Queue;

@Service
public class SimulationService {
    @Autowired
    private MMUEntity mmu;

    @Autowired
    private SchedulerEntity scheduler;

    public SimulationService() {
    }

    private Queue<String> instructionsQueue;

    public Queue<String> getInstructionsQueue() {
        return instructionsQueue;
    }

    public void setInstructionsQueue(Queue<String> instructionsQueue) {
        this.instructionsQueue = instructionsQueue;
    }

 

}
