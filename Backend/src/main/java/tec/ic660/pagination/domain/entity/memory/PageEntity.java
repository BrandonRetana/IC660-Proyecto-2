package tec.ic660.pagination.domain.entity.memory;

public class PageEntity {
    private final int id;
    private int physicalAddres;
    private boolean isInRealMemory;
    private int timeStamp;
    private boolean referenceBit;
    private int ptrId;
    private int usedSpace;
    private int LoadedTime;
    private static int counter = -1;
    
    public PageEntity(int physicalAddres, boolean isInRealMemory, int ptrId, int TimeStamp, int usedSpace) {
        this.id = ++counter;
        this.physicalAddres = physicalAddres;
        this.isInRealMemory = isInRealMemory;
        this.timeStamp = TimeStamp;
        this.referenceBit = false;
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


    public boolean isMarked(){
        return referenceBit;
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

    

    @Override
    public String toString() {
        return "PageEntity [id=" + id + ", physicalAddres=" + physicalAddres + ", isInRealMemory=" + isInRealMemory
                + ", referenceBit=" + referenceBit + ", ptrId=" + ptrId + ", LoadedTime=" + LoadedTime + ", timeStamp="
                + timeStamp + "]";
    }


    public int getTimeStamp() {
        return timeStamp;
    }


    public void setTimeStamp(int timeStamp) {
        this.timeStamp = timeStamp;
    }

    
    

}
