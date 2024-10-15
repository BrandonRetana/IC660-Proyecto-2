package tec.ic660.pagination.domain.algorithms;

import tec.ic660.pagination.domain.entity.memory.PageEntity;

import java.util.*;

public class OptimalAlgorithm extends PagingAlgorithm {

    private Map<Integer, List<Integer>> futurePageUsage;

    public OptimalAlgorithm(Queue<String> instructionsQueue) {
        this.futurePageUsage = new HashMap<>();
        preprocessInstructions(instructionsQueue);
    }
    @Override
    public void addPageToAlgorithmStructure(PageEntity page) {
    }

    @Override
    public void removePageFromAlgorithmStructure(PageEntity page) {
    }
    private void preprocessInstructions(Queue<String> instructionsQueue) {
        int instructionIndex = 0;

        for (String instruction : instructionsQueue) {
            String[] parts = instruction.split("\\(|,|\\)");
            String operation = parts[0].trim();

            if (operation.equals("use")) {
                int ptr = Integer.parseInt(parts[1].trim());
                futurePageUsage.putIfAbsent(ptr, new ArrayList<>());
                futurePageUsage.get(ptr).add(instructionIndex);
            }
            instructionIndex++;
        }
    }

    @Override
    public void handlePageFault(List<PageEntity> realMemory, List<PageEntity> virtualMemory, PageEntity newPage, Integer pagesInMemory) {
        if (pagesInMemory == 100) { // Si la memoria real está llena
            PageEntity pageToEvict = findOptimalPageToEvict(realMemory);
            movePageToVirtualMemory(virtualMemory, pageToEvict);
        }
        movePageToRealMemory(realMemory, newPage);
    }

    private PageEntity findOptimalPageToEvict(List<PageEntity> realMemory) {
        int farthestUse = -1;
        PageEntity pageToEvict = null;

        // Iteramos sobre todas las páginas de la memoria real
        for (PageEntity page : realMemory) {
            if (page == null) continue;

            // Buscamos cuándo se volverá a usar el puntero (ptr) asociado a la página
            List<Integer> futureUses = futurePageUsage.getOrDefault(page.getPtrId(), new ArrayList<>());

            // Si la página no se va a usar más en el futuro, la seleccionamos para reemplazo
            if (futureUses.isEmpty()) {
                return page;
            } else {
                // Si la página se usará en el futuro, tomamos el uso más lejano
                int nextUse = futureUses.remove(0); // Tomamos el próximo uso
                if (nextUse > farthestUse) {
                    farthestUse = nextUse;
                    pageToEvict = page;
                }
            }
        }

        return pageToEvict;
    }
}
