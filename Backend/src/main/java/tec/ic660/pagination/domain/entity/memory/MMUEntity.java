package tec.ic660.pagination.domain.entity.memory;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import tec.ic660.pagination.domain.valueObjects.PTR;
import tec.ic660.pagination.domain.algorithms.*;

@Component
public class MMUEntity {
    private final int PAGE_SIZE = 4096;
    private final List<PageEntity> realMemory;
    private final List<PageEntity> virtualMemory;
    private final Map<PTR, List<PageEntity>> memoryMap;
    private PagingAlgorithm pagingAlgorithm;

    public MMUEntity() {
        this.realMemory = new ArrayList<>(100);
        this.virtualMemory = new ArrayList<>();
        this.memoryMap = new Hashtable<>();
        this.pagingAlgorithm = new FIFOAlgorithm(); 
    }

    private int calculatePages(int size) {
        int result = size / PAGE_SIZE;
        if (size % PAGE_SIZE != 0) {
            result++;
        }
        return result;
    }

    public PTR newMemory(int pid, int size) {
        int requiredPages = calculatePages(size);
        PTR ptr = new PTR(pid);
        List<PageEntity> pages = new ArrayList<>();
        for (int i = 0; i < realMemory.size() && requiredPages > 0; i++) {
            if (realMemory.get(i) == null) {
                PageEntity page = new PageEntity(i, true);
                realMemory.set(i, page);
                pagingAlgorithm.addPageToAlgorithmStructure(page);
                pages.add(page);
                requiredPages--;
            }
        }
        if (requiredPages > 0) {
            for (int i = 0; i < requiredPages; i++) {
                PageEntity page = new PageEntity(i, true);
                pagingAlgorithm.handlePageFault(this.realMemory, this.virtualMemory, page);
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
                pagingAlgorithm.handlePageFault(this.realMemory, this.virtualMemory, page);
            }
        }
    }

    public void deleteMemory(PTR ptr) {
        List<PageEntity> pages = memoryMap.get(ptr);
        for (PageEntity page : pages) {
            if (page.isInRealMemory()) {
                realMemory.remove(page);
                pagingAlgorithm.removePageFromAlgorithmStructure(page);
                continue;
            }
            virtualMemory.remove(page);
        }
        memoryMap.remove(ptr);
    }

    public void killProcessMemory(int pid) {
        Iterator<Map.Entry<PTR, List<PageEntity>>> iterator = memoryMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<PTR, List<PageEntity>> entry = iterator.next();
            if (entry.getKey().getPid() == pid) {
                deleteMemory(entry.getKey());
                iterator.remove();
            }
        }
    }


}
