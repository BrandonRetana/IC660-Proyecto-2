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
        mruStack.remove(page); // Remover la página de la pila
    }

    @Override
    public void handlePageFault(List<PageEntity> realMemory, List<PageEntity> virtualMemory, PageEntity newPage) {
        if (mruStack.size() == realMemory.size()) {
            // Se saca la página más recientemente usada, que está en la cima de la pila
            PageEntity pageToEvict = mruStack.pop();
            movePageToVirtualMemory(virtualMemory, pageToEvict); // Mover la página a la memoria virtual
        }

        // Añadir la nueva página a la memoria real
        movePageToRealMemory(realMemory, newPage);
        mruStack.push(newPage); // Añadir la nueva página al tope de la pila
    }

}
