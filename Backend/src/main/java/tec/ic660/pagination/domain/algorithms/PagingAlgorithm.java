package tec.ic660.pagination.domain.algorithms;

import java.util.List;

import tec.ic660.pagination.domain.entity.memory.PageEntity;

public abstract class PagingAlgorithm {
    
    public abstract void handlePageFault(List<PageEntity> realMemory, List<PageEntity> virtualMemory, PageEntity page);
    public abstract void addPageToAlgorithmStructure(PageEntity page);
    public abstract void removePageFromAlgorithmStructure(PageEntity page);

    public void movePageToRealMemory(List<PageEntity> realMemory, List<PageEntity> virtualMemory, PageEntity page) {
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
    public void movePageToVirtualMemory(List<PageEntity> virtualMemory, List<PageEntity> realMemory, PageEntity page) {
        virtualMemory.add(page);
        page.setInRealMemory(false);
        page.setPhysicalAddress(100+virtualMemory.size());
        removePageFromAlgorithmStructure(page);
        realMemory.remove(page);
    }
}
