package tec.ic660.pagination.domain.algorithms;

import tec.ic660.pagination.domain.entity.memory.PageEntity;
import java.util.List;

public class OptimalAlgorithm extends PagingAlgorithm {

    public OptimalAlgorithm() {
        // Constructor del algoritmo óptimo
    }

    @Override
    public void addPageToAlgorithmStructure(PageEntity page) {
        // No es necesario un seguimiento adicional en el algoritmo óptimo
    }

    @Override
    public void removePageFromAlgorithmStructure(PageEntity page) {
        // No es necesario un seguimiento adicional en el algoritmo óptimo
    }

    @Override
    public void handlePageFault(List<PageEntity> realMemory, List<PageEntity> virtualMemory, PageEntity newPage, List<PageEntity> futurePages) {
        if (realMemory.size() < getMaxRealMemorySize()) {
            // Si hay espacio en la memoria real, simplemente agregamos la página
            movePageToRealMemory(realMemory, newPage);
        } else {
            // Si no hay espacio, seleccionamos la página que no será usada en el futuro por más tiempo
            PageEntity pageToEvict = findOptimalPageToEvict(realMemory, futurePages);
            movePageToVirtualMemory(virtualMemory, pageToEvict); // Mover la página a la memoria virtual
            movePageToRealMemory(realMemory, newPage); // Añadir la nueva página a la memoria real
        }
    }

    private PageEntity findOptimalPageToEvict(List<PageEntity> realMemory, List<PageEntity> futurePages) {
        int farthestPageIndex = -1;
        int farthestDistance = -1;

        // Iterar sobre las páginas en la memoria real para encontrar la que no se usa en más tiempo
        for (int i = 0; i < realMemory.size(); i++) {
            PageEntity currentPage = realMemory.get(i);
            int nextUseIndex = findNextUseIndex(currentPage, futurePages);

            if (nextUseIndex == -1) {
                // Si la página no se usará más, es la mejor opción para reemplazo
                return currentPage;
            } else if (nextUseIndex > farthestDistance) {
                // Si la página se usa pero mucho más adelante, actualizamos la que más tarda en usarse
                farthestDistance = nextUseIndex;
                farthestPageIndex = i;
            }
        }

        // Retornar la página que será usada más tarde
        return realMemory.get(farthestPageIndex);
    }

    private int findNextUseIndex(PageEntity page, List<PageEntity> futurePages) {
        // Buscar cuándo la página será usada en el futuro
        for (int i = 0; i < futurePages.size(); i++) {
            if (futurePages.get(i).getPageNumber() == page.getPageNumber()) {
                return i;
            }
        }
        // Si no se encuentra en el futuro, retornamos -1
        return -1;
    }
    
    private int getMaxRealMemorySize() {
        return 100; // Aquí defines el tamaño máximo de la memoria real
    }

}
