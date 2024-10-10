package tec.ic660.pagination.domain.entity.cpu;

import java.util.List;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.Hashtable;
import java.util.ArrayList;

import tec.ic660.pagination.domain.valueObjects.PTR;

@Component
public class SchedulerEntity {
    private final Map<Integer, List<PTR>> processTable;

    public SchedulerEntity() {
        this.processTable = new Hashtable<>();
    }

    public void addPtr2Process(int id, PTR ptr) {
        Integer pid = id;
        if (this.processTable.containsKey(pid)) {
            List<PTR> ptrList = this.processTable.get(pid);
            ptrList.add(ptr);
            return;
        }
        List<PTR> ptrList = new ArrayList<>();
        ptrList.add(ptr);
        this.processTable.put(pid, ptrList);
    }

}
