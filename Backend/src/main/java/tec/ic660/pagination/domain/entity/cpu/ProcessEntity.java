package tec.ic660.pagination.domain.entity.cpu;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import tec.ic660.pagination.domain.valueObjects.PTR;

public class ProcessEntity {
    private int pid;
    private List<PTR> sybomTable;
    

    public ProcessEntity() {
        this.sybomTable = new ArrayList<PTR>();
        this.pid = new Random().nextInt();
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public void addPtr2SymbolTable(PTR ptr) {
        sybomTable.add(ptr);
    }

    public List<PTR> getSymbolTable() {
        return sybomTable;
    }

}
