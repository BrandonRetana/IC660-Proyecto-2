package tec.ic660.pagination.domain.algorithms;

import tec.ic660.pagination.domain.entity.memory.PageEntity;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SecondChanceAlgorithm extends PagingAlgorithm {

    private Queue<PageEntity> secondChanceQueue;

    public SecondChanceAlgorithm() {
        this.secondChanceQueue = new LinkedList<>();
    }
    
    @Override
    public void addPageToAlgorithmStructure(PageEntity page) {
        secondChanceQueue.add(page);
    }

    @Override
    public void removePageFromAlgorithmStructure(PageEntity page) {
        secondChanceQueue.remove(page);
    }

    @Override
    public void handlePageFault(List<PageEntity> realMemory, List<PageEntity> virtualMemory, PageEntity page, Integer pagesInMemory) {
        if (pagesInMemory == 100) {     
            while (true) {
                PageEntity pageToEvict = secondChanceQueue.poll();     
                if (pageToEvict.getReferenceBit()) { 
                    pageToEvict.setReferenceBit(false); 
                    secondChanceQueue.add(pageToEvict); 
                } else {
                    int freeFrame = pageToEvict.getPhysicalAddres();
                    realMemory.set(freeFrame, pageToEvict);
                    page.setPhysicalAddres(freeFrame);
                    movePageToVirtualMemory(virtualMemory, realMemory, pageToEvict);
                    break; 
                }
            }
        }else{
            movePageToRealMemory(realMemory, virtualMemory, page);
        }
        secondChanceQueue.add(page);      
    }
}
