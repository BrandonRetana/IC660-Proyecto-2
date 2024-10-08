package tec.ic660.pagination.domain.entity;

import java.util.UUID;

public class PTR {
    private UUID id;
    private UUID pid;

    public PTR(UUID pid) {
        this.id = UUID.randomUUID();
        this.pid = pid;
    }
    public UUID getId() { 
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public UUID getPid() {
        return pid;
    }
    public void setPid(UUID pid) {
        this.pid = pid;
    }  
}