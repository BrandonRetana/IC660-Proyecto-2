package tec.ic660.pagination.domain.entity.memory;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import tec.ic660.pagination.domain.algorithms.PagingAlgorithm;
import tec.ic660.pagination.domain.valueObjects.PTR;
import tec.ic660.pagination.domain.valueObjects.RealMemory;

public class MMUEntity {
    private final int PAGE_SIZE = 4096;
    private final int MAX_MEMORY_PAGES = 100;
    private final RealMemory<PageEntity> realMemory;
    private final List<PageEntity> virtualMemory;
    private final Map<PTR, List<PageEntity>> memoryMap;
    private PagingAlgorithm pagingAlgorithm;
    private Integer simulationTime;
    private Integer TrashingTime;

    public MMUEntity() {
        this.realMemory = new RealMemory<>(100);
        this.virtualMemory = new ArrayList<>();
        this.memoryMap = new Hashtable<>();
        this.simulationTime = 0;
        this.TrashingTime = 0;
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
        int pageTimeCounter = 0;
        int remainingSize = size;
        int usedSpace;
        // Nuevas paginas y memoria vacia
        for (int i = 0; i < MAX_MEMORY_PAGES && requiredPages > 0; i++) {
            if (this.realMemory.get(i) == null) {
                // Page
                usedSpace = Math.min(remainingSize, this.PAGE_SIZE);
                PageEntity nerwPageEntity = new PageEntity(i, true, ptr.getId(), simulationTime, usedSpace);
                nerwPageEntity.setLoadedTime(pageTimeCounter);
                pages.add(nerwPageEntity);
                realMemory.set(i, nerwPageEntity);
                pagingAlgorithm.addPageToAlgorithmStructure(nerwPageEntity);
                // Metrics
                remainingSize -= usedSpace;
                this.simulationTime += 1;
                pageTimeCounter += 1;
                requiredPages--;
            }
        }
        // Nuevas paginas, memoria llena
        if (requiredPages > 0) {
            for (int j = 0; j < requiredPages; j++) {
                // Page
                usedSpace = Math.min(remainingSize, this.PAGE_SIZE);
                PageEntity newPageEntity = new PageEntity(-1000, true, ptr.getId(), simulationTime, usedSpace);
                pagingAlgorithm.handlePageFault(this.realMemory, this.virtualMemory, newPageEntity);
                newPageEntity.setLoadedTime(pageTimeCounter);
                pages.add(newPageEntity);
                // Metrics
                remainingSize -= usedSpace;
                this.simulationTime += 5;
                this.TrashingTime += 5;
                pageTimeCounter += 5;
            }
        }

        this.memoryMap.put(ptr, pages);
        return ptr;
    }

    public void useMemory(PTR ptr) {
        List<PageEntity> pages = memoryMap.get(ptr);
        Integer pageTimeCounter = 0;
        for (PageEntity pageEntity : pages) {
            // Hit
            if (pageEntity.isInRealMemory()) {
                pageEntity.setLoadedTime(pageTimeCounter);
                pageEntity.setTimeStamp(this.simulationTime);
                pageEntity.setReferenceBit(1);
        
                this.simulationTime += 1;
                pageTimeCounter += 1;
            }

            // Fault y memoria suficiente
            if (!pageEntity.isInRealMemory() && this.realMemory.getNumberOfPages() < this.MAX_MEMORY_PAGES) {
                for (int i = 0; i < this.MAX_MEMORY_PAGES; i++) {
                    if (realMemory.get(i) == null) {
                        virtualMemory.remove(pageEntity);
                        pageEntity.setInRealMemory(true);
                        pageEntity.setPhysicalAddres(i);
                        pageEntity.setLoadedTime(pageTimeCounter);
                        pageEntity.setTimeStamp(this.simulationTime);
                        pageEntity.setReferenceBit(1);
                       
                        realMemory.set(i, pageEntity);
                        this.pagingAlgorithm.addPageToAlgorithmStructure(pageEntity);
                        this.simulationTime += 5;
                        this.TrashingTime += 5;
                        pageTimeCounter += 5;
                        break;
                    }
                }
            }
            // Fault y memoria llena
           else if (!pageEntity.isInRealMemory()) {
                this.pagingAlgorithm.handlePageFault(this.realMemory, this.virtualMemory, pageEntity);
                pageEntity.setLoadedTime(pageTimeCounter);
                pageEntity.setTimeStamp(this.simulationTime);
                pageEntity.setReferenceBit(1);
                
                this.simulationTime += 5;
                this.TrashingTime += 5;
                pageTimeCounter += 5;
            }

        }
    }

    public void deleteMemory(PTR ptr) {
        List<PageEntity> pages = memoryMap.get(ptr);
        for (PageEntity pageEntity: pages) {
            if (pageEntity.isInRealMemory()) {
                realMemory.set(pageEntity.getPhysicalAddres(), null);
                pagingAlgorithm.removePageFromAlgorithmStructure(pageEntity);
            }
            else{
                virtualMemory.remove(pageEntity);
            }
        }
        memoryMap.remove(ptr);
    }

    public void killProcessMemory(int pid) {
         Set<PTR> keysSet = memoryMap.keySet();
         List<PTR> ptrs = new ArrayList<>(keysSet);
         for (PTR ptr : ptrs) {
            if (ptr.getPid() == pid) {
                deleteMemory(ptr);
            }
         }
    }

    public int getMemoryFragmentation() {
        int totalFragmentation = 0;
        for (PageEntity page : realMemory) {
            if (page != null) {
                int usedSpace = page.getUsedSpace();
                int fragmentationInPage = PAGE_SIZE - usedSpace;
                if (fragmentationInPage > 0) {
                    totalFragmentation += fragmentationInPage;
                }
            }
        }
        return totalFragmentation / 1024;
    }

    public RealMemory<PageEntity> getRealMemory() {
        return realMemory;
    }

    public List<PageEntity> getVirtualMemory() {
        return virtualMemory;
    }

    public void setPagingAlgorithm(PagingAlgorithm algorithm) {
        this.pagingAlgorithm = algorithm;
    }
    
    public Integer getTrashingTime() {
        return TrashingTime;
    }

    public Integer getSimulationTime() {
        return simulationTime;
    }

    public PagingAlgorithm getPagingAlgorithm() {
        return pagingAlgorithm;
    }



    

}
