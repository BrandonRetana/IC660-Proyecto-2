package tec.ic660.pagination.domain.entity.cpu;

import java.util.List;
import java.util.Map;
import java.util.Hashtable;
import java.util.ArrayList;

import tec.ic660.pagination.domain.valueObjects.PTR;

public class SchedulerEntity {
    private final Map<Integer, List<PTR>> processTable;
    private final Map<Integer, PTR> allPTRs;

    public SchedulerEntity() {
        this.processTable = new Hashtable<>();
        this.allPTRs = new Hashtable<>();
    }

    public void addPtr2Process(int pid, PTR ptr) {
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

    public void deletePTRInProccess(Integer ptrId) {
        PTR ptr2delete = this.getPTRbyId(ptrId);

        if (ptr2delete == null) {
            System.out.println("PTR not found");
            return;
        }

        Integer idProcess = ptr2delete.getPid();

        List<PTR> ptrList = this.processTable.get(idProcess);
        if (ptrList != null) {
            ptrList.remove(ptr2delete);
        }
        this.allPTRs.remove(ptrId);
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
    }

    public Integer getNumberOfProcess() {
        return this.processTable.size();
    }

}
