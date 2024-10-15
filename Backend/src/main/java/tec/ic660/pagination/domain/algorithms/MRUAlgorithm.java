package tec.ic660.pagination.domain.algorithms;

import tec.ic660.pagination.domain.entity.memory.PageEntity;
import java.util.List;
import java.util.Stack;

public class MRUAlgorithm extends PagingAlgorithm {

    // Usamos un Stack para rastrear las páginas recientemente usadas
    private Stack<PageEntity> mruStack;

    public MRUAlgorithm() {
        this.mruStack = new Stack<>();
    }
    public void pushPageToTop(PageEntity page) {
        if (mruStack.contains(page)) {
            mruStack.remove(page);
        }
        mruStack.push(page); 
    }
    @Override
    public void addPageToAlgorithmStructure(PageEntity page) {
        mruStack.add(page);
    }

    @Override
    public void removePageFromAlgorithmStructure(PageEntity page) {
        mruStack.remove(page); 
    }

    @Override
    public void handlePageFault(List<PageEntity> realMemory, List<PageEntity> virtualMemory, PageEntity page, Integer numberOfMemoryPages) {
        if (numberOfMemoryPages == 100) {
            PageEntity pageToEvict = mruStack.pop();
            int freeFrame = pageToEvict.getPhysicalAddres();
            movePageToVirtualMemory(virtualMemory, pageToEvict); 
            realMemory.set(freeFrame, page);
            page.setPhysicalAddres(freeFrame);
        }else{
            movePageToRealMemory(realMemory, page);
        }
        mruStack.push(page); 
    }

}
