package tec.ic660.pagination.domain.valueObjects;


public class PTR {
    private int id;
    private int pid;
    private int initialMemory;
    private static int counter = 0;

    public PTR(int pid) {
        this.id = ++counter;
        this.pid = pid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    @Override
    public String toString() {
        return "PTR [id=" + id + ", pid=" + pid + ", initialMemory=" + initialMemory + "]";
    }

    public int getInitialMemory() {
        return initialMemory;
    }

    public void setInitialMemory(int initialMemory) {
        this.initialMemory = initialMemory;
    }

    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        PTR.counter = counter;
    }

    

    
}