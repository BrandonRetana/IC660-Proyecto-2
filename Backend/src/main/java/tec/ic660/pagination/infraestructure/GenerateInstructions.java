package tec.ic660.pagination.infraestructure;

import java.util.ArrayList;
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
    private Map<Integer, Integer> processPTR;

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
                result = "new("+id+","+size+")";
                break;
            case TYPE_USE:
                result = "use("+id+")";
                break;
            case TYPE_DELETE:
                result = "delete("+id+")";
                break;
            case TYPE_KILL:
                result = "kill("+id+")";
                break;
        }
        return result;
    }

    private int getRandomBetween(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("min must be minor than max");
        }
        return random.nextInt((max - min) + 1) + min;
    }

    private void decrementValue(Integer pid) {
        Integer value = this.process.get(pid);
        this.process.put(pid, value - 1);
    }

    private void generateProcess(Integer numberOfProcess, Integer numberOfInstructions) {
        int remainingInstructions = numberOfInstructions;

        for (int i = 1; i <= numberOfProcess; i++) {
            int maxInstructions = remainingInstructions - (numberOfProcess - i);
            int instructionsForThisProcess = getRandomBetween(1, maxInstructions);
            process.put(i, instructionsForThisProcess);
            processPTR.put(i, 0);
            remainingInstructions -= instructionsForThisProcess;
        }
    }

    private boolean isLastInstructionKill(Queue<String> instructions) {
        if (instructions.isEmpty()) {
            return false;
        }
        String lastInstruction = ((LinkedList<String>) instructions).getLast();
        return lastInstruction.startsWith("kill");
    }

    private String generateNewInstruction(int numberOfProcess, int pid) {
        int size = getRandomBetween(MIN_PAGE_SIZE, MAX_PAGE_SIZE);
        Integer numberOfPTRs = this.process.get(pid);
        this.processPTR.put(pid, numberOfPTRs + 1);
        decrementValue(pid);
        return getStringInstruction(pid, size, TYPE_NEW);
    }

    private String generateUseInstruction(Integer pid) {
        Integer ptr = getRandomBetween(1, this.processPTR.get(pid));
        decrementValue(pid);
        return getStringInstruction(ptr, ptr, TYPE_USE);
    }

    private String generateDeleteInstruction(Integer pid) {
        Integer ptr = this.processPTR.get(pid);
        Integer numberOfPtrs = this.process.get(pid);
        this.process.put(pid, numberOfPtrs - 1);
        decrementValue(pid);
        return getStringInstruction(ptr, ptr, TYPE_DELETE);
    }

    private String generateKillInstruction(Integer pid) {
        return getStringInstruction(pid, pid, TYPE_KILL);
    }

    public Queue<String> getInstructions(Integer numberOfInstructions, Integer numberOfProcess) {
        int remainingInstructions = numberOfInstructions;
        Queue<String> instructions = new LinkedList<>();

        generateProcess(numberOfProcess, numberOfInstructions);
        List<Integer> keys = new LinkedList<>(this.process.keySet());

        while (remainingInstructions > 0) {
            int randomIndex = getRandomBetween(0, keys.size());
            int typeOfInstruction = getRandomBetween(1, 3);

            if (this.process.get(randomIndex) == 1) {
                instructions.add(generateKillInstruction(numberOfProcess));
                keys.remove(randomIndex);
                remainingInstructions--;
                continue;
            }

            if (typeOfInstruction == TYPE_NEW && !isLastInstructionKill(instructions)) {
                instructions.add(generateNewInstruction(numberOfProcess, randomIndex));
                remainingInstructions--;
            }

            if (typeOfInstruction == TYPE_USE) {
                Integer numberOfPTRs = processPTR.get(randomIndex);
                if (numberOfPTRs < 1) {
                    continue;
                }
                instructions.add(generateUseInstruction(randomIndex));
                remainingInstructions--;
            }

            if (typeOfInstruction == TYPE_DELETE) {
                Integer numberOfPTRs = processPTR.get(randomIndex);
                if (numberOfPTRs < 1) {
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
