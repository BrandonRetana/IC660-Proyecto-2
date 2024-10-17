package tec.ic660.pagination.infraestructure;

import java.util.*;

public class InstructionGenerator {

    private Queue<String> instructions;
    private Map<Integer, List<Integer>> processMemoryMap; // Almacena procesos y sus PTRs
    private Random random;
    private int nextPID;
    private int nextPTR;

    // Probabilidades para instrucciones
    private final double NEW_PROB = 0.30;
    private final double USE_PROB = 0.40;
    private final double DELETE_PROB = 0.20;
    private final double KILL_PROB = 0.10;

    public InstructionGenerator() {
        instructions = new LinkedList<>();
        processMemoryMap = new HashMap<>();
        random = new Random();
        nextPID = 1; // Comienza con el primer PID
        nextPTR = 1; // Comienza con el primer PTR
    }

    public Queue<String> generateInstructions(int seed, int numberOfProcesses, int numberOfInstructions) {
        this.random = new Random(seed);
        int totalInstructions = numberOfProcesses * numberOfInstructions;

        // Para evitar un new inmediatamente después de un kill
        boolean lastWasKill = false;

        while (totalInstructions > 0) {
            double chance = random.nextDouble();
            if (chance < NEW_PROB && !lastWasKill) {
                if (createNewProcess()) {
                    totalInstructions--;
                    lastWasKill = false;
                }
            } else if (chance < NEW_PROB + USE_PROB) {
                if (useMemoryReference()) {
                    totalInstructions--;
                    lastWasKill = false;
                }
            } else if (chance < NEW_PROB + USE_PROB + DELETE_PROB) {
                if (deleteMemoryReference()) {
                    totalInstructions--;
                    lastWasKill = false;
                }
            } else {
                if (killProcess()) {
                    totalInstructions--;
                    lastWasKill = true;
                }
            }
        }

        return instructions;
    }

    private boolean createNewProcess() {
        int size = random.nextInt(81920) + 1; // Tamaño entre 1 y 81920
        int pid = nextPID++;

        List<Integer> ptrs = new ArrayList<>();
        int ptr = nextPTR++;
        ptrs.add(ptr);

        processMemoryMap.put(pid, ptrs);
        instructions.add("new(" + pid + ", " + size + ")");

        return true;
    }

    private boolean useMemoryReference() {
        if (processMemoryMap.isEmpty()) {
            return false; // No hay procesos para usar
        }

        List<Integer> availablePTRs = getAvailablePTRs();
        if (availablePTRs.isEmpty()) {
            return false; // No hay PTRs disponibles
        }

        int ptr = availablePTRs.get(random.nextInt(availablePTRs.size()));
        instructions.add("use(" + ptr + ")");

        return true;
    }

    private boolean deleteMemoryReference() {
        if (processMemoryMap.isEmpty()) {
            return false; // No hay procesos
        }

        List<Integer> availablePTRs = getAvailablePTRs();
        if (availablePTRs.isEmpty()) {
            return false; // No hay PTRs para eliminar
        }

        int ptr = availablePTRs.get(random.nextInt(availablePTRs.size()));
        removePTR(ptr);
        instructions.add("delete(" + ptr + ")");

        return true;
    }

    private boolean killProcess() {
        if (processMemoryMap.isEmpty()) {
            return false; // No hay procesos para matar
        }

        int pid = getRandomPID();
        processMemoryMap.remove(pid); // Eliminar proceso
        instructions.add("kill(" + pid + ")");

        return true;
    }

    private int getRandomPID() {
        List<Integer> pids = new ArrayList<>(processMemoryMap.keySet());
        return pids.get(random.nextInt(pids.size()));
    }

    private List<Integer> getAvailablePTRs() {
        List<Integer> ptrs = new ArrayList<>();
        for (List<Integer> ptrList : processMemoryMap.values()) {
            ptrs.addAll(ptrList);
        }
        return ptrs;
    }

    private void removePTR(int ptr) {
        for (List<Integer> ptrList : processMemoryMap.values()) {
            ptrList.remove(Integer.valueOf(ptr));
        }
    }
}