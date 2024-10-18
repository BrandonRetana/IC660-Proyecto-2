package tec.ic660.pagination.domain.algorithms;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import tec.ic660.pagination.domain.entity.memory.PageEntity;

public class RandomAlgorithm extends PagingAlgorithm {

    private LinkedList<PageEntity> randomList;
    private Random random;
    
    public RandomAlgorithm() {
        this.random = new Random();
        this.randomList = new LinkedList<>();
    }

    @Override
    public void addPageToAlgorithmStructure(PageEntity page) {
        randomList.add(page); 
    }

    @Override
    public void removePageFromAlgorithmStructure(PageEntity page) {
        randomList.remove(page); 
    }

    @Override
    public void handlePageFault(List<PageEntity> realMemory, List<PageEntity> virtualMemory, PageEntity page) {
        PageEntity pageToEvict = randomList.remove(random.nextInt(randomList.size()));
        movePageToVirtualMemory(virtualMemory, realMemory, pageToEvict);
        movePageToRealMemory(realMemory, virtualMemory, page);
    }
}
