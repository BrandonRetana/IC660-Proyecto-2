package tec.ic660.pagination.domain.algorithms;

import tec.ic660.pagination.domain.entity.memory.PageEntity;
import java.util.List;
import java.util.Random;

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
    public void handlePageFault(List<PageEntity> realMemory, List<PageEntity> virtualMemory, PageEntity newPage) {
            int randomIndex = random.nextInt(realMemory.size());
            PageEntity pageToEvict = realMemory.get(randomIndex);
            movePageToVirtualMemory(virtualMemory, pageToEvict);
            realMemory.set(randomIndex, newPage);  
    }
}
