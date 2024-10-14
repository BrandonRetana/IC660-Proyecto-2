package tec.ic660.pagination.infraestructure;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class GenerateInstructions {
    private Integer seed;
    private Random random;
    private Map<Integer, Integer> process;
    private Map<Integer, List<Integer>> processPTR;
    private static int counter = 0;

    private static final int MIN_PAGE_SIZE = 4096; // 1 page
    private static final int MAX_PAGE_SIZE = 81920; // 20 pages
    private static final int TYPE_NEW = 1;
    private static final int TYPE_USE = 2;
    private static final int TYPE_DELETE = 3;
    private static final int TYPE_KILL = 4;

    private String getStringInstruction(Integer id, Integer size, Integer typeOfString) {
        String result = "";
        switch (typeOfString) {
            case TYPE_NEW:
                result = "new(" + id + "," + size + ")";
                break;
            case TYPE_USE:
                result = "use(" + id + ")";
                break;
            case TYPE_DELETE:
                result = "delete(" + id + ")";
                break;
            case TYPE_KILL:
                result = "kill(" + id + ")";
                break;
        }
        return result;
    }

    private int getRandomBetween(int min, int max) {
        if (min == max) {
            return max;
        }
        if (min > max) {
            System.out.println("process");
            System.out.println("process");
            throw new IllegalArgumentException("min must be minor than max");
        }
        return random.nextInt((max - min) + 1) + min;
    }

    private void decrementValue(Integer pid) {
        Integer value = this.process.get(pid);
        this.process.put(pid, value - 1);
    }

    private void generateProcesses(int numberOfProcesses, int numberOfInstructions) {
        int remainingInstructions = numberOfInstructions;

        // Asegurarse de que cada proceso tenga al menos 2 instrucciones
        if (numberOfInstructions < numberOfProcesses * 2) {
            throw new IllegalArgumentException("Number of instructions must be at least twice the number of processes");
        }

        // Asignar al menos 2 instrucciones a cada proceso
        for (int i = 0; i < numberOfProcesses; i++) {
            process.put(i, 2);
            processPTR.put(i, new LinkedList<>());
            remainingInstructions -= 2;
        }

        // Distribuir las instrucciones restantes aleatoriamente
        for (int i = 1; i < numberOfProcesses; i++) {
            if (remainingInstructions > 0) {
                int maxInstructions = remainingInstructions - (numberOfProcesses - i);
                int instructionsForThisProcess = getRandomBetween(0, maxInstructions);
                process.put(i, process.get(i) + instructionsForThisProcess);
                remainingInstructions -= instructionsForThisProcess;
            }
        }
    }

    private boolean isLastInstructionKill(Queue<String> instructions) {
        if (instructions.isEmpty()) {
            return false;
        }
        String lastInstruction = ((LinkedList<String>) instructions).getLast();
        return lastInstruction.startsWith("kill");
    }

    private String generateNewInstruction(int pid) {
        int size = getRandomBetween(MIN_PAGE_SIZE, MAX_PAGE_SIZE);
        processPTR.get(pid).add(++counter);
        decrementValue(pid);
        return getStringInstruction(pid, size, TYPE_NEW);
    }

    private String generateUseInstruction(Integer pid) {
        Integer randomIndex = getRandomBetween(0, this.processPTR.get(pid).size() - 1);
        Integer ptr = processPTR.get(pid).get(randomIndex);
        decrementValue(pid);
        return getStringInstruction(ptr, ptr, TYPE_USE);
    }

    private String generateDeleteInstruction(Integer pid) {
        Integer randomIndex = getRandomBetween(0, this.processPTR.get(pid).size() - 1);
        Integer ptr = processPTR.get(pid).get(randomIndex);
        processPTR.get(pid).remove(ptr);
        decrementValue(pid);
        return getStringInstruction(ptr, ptr, TYPE_DELETE);
    }

    private String generateKillInstruction(Integer pid) {
        return getStringInstruction(pid, pid, TYPE_KILL);
    }

    public Queue<String> getInstructions(Integer numberOfInstructions, Integer numberOfProcess) {
        int remainingInstructions = numberOfInstructions;
        Queue<String> instructions = new LinkedList<>();

        generateProcesses(numberOfProcess, numberOfInstructions);
        List<Integer> keys = new LinkedList<>(this.process.keySet());

        while (remainingInstructions > 0 && keys.size() > 0) {
            int randomIndex = getRandomBetween(0, keys.size() - 1);
            int typeOfInstruction = getRandomBetween(1, 3);

            if (this.process.get(randomIndex) == 1) {
                instructions.add(generateKillInstruction(numberOfProcess));
                keys.remove(randomIndex);
                remainingInstructions--;
                continue;
            }

            if (typeOfInstruction == TYPE_NEW && !isLastInstructionKill(instructions)) {
                instructions.add(generateNewInstruction(randomIndex));
                remainingInstructions--;
            }

            if (typeOfInstruction == TYPE_USE) {
                Integer numberOfPTRs = processPTR.get(randomIndex).size();
                if (numberOfPTRs < 1) {
                    if (!isLastInstructionKill(instructions)) {
                        instructions.add(generateNewInstruction(randomIndex));
                        remainingInstructions--;    
                    }
                    continue;
                }
                instructions.add(generateUseInstruction(randomIndex));
                remainingInstructions--;
            }

            if (typeOfInstruction == TYPE_DELETE) {
                Integer numberOfPTRs = processPTR.get(randomIndex).size();
                if (numberOfPTRs < 1) {
                    if (!isLastInstructionKill(instructions)) {
                        instructions.add(generateNewInstruction(randomIndex));
                        remainingInstructions--;    
                    }
                    continue;
                }
                instructions.add(generateDeleteInstruction(randomIndex));
                remainingInstructions--;
            }

        }

        return instructions;
    }

    public GenerateInstructions() {
        this.seed = 10;
        this.process = new HashMap<>();
        this.processPTR = new HashMap<>();
        this.random = new Random(seed);
    }

    public Integer getSeed() {
        return seed;
    }

    public void setSeed(Integer seed) {
        this.seed = seed;
        this.random = new Random(seed);
    }

}
