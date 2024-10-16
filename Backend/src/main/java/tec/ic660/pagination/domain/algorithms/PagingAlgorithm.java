package tec.ic660.pagination.domain.algorithms;

import tec.ic660.pagination.domain.entity.memory.PageEntity;
import java.util.List;

public abstract class PagingAlgorithm {
    
    public abstract void handlePageFault(List<PageEntity> realMemory, List<PageEntity> virtualMemory, PageEntity page, Integer pagesInMemory);
    public abstract void addPageToAlgorithmStructure(PageEntity page);
    public abstract void removePageFromAlgorithmStructure(PageEntity page);

    protected void movePageToRealMemory(List<PageEntity> realMemory, List<PageEntity> virtualMemory, PageEntity page) {
        for (int i = 0; i < 100; i++) {
            if (realMemory.get(i) == null) {
                realMemory.set(i, page);
                page.setInRealMemory(true);
                page.setPhysicalAddress(i);
                virtualMemory.remove(page);
                return;
            }
        }
    }
    protected void movePageToVirtualMemory(List<PageEntity> virtualMemory, List<PageEntity> realMemory, PageEntity page) {
        virtualMemory.add(page);
        page.setInRealMemory(false);
        page.setPhysicalAddress(100+virtualMemory.size());
        removePageFromAlgorithmStructure(page);
        realMemory.remove(page);
    }
}
