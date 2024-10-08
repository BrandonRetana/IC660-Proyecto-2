package tec.ic660.pagination.domain.entity.memory;

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

    public void setInRealMemory(boolean isInRealMemory) {
        this.isInRealMemory = isInRealMemory;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}
