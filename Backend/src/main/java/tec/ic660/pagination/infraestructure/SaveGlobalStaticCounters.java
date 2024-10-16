package tec.ic660.pagination.infraestructure;

public class SaveGlobalStaticCounters {
    private Integer PTRCounter;
    private Integer PageCounter;

    public SaveGlobalStaticCounters() {
    }

    public SaveGlobalStaticCounters(Integer pTRCounter, Integer pageCounter) {
        PTRCounter = pTRCounter;
        PageCounter = pageCounter;
    }

    public Integer getPTRCounter() {
        return PTRCounter;
    }

    public void setPTRCounter(Integer pTRCounter) {
        PTRCounter = pTRCounter;
    }

    public Integer getPageCounter() {
        return PageCounter;
    }

    public void setPageCounter(Integer pageCounter) {
        PageCounter = pageCounter;
    }
    
    

}
