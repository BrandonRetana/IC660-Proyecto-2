package tec.ic660.pagination.domain.algorithms;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import tec.ic660.pagination.domain.entity.memory.PageEntity;

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
    public void handlePageFault(List<PageEntity> realMemory, List<PageEntity> virtualMemory, PageEntity page) {
        System.out.println(fifoQueue.size()+ "fifoQueue size");
        PageEntity pageToEvict = fifoQueue.poll();
        int freeFrame = pageToEvict.getPhysicalAddres();
        movePageToVirtualMemory(virtualMemory, realMemory, pageToEvict);
        realMemory.add(freeFrame, page);
        page.setPhysicalAddres(freeFrame);
        page.setInRealMemory(true);
    }
}
