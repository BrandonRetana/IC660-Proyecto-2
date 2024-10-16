package tec.ic660.pagination.domain.entity.memory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import tec.ic660.pagination.domain.algorithms.FIFOAlgorithm;
import tec.ic660.pagination.domain.algorithms.MRUAlgorithm;
import tec.ic660.pagination.domain.algorithms.PagingAlgorithm;
import tec.ic660.pagination.domain.algorithms.SecondChanceAlgorithm;
import tec.ic660.pagination.domain.valueObjects.PTR;

@Component
public class MMUEntity {
    private final int PAGE_SIZE = 4096;
    private final int MAX_MEMORY_PAGES = 100;
    private final List<PageEntity> realMemory;
    private final List<PageEntity> virtualMemory;
    private final Map<PTR, List<PageEntity>> memoryMap;
    private PagingAlgorithm pagingAlgorithm;
    private Integer pagesInMemory;
    private Integer simulationTime;
    private Integer TrashingTime;


    public MMUEntity() {
        this.realMemory = new ArrayList<>(Collections.nCopies(100, null));
        this.virtualMemory = new ArrayList<>();
        this.memoryMap = new Hashtable<>();
        this.pagingAlgorithm = new FIFOAlgorithm();
        this.pagesInMemory = 0;
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
    
        for (int i = 0; i < MAX_MEMORY_PAGES && requiredPages > 0; i++) {
            if (realMemory.get(i) == null) {
                usedSpace = Math.min(remainingSize, PAGE_SIZE);
                PageEntity page = new PageEntity(i, true, pid, simulationTime, usedSpace);
                realMemory.set(i, page);
                pagingAlgorithm.addPageToAlgorithmStructure(page);
                pages.add(page);
                page.setLoadedTime(pageTimeCounter);
                remainingSize -= usedSpace;  
                requiredPages--;
                pagesInMemory++;
                simulationTime += 1;
                pageTimeCounter += 1;
            }
        }
        if (requiredPages > 0) {
            for (int i = 0; i < requiredPages; i++) {
                usedSpace = Math.min(remainingSize, PAGE_SIZE);
                PageEntity page = new PageEntity(i, true, pid, simulationTime, usedSpace);
                pagingAlgorithm.handlePageFault(this.realMemory, this.virtualMemory, page, pagesInMemory);
                page.setLoadedTime(pageTimeCounter);
                remainingSize -= usedSpace;
                simulationTime += 5;
                TrashingTime += 5;
                pageTimeCounter += 5;
            }
        }
        
        memoryMap.put(ptr, pages);
        return ptr;
    }
    

    public void useMemory(PTR ptr) {
        System.out.println(ptr);
        List<PageEntity> pages = memoryMap.get(ptr);
        if (pages == null) {
            System.out.println("Puntero no encontrado.");
            return;
        }

        int pageTimeCounter = 0;

        for (PageEntity page : pages) {
            if (!page.isInRealMemory() ){
                pagingAlgorithm.handlePageFault(this.realMemory, this.virtualMemory, page, pagesInMemory);
                page.setLoadedTime(pageTimeCounter);
                page.setTimeStarted(simulationTime);    
                simulationTime+=5;
                TrashingTime+=5;
                pageTimeCounter+=5;
            }
            else{
                page.setLoadedTime(pageTimeCounter);
                simulationTime+=1; 
                pageTimeCounter+=1;
            }
            if (pagingAlgorithm instanceof SecondChanceAlgorithm) {
                page.setReferenceBit(true);
            }
            if (pagingAlgorithm instanceof MRUAlgorithm) { 
                ((MRUAlgorithm) pagingAlgorithm).pushPageToTop(page);
            }
        }

    }

    public void deleteMemory(PTR ptr) {
        List<PageEntity> pages = memoryMap.get(ptr);
        for (PageEntity page : pages) {
            if (page.isInRealMemory()) {
                Integer pageIndex = realMemory.indexOf(page);
                this.realMemory.add(pageIndex, null);
                pagesInMemory--;
                pagingAlgorithm.removePageFromAlgorithmStructure(page);
            }
            else{
            System.out.println("Hola :)");
            System.out.println(this.virtualMemory.remove(page));
            }
        }
        memoryMap.remove(ptr);
    }

    private void deleteMemory(PTR ptr, Boolean kill) {
        List<PageEntity> pages = memoryMap.get(ptr);
        for (PageEntity page : pages) {
            if (page.isInRealMemory()) {
                Integer pageIndex = realMemory.indexOf(page);
                this.realMemory.add(pageIndex, null);
                pagesInMemory--;
                pagingAlgorithm.removePageFromAlgorithmStructure(page);
            }
            else{
            System.out.println(this.virtualMemory.remove(page));
            }
        }
    }

    public void killProcessMemory(int pid) {
        Iterator<Map.Entry<PTR, List<PageEntity>>> iterator = memoryMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<PTR, List<PageEntity>> entry = iterator.next();
            if (entry.getKey().getPid() == pid) {
                deleteMemory(entry.getKey(), true);
                iterator.remove(); 
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
    
    public List<PageEntity> getRealMemory() {
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

    public Integer getPagesInMemory() {
        return pagesInMemory;
    }


}
