package tec.ic660.pagination.domain.entity.memory;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import tec.ic660.pagination.domain.valueObjects.PTR;

public class MMUEntity {
    private final int PAGE_SIZE = 4096;
    private final List<PageEntity> realMemory;
    private final List<PageEntity> virtualMemory;
    private final Map<PTR, List<PageEntity>> memoryMap;

    public MMUEntity() {
        this.realMemory = new ArrayList<>(100);
        this.virtualMemory = new ArrayList<>();
        this.memoryMap = new Hashtable<>();
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
        for (int i = 0; i < realMemory.size() && requiredPages > 0; i++) {
            if (realMemory.get(i) == null) {
                PageEntity page = new PageEntity(i, true);
                realMemory.set(i, page);
                pages.add(page);
                requiredPages--;
            }
        }
        if (requiredPages > 0) {
            for (int i = 0; i < requiredPages; i++) {
                PageEntity page = new PageEntity(i, true);
                // TODO: aqui va el algoritmo de paginacion
                requiredPages--;
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
        Iterator<Map.Entry<PTR, List<PageEntity>>> iterator = memoryMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<PTR, List<PageEntity>> entry = iterator.next();
            if (entry.getKey().getPid().equals(pid)) {
                iterator.remove();
            }
        }
    }

}