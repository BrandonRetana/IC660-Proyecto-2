package tec.ic660.pagination.domain.entity;

import java.util.UUID;

public class PageEntity {
    private final UUID id;
    private final int physicalAddres;
    private boolean isInVirtualMemory;
    private boolean isInUse;
    
    public PageEntity(int physicalAddres, boolean isInVirtualMemory) {
        this.id = UUID.randomUUID();
        this.physicalAddres = physicalAddres;
        this.isInVirtualMemory = isInVirtualMemory;
        this.isInUse = false;
    }

    public UUID getId() {
        return id;
    }

    public int getPhysicalAddres() {
        return physicalAddres;
    }

    public boolean isInVirtualMemory() {
        return isInVirtualMemory;
    }

    public void setInVirtualMemory(boolean isInVirtualMemory) {
        this.isInVirtualMemory = isInVirtualMemory;
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
