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
                        numInstructions--;
                        continue;
                    }
                }
                if (instruction.startsWith("use") || instruction.startsWith("delete")) {
                    if (ptrIds.size() == 0) {
                        numInstructions--;
                        continue;
                    }
                }
                switch (instruction) {
                    case "new":
                        int size = random.nextInt(81920) + 1;
                        processInstructions.add(String.format("new(%d, %d)", pid, size));
                        actualPtrId++;
                        ptrIds.add(actualPtrId);
                        List<Integer> actualList = processMap.get(pid);
                        actualList.add(actualPtrId);
                        processMap.put(pid, actualList);
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
                        processInstructions.add(String.format("kill(%d)", pid));
                        List<Integer> list2delete = processMap.get(pid);
                        ptrIds.removeAll(list2delete);
                        break;
                }
            }
            instructions.addAll(processInstructions);
        }
        Queue<String> instructionsQueue = instructions;
        return instructionsQueue;
    }

    private static String getRandomInstruction(Random random) {
        // Crear una lista donde las instrucciones con más probabilidad aparezcan más veces
        String[] weightedInstructions = {
            "new", "new", "new","new", "new", "new", "new", "new", "new","new", "new", "new", 
            "use", "use", "use","use", "use", "use", "use", "use", "use","use", "use", "use", 
            "delete", "delete", "delete", "delete",  
            "kill"               
        };
    
        // Elegir una instrucción al azar de la lista ponderada
        return weightedInstructions[random.nextInt(weightedInstructions.length)];
    }

}