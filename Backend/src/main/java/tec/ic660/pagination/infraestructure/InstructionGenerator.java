package tec.ic660.pagination.infraestructure;

import java.util.*;

public class InstructionGenerator {

    public Queue<String> generateInstructions(Integer seed, Integer numberOfProcess,
            Integer numberOfInstructions) {
        LinkedList<String> instructions = new LinkedList<>();
        List<Integer> ptrIds = new LinkedList<>();
        Integer actualPtrId = 0;
        Random random = new Random(seed);
        Map<Integer, List<Integer>> processMap = new Hashtable<>();

        for (int pid = 1; pid <= numberOfProcess; pid++) {
            List<String> processInstructions = new ArrayList<>();
            int numInstructions = numberOfInstructions / numberOfProcess;
            processMap.put(pid, new LinkedList<Integer>());
            for (int i = 0; i < numInstructions; i++) {
                String instruction = getRandomInstruction(random);
                String lastElement = "";
                if (instructions.size() > 0) {
                lastElement = instructions.get(instructions.size() - 1);                    
                }
                if (instruction.equals("new")) {
                    if (lastElement.startsWith("kill")) {
                        i--;
                        continue;
                    }
                }
                if (instruction.startsWith("use") || instruction.startsWith("delete")) {
                    if (ptrIds.size() == 0) {
                        i--;
                        continue;
                    }
                }
                List<Integer> processes = new ArrayList<>(processMap.keySet());
                Integer randomPID = processes.get(random.nextInt(processes.size()));
                switch (instruction) {
                    case "new":
                        int size = random.nextInt(81920) + 1;
                        processInstructions.add(String.format("new(%d, %d)", randomPID, size));
                        actualPtrId++;
                        ptrIds.add(actualPtrId);
                        List<Integer> actualList = processMap.get(randomPID);
                        actualList.add(actualPtrId);
                        processMap.put(randomPID, actualList);
                        break;
                    case "use":
                        int ptrUse = random.nextInt(ptrIds.size());
                        Integer ptrdId2use = ptrIds.get(ptrUse);
                        processInstructions.add(String.format("use(%d)", ptrdId2use));
                        break;
                    case "delete":
                        int ptrIdex2delete = random.nextInt(ptrIds.size());
                        Integer ptrdId2Delete = ptrIds.get(ptrIdex2delete);
                        processInstructions.add(String.format("delete(%d)", ptrdId2Delete));
                        ptrIds.remove(ptrdId2Delete);
                        break;
                    case "kill":
                        processInstructions.add(String.format("kill(%d)", randomPID));
                        List<Integer> list2delete = processMap.get(randomPID);
                        ptrIds.removeAll(list2delete);
                        processes.remove(randomPID);
                        break;
                }
            }
            instructions.addAll(processInstructions);
        }
        Queue<String> instructionsQueue = instructions;
        return instructionsQueue;
    }

    private static String getRandomInstruction(Random random) {
        // Generar un número aleatorio entre 0 y 100
        int randomValue = random.nextInt(100) + 1; // Entre 1 y 100
    
        // Asignar rangos a cada instrucción según sus porcentajes
        if (randomValue <= 30) {
            return "new";  // 30% probabilidad
        } else if (randomValue <= 70) {
            return "use";  // 40% 
        } else if (randomValue <= 90) {
            return "delete";  // 20% 
        } else {
            return "kill";  // 10% 
        }
    }

}