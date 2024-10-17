package tec.ic660.pagination.domain.algorithms;

import java.util.List;

import tec.ic660.pagination.domain.entity.memory.PageEntity;

public abstract class PagingAlgorithm {
    
    public abstract void handlePageFault(List<PageEntity> realMemory, List<PageEntity> virtualMemory, PageEntity page);
    public abstract void addPageToAlgorithmStructure(PageEntity page);
    public abstract void removePageFromAlgorithmStructure(PageEntity page);

    public void movePageToRealMemory(List<PageEntity> realMemory, List<PageEntity> virtualMemory, PageEntity page) {
        virtualMemory.remove(page);
        for (int i = 0; i < 100; i++) {
            if (realMemory.get(i) == null) {
                realMemory.set(i, page);
                page.setInRealMemory(true);
                page.setPhysicalAddress(i);
                addPageToAlgorithmStructure(page);
                return;
            }
        }
        System.out.println("Aqui no debe de caer, estoy en move to realMemory");
    }


    public void movePageToVirtualMemory(List<PageEntity> virtualMemory, List<PageEntity> realMemory, PageEntity page) {
        realMemory.set(page.getPhysicalAddres(), null);
        removePageFromAlgorithmStructure(page);
        page.setInRealMemory(false);
        page.setPhysicalAddress(100+page.getId());
        virtualMemory.add(page);
    }
}
