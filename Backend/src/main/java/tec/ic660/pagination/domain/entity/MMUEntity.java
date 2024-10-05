package tec.ic660.pagination.domain.entity;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.UUID;

public class MMUEntity {
    private final int PAGE_SIZE = 4096;
    private final List<PageEntity> realMemory;
    private int availablePages;
    private final List<PageEntity> virtualMemory;
    private final Dictionary<UUID, List<PageEntity>> memoryMap;

    public MMUEntity() {
        this.realMemory = new ArrayList<>();
        this.virtualMemory = new ArrayList<>();
        this.memoryMap = new Hashtable<>();
        this.availablePages = 100;
        this.initializePages();
    }

    private void initializePages() {
        for (int i = 0; i < 100; i++) {
            PageEntity pageEntity = new PageEntity(i, false);
            this.virtualMemory.add(pageEntity);
        }
    }

    private int calculatePages(int size) {
        int result = size / PAGE_SIZE;
        if (size % PAGE_SIZE != 0) {
            result++;
        }
        return result;
    }

    private UUID requestPages(int numberOfPages) {
        UUID ptr = UUID.randomUUID();
        List<PageEntity> pages = new ArrayList<>();
        for (PageEntity pageEntity : realMemory) {
            if (!pageEntity.isInUse()) {
                pageEntity.setInUse(true);
                pages.add(pageEntity);
            }
        }
        this.memoryMap.put(ptr, pages);
        return ptr;
    }

    public UUID newMemory(UUID pid, int size) {
        int requiredPages = calculatePages(size);
        if (availablePages < requiredPages) {
            // TODO: aqui va el algoritmo de paginacion
            return null;
        }
        UUID ptr = requestPages(requiredPages);
        return ptr;
    }

    public void useMemory(UUID ptr) {

    }

    public void deleteMemory(UUID ptr) {

    }

    public void killProcces(UUID pid) {

    }

}
