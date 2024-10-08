package tec.ic660.pagination.domain.entity;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.UUID;

public class MMUEntity {
    private final int PAGE_SIZE = 4096;
    private final List<PageEntity> realMemory;
    private final List<PageEntity> virtualMemory;
    private final Dictionary<PTR, List<PageEntity>> memoryMap;

    public MMUEntity() {
        this.realMemory = new ArrayList<>(100);
        this.virtualMemory = new ArrayList<>();
        this.memoryMap = new Hashtable<>();
        this.initializePages();
    }

    private int calculatePages(int size) {
        int result = size / PAGE_SIZE;
        if (size % PAGE_SIZE != 0) {
            result++;
        }
        return result;
    }

    public PTR newMemory(UUID pid, int size) {
        int requiredPages = calculatePages(size);
        PTR ptr = new PTR(pid);
        List<PageEntity> pages = new ArrayList<>();
        for (int i = 0 ; i < realMemory.size() && requiredPages > 0; i++) {
            if (realMemory.get(i) == null) { 
                PageEntity page = new PageEntity(i, true);
                realMemory.set(i, page);
                pages.add(page);
                requiredPages--;
                continue;
            }
        }
        if (requiredPages > 0) {
            for (int i = 0 ; i < requiredPages > 0; i++) {
                PageEntity page = new PageEntity(i, true);
                //TODO: aqui va el algoritmo de paginacion
            }
        }
        memoryMap.put(ptr, pages);
        return ptr;
    }

     public void useMemory(PTR ptr) {
        List<PageEntity> pages = memoryMap.get(ptr);
        if (pages == null) {
            System.out.println("Puntero no encontrado.");
            return;
        }
        for (PageEntity page : pages) {
            if (!page.isInRealMemory()) {
            // TODO: aqui va el algoritmo de paginacion
            }
        }
    }

    public void deleteMemory(PTR ptr) {
        List<PageEntity> pages = memoryMap.get(ptr);
        for (PageEntity page : pages) {
            if (page.isInRealMemory()) {
                realMemory.remove(page);
                continue;
            }
            virtualMemory.remove(page);
        }
        memoryMap.remove(ptr);
    }

    public void killProcess(UUID pid) {

    }

}
