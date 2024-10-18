package tec.ic660.pagination.domain.algorithms;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import tec.ic660.pagination.domain.entity.memory.PageEntity;

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
    public void handlePageFault(List<PageEntity> realMemory, List<PageEntity> virtualMemory, PageEntity page) {   
            while (true) {
                PageEntity pageToEvict = secondChanceQueue.poll();     
                if (pageToEvict.getReferenceBit()) { 
                    pageToEvict.setReferenceBit(false); 
                    secondChanceQueue.add(pageToEvict); 
                } else {
                    movePageToRealMemory(realMemory, virtualMemory, page);
                    movePageToVirtualMemory(virtualMemory, realMemory, pageToEvict);
                    break; 
                }
            }    
    }
}
