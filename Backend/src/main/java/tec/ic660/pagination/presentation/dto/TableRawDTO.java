package tec.ic660.pagination.presentation.dto;


public class TableRawDTO {
    private int pageId;
    private int pid;
    private String loaded;
    private int lAddr;
    private int mAddr;
    private int dAddr;
    private String loadedT;
    private String mark;

    public TableRawDTO() {}

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getLoaded() {
        return loaded;
    }

    public void setLoaded(String loaded) {
        this.loaded = loaded;
    }

    public int getLAddr() {
        return lAddr;
    }

    public void setLAddr(int lAddr) {
        this.lAddr = lAddr;
    }

    public int getMAddr() {
        return mAddr;
    }

    public void setMAddr(int mAddr) {
        this.mAddr = mAddr;
    }

    public int getDAddr() {
        return dAddr;
    }

    public void setDAddr(int dAddr) {
        this.dAddr = dAddr;
    }

    public String getLoadedT() {
        return loadedT;
    }

    public void setLoadedT(String loadedT) {
        this.loadedT = loadedT;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}
