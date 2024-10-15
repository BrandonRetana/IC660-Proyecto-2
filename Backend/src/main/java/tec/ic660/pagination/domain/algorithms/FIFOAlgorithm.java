package tec.ic660.pagination.domain.algorithms;

import tec.ic660.pagination.domain.entity.memory.PageEntity;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class FIFOAlgorithm extends PagingAlgorithm {

    private Queue<PageEntity> fifoQueue;

    public FIFOAlgorithm() {
        this.fifoQueue = new LinkedList<>();
    }

    @Override
    public void addPageToAlgorithmStructure(PageEntity page) {
        fifoQueue.add(page);
    }
    @Override
    public void removePageFromAlgorithmStructure(PageEntity page) {
        fifoQueue.remove(page);
    }
    @Override
    public void handlePageFault(List<PageEntity> realMemory, List<PageEntity> virtualMemory, PageEntity page, Integer numberOfMemoryPages) {
        if (numberOfMemoryPages == 100) {
            PageEntity pageToEvict = fifoQueue.poll();
            int freeFrame = pageToEvict.getPhysicalAddres();
            movePageToVirtualMemory(virtualMemory, pageToEvict);
            realMemory.set(freeFrame, page);
            page.setPhysicalAddres(freeFrame);
        }else{
            movePageToRealMemory(realMemory, page);
        }
        fifoQueue.add(page);
    }
}
