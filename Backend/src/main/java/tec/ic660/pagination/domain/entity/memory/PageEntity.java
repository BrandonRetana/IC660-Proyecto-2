package tec.ic660.pagination.domain.entity.memory;

public class PageEntity {
    private final int id;
    private final int physicalAddres;
    private boolean isInRealMemory;
    private boolean used;
    private static int counter = 0;
    
    public PageEntity(int physicalAddres, boolean isInRealMemory) {
        this.id = ++counter;
        this.physicalAddres = physicalAddres;
        this.isInRealMemory = isInRealMemory;
        this.used = false;
    }

    public int getId() {
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
