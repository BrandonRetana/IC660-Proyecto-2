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
    public void handlePageFault(List<PageEntity> realMemory, List<PageEntity> virtualMemory, PageEntity page) {
        if (fifoQueue.size() == realMemory.size()) {
            PageEntity pageToEvict = fifoQueue.poll();
            movePageToVirtualMemory(virtualMemory, pageToEvict);
        }
        movePageToRealMemory(realMemory, page);
        fifoQueue.add(page);
    }
}
