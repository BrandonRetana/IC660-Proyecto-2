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
    public void handlePageFault(List<PageEntity> realMemory, List<PageEntity> virtualMemory, PageEntity page, Integer pagesInMemory ) {
        if (pagesInMemory == 100) {
            PageEntity pageToEvict = realMemory.get(random.nextInt(realMemory.size()));
            int freeFrame = pageToEvict.getPhysicalAddres();
            movePageToVirtualMemory(virtualMemory, realMemory, pageToEvict);   
            realMemory.set(freeFrame, page);  
            page.setPhysicalAddres(freeFrame);
        }else{
            movePageToRealMemory(realMemory, virtualMemory ,page);
        }
    }
}
