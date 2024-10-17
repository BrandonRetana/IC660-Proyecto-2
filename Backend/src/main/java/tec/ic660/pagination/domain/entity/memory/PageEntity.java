package tec.ic660.pagination.domain.entity.memory;

public class PageEntity {
    private final int id;
    private int physicalAddres;
    private boolean isInRealMemory;
    private int timeStamp;
    private Integer referenceBit;
    private int ptrId;
    private int usedSpace;
    private int LoadedTime;
    private static int counter = -1;
    
    public PageEntity(int physicalAddres, boolean isInRealMemory, int ptrId, int TimeStamp, int usedSpace) {
        this.id = ++counter;
        this.physicalAddres = physicalAddres;
        this.isInRealMemory = isInRealMemory;
        this.timeStamp = TimeStamp;
        this.referenceBit = -1;
        this.ptrId = ptrId;
        this.usedSpace = usedSpace;
        this.LoadedTime = 0;
     
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
        if (referenceBit == 1) {
            return true;
        }
        return false;
    }

    public void setReferenceBit(boolean referenceBit) {
        if (referenceBit) {
            this.referenceBit = 1;
            return;
        }
        this.referenceBit = 0;

    }
    public int getPtrId() {
        return ptrId;
    }
    public void setPtrId(int ptrId) {
        this.ptrId = ptrId;
    }

    public int getLoadedTime() {
        return LoadedTime;
    }   
    public void setLoadedTime(int LoadedTime) {
        this.LoadedTime = LoadedTime;
    }

    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        PageEntity.counter = counter;
    }

    public int getTimeStarted() {
        return timeStamp;
    }
    public void setTimeStarted(int timeStamp) {
        this.timeStamp = timeStamp;
    }
    public int getUsedSpace() {
        return usedSpace;
    }
    public void setUsedSpace(int usedSpace) {
        this.usedSpace = usedSpace;
    }

    public int getTimeStamp() {
        return timeStamp;
    }


    public void setTimeStamp(int timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getMark(){
        if (referenceBit == -1) {
            return String.valueOf(this.timeStamp);
        }
        return String.valueOf(this.referenceBit);
    }

    

    @Override
    public String toString() {
        return "PageEntity [id=" + id + ", physicalAddres=" + physicalAddres + ", isInRealMemory=" + isInRealMemory
                + ", referenceBit=" + referenceBit + ", ptrId=" + ptrId + ", LoadedTime=" + LoadedTime + ", timeStamp="
                + timeStamp + "]";
    }

}
