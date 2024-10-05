package tec.ic660.pagination.domain.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProcessEntity {
    private UUID pId;
    private  List<UUID> sybomTable;

    public ProcessEntity() {
        this.sybomTable = new ArrayList();
        this.pId = UUID.randomUUID();
    }



}
