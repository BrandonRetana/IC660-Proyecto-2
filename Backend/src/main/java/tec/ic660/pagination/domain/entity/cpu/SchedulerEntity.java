package tec.ic660.pagination.domain.entity.cpu;

import java.util.List;

public class SchedulerEntity {
    private List<ProcessEntity> processTable;

    public SchedulerEntity(List<ProcessEntity> processTable) {
        this.processTable = processTable;
    }

    public List<ProcessEntity> getProcessTable() {
        return processTable;
    }

    public void setProcessTable(List<ProcessEntity> processTable) {
        this.processTable = processTable;
    }

    
}
