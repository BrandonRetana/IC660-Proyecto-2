package tec.ic660.pagination.domain.entity.cpu;

import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class SchedulerEntity {
    private List<ProcessEntity> processTable;

    public SchedulerEntity() {}

    public List<ProcessEntity> getProcessTable() {
        return processTable;
    }

    public void setProcessTable(List<ProcessEntity> processTable) {
        this.processTable = processTable;
    }
}
