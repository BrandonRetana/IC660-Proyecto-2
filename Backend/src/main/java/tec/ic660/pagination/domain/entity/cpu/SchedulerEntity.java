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
    private final Map<Integer, PTR> allPTRs;

    public SchedulerEntity() {
        this.processTable = new Hashtable<>();
        this.allPTRs = new Hashtable<>();
    }

    public void addPtr2Process(int id, PTR ptr) {
        Integer pid = id;
        this.allPTRs.put(ptr.getId(), ptr);
        if (this.processTable.containsKey(pid)) {
            List<PTR> ptrList = this.processTable.get(pid);
            ptrList.add(ptr);
            return;
        }
        List<PTR> ptrList = new ArrayList<>();
        ptrList.add(ptr);
        this.processTable.put(pid, ptrList);
    }

    public PTR getPTRbyId(Integer id) {
        return this.allPTRs.get(id);
    }

    public void deletePTRInProccess(Integer id) {
        PTR ptr2delete = this.getPTRbyId(id);

        if (ptr2delete == null) {
            System.out.println("PTR not found");
            return;
        }

        Integer idProcess = ptr2delete.getPid();

        List<PTR> ptrList = this.processTable.get(idProcess);
        if (ptrList != null) {
            ptrList.remove(ptr2delete);
        }
        this.allPTRs.remove(id);
    }

    public void killProcess(Integer pid) {
        if (!this.processTable.containsKey(pid)) {
            System.out.println("Process not found");
            return;
        }
        List<PTR> ptrList = this.processTable.get(pid);
        for (PTR ptr : ptrList) {
            this.allPTRs.remove(ptr.getId());
        }
        this.processTable.remove(pid);
        System.out.println("Process and all associated PTRs removed successfully");
    }

    public Integer getNumberOfProcess() {
        return this.processTable.size();
    }

}
