package tec.ic660.pagination.infraestructure;

import java.util.LinkedList;
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

    private String getStringInstruction(Integer id, Integer size) {
        String result;

        return null;
    }

    private int getRandomBetween(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("min must be minor than max");
        }
        return random.nextInt((max - min) + 1) + min;
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

    private String generateNewInstruction(int numberOfProcess) {
        int pid = getRandomBetween(1, numberOfProcess);
        int size = getRandomBetween(MIN_PAGE_SIZE, MAX_PAGE_SIZE);
        return getStringInstruction(pid, size);
    }

    public GenerateInstructions() {
        this.seed = 10;

        this.random = new Random(seed);
    }

    /*TODO: Aqui la logica deberia cambiar, generar el pid al que se le va a agregar la intruccion, agregar la verfiicacion de si este proceso existe para ello 
    se deben aplanar los keys del map de procesos, y generar un random para en entre esa lista, luego verificar que a ese pid le queden instrucciones, y en el mapa de lls procesos 
    con los PTR verificar que tenga al menos uno, para las instrucciones, use o delete. 
    */  
    public Queue<String> getInstructions(Integer numberOfInstructions, Integer numberOfProcess) {
        int remainingInstructions = numberOfInstructions;
        Queue<String> instructions = new LinkedList<>();

        generateProcess(numberOfProcess, numberOfInstructions);

        while (remainingInstructions > 0) {
            int typeOfInstruction = getRandomBetween(1, 4);

            if (typeOfInstruction == TYPE_NEW && !isLastInstructionKill(instructions)) {
                instructions.add(generateNewInstruction(numberOfProcess));
                remainingInstructions--;
            }

            if (typeOfInstruction == TYPE_USE) {
                
            }

            if (typeOfInstruction == TYPE_DELETE) {
                
            }

            if (typeOfInstruction == TYPE_KILL) {
                
            }

        }

        return instructions;
    }

    

    public Integer getSeed() {
        return seed;
    }

    public void setSeed(Integer seed) {
        this.seed = seed;
        this.random = new Random(seed);
    }

}
