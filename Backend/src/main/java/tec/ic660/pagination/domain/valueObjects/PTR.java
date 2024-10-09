package tec.ic660.pagination.domain.valueObjects;

import java.util.UUID;

public class PTR {
    private UUID id;
    private int pid;

    public PTR(int pid) {
        this.id = UUID.randomUUID();
        this.pid = pid;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }
}