package tec.ic660.pagination.domain.entity.memory;

public class PageEntity {
    private final int id;
    private int physicalAddres;
    private boolean isInRealMemory;
    private boolean referenceBit ;
    private int ptrId;
    private static int counter = 0;
    
    public PageEntity(int physicalAddres, boolean isInRealMemory, int ptrId) {
        this.id = ++counter;
        this.physicalAddres = physicalAddres;
        this.isInRealMemory = isInRealMemory;
        this.referenceBit = false;
        this.ptrId = ptrId;
    }

    public int getId() {
        return id;
    }

    public int getPhysicalAddres() {
        return physicalAddres;
    }
    public void setPhysicalAddres(int physicalAddres) {
        this.physicalAddres = physicalAddres;
    }
    public boolean isInRealMemory() {
        return isInRealMemory;
    }

    public void setInRealMemory(boolean isInRealMemory) {
        this.isInRealMemory = isInRealMemory;
    }

    public boolean getReferenceBit() {
        return referenceBit;
    }

    public void setReferenceBit(boolean referenceBit) {
        this.referenceBit = referenceBit;
    }
    public int getPtrId() {
        return ptrId;
    }
    public void setPtrId(int ptrId) {
        this.ptrId = ptrId;
    }

    public void setPhysicalAddress(int physicalAddres) {
        this.physicalAddres = physicalAddres;
    }
}
