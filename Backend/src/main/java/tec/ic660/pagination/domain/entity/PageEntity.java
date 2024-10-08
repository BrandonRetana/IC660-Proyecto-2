package tec.ic660.pagination.domain.entity;

import java.util.UUID;

public class PageEntity {
    private final UUID id;
    private final int physicalAddres;
    private boolean isInRealMemory;
    private boolean used;
    
    public PageEntity(int physicalAddres, boolean isInRealMemory) {
        this.id = UUID.randomUUID();
        this.physicalAddres = physicalAddres;
        this.isInRealMemory = isInRealMemory;
        this.used = false;
    }

    public UUID getId() {
        return id;
    }

    public int getPhysicalAddres() {
        return physicalAddres;
    }

    public boolean isInRealMemory() {
        return isInRealMemory;
    }

    public void setInVirtualMemory(boolean isInRealMemory) {
        this.isInRealMemory = isInRealMemory;
    }

    public boolean isInUse() {
        return isInUse;
    }

    public void setInPtr(boolean isInUse) {
        this.isInUse = isInUse;
    }

    public void setInUse(boolean isInUse) {
        this.isInUse = isInUse;
    }
    
}
