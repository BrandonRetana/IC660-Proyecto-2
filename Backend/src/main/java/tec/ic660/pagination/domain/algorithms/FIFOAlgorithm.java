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
        PageEntity pageToEvict;
        int freeFrame;
        try {
        pageToEvict = fifoQueue.poll();
        freeFrame = realMemory.indexOf(pageToEvict);
        realMemory.set(freeFrame, page);
        movePageToVirtualMemory(virtualMemory, realMemory, pageToEvict);
        page.setPhysicalAddres(freeFrame);
        page.setInRealMemory(true);
        System.out.println(fifoQueue.size()+ "fifoQueue size" + pageToEvict);
    } catch (Exception e) {
        System.out.println(e);
    }
    }
}
