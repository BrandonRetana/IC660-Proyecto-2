package tec.ic660.pagination.domain.entity.cpu;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import tec.ic660.pagination.domain.valueObjects.PTR;

public class ProcessEntity {
    private UUID pid;
    private List<UUID> sybomTable;

    public ProcessEntity() {
        this.sybomTable = new ArrayList<UUID>();
        this.pid = UUID.randomUUID();
    }

    public UUID getPid() {
        return pid;
    }

    public void setPid(UUID pid) {
        this.pid = pid;
    }

    public void addPtr2SymbolTable(PTR ptr){
        sybomTable.add(pid);
    }

    public List<UUID> getSymbolTable() {
        return sybomTable;
    }


}
