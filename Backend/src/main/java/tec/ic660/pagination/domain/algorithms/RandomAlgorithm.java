package tec.ic660.pagination.domain.algorithms;

import java.util.List;
import java.util.Random;

import tec.ic660.pagination.domain.entity.memory.PageEntity;

public class RandomAlgorithm extends PagingAlgorithm {

    private Random random;

    public RandomAlgorithm() {
        this.random = new Random();
    }

    @Override
    public void addPageToAlgorithmStructure(PageEntity page) {
        return;
    }

    @Override
    public void removePageFromAlgorithmStructure(PageEntity page) {
        return;
    }

    @Override
    public void handlePageFault(List<PageEntity> realMemory, List<PageEntity> virtualMemory, PageEntity page) {
        PageEntity pageToEvict = realMemory.get(random.nextInt(realMemory.size()));
        int freeFrame = pageToEvict.getPhysicalAddres();
        movePageToVirtualMemory(virtualMemory, realMemory, pageToEvict);   
        realMemory.set(freeFrame, page);  
        page.setPhysicalAddres(freeFrame);
    }
}
