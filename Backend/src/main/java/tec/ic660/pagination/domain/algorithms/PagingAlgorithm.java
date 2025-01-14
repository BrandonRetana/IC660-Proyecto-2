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
                page.setInRealMemory(true);
                page.setPhysicalAddres(i);
                realMemory.set(i, page);
                addPageToAlgorithmStructure(page);
                return;
            }
        }
        System.out.println("Aqui no debe de caer, estoy en move to realMemory");
    }


    public void movePageToVirtualMemory(List<PageEntity> virtualMemory, List<PageEntity> realMemory, PageEntity page) {
        realMemory.set(page.getPhysicalAddres(), null);
        page.setInRealMemory(false);
        page.setPhysicalAddres(100+page.getId());
        virtualMemory.add(page);
    }
}
