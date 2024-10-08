package tec.ic660.pagination.domain.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProcessEntity {
    private UUID pid;
    private List<UUID> sybomTable;

    public ProcessEntity() {
        this.sybomTable = new ArrayList();
        this.pid = UUID.randomUUID();
    }



}
