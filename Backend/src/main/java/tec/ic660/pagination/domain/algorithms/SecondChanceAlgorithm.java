package tec.ic660.pagination.domain.algorithms;

import tec.ic660.pagination.domain.entity.memory.PageEntity;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SecondChanceAlgorithm extends PagingAlgorithm {

    private Queue<PageEntity> secondChanceQueue;

    public SecondChanceAlgorithm() {
        this.secondChanceQueue = new LinkedList<>();
    }
    
    @Override
    public void addPageToAlgorithmStructure(PageEntity page) {
        secondChanceQueue.add(page);
    }

    @Override
    public void removePageFromAlgorithmStructure(PageEntity page) {
        secondChanceQueue.remove(page);
    }

    @Override
    public void handlePageFault(List<PageEntity> realMemory, List<PageEntity> virtualMemory, PageEntity newPage) {
        while (true) {
            PageEntity page = secondChanceQueue.poll(); // Quitamos la página de la cola
            
            if (page.getReferenceBit()) { //si la pagina se usó
                page.setReferenceBit(false); // Se resetea el bit de uso
                secondChanceQueue.add(page); // Reinsertamos la página al final de la cola
            } else {
                movePageToVirtualMemory(virtualMemory, page);
                break; // Salimos del bucle, ya que encontramos la página a reemplazar
            }
        }
        movePageToRealMemory(realMemory, newPage);
        secondChanceQueue.add(newPage); // Añadimos la nueva página a la cola
    }
}
