package tec.ic660.pagination.domain.algorithms;

import java.util.List;
import java.util.Stack;

import tec.ic660.pagination.domain.entity.memory.PageEntity;

public class MRUAlgorithm extends PagingAlgorithm {

    private Stack<PageEntity> mruStack;

    public MRUAlgorithm() {
        this.mruStack = new Stack<>();
    }
    @Override
    public void addPageToAlgorithmStructure(PageEntity page) {
        if (mruStack.contains(page)) {
            mruStack.remove(page);
        }
        mruStack.push(page); 
    }
    @Override
    public void removePageFromAlgorithmStructure(PageEntity page) {
        mruStack.remove(page); 
    }
    @Override
    public void handlePageFault(List<PageEntity> realMemory, List<PageEntity> virtualMemory, PageEntity page) {
        PageEntity pageToEvict = mruStack.pop();
        movePageToVirtualMemory(virtualMemory, realMemory, pageToEvict);
        movePageToRealMemory(realMemory, virtualMemory, page); 
    }

}
