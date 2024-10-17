package tec.ic660.pagination.infraestructure;

import java.util.*;

public class InstructionGenerator {

    public Queue<String> generateInstructions(Integer seed, Integer numberOfProcess, Integer numberOfInstructions) {
        LinkedList<String> instructions = new LinkedList<>();
        List<Integer> ptrIds = new LinkedList<>();
        Integer actualPtrId = 0;
        Random random = new Random(seed);
        Map<Integer, List<Integer>> processMap = new Hashtable<>();
        Set<Integer> activeProcesses = new HashSet<>(); // Para rastrear los procesos que han sido creados
        Boolean killedPid;

        for (int pid = 1; pid <= numberOfProcess; pid++) {
            List<String> processInstructions = new ArrayList<>();
            int numInstructions = numberOfInstructions / numberOfProcess;
            killedPid = false;
            for (int i = 0; i < numInstructions; i++) {
                String instruction = getRandomInstruction(random);
                String lastElement = "";
                if (instructions.size() > 0) {
                    lastElement = instructions.get(instructions.size() - 1);
                }

                // Validación para evitar "new" justo después de "kill"
                if (instruction.equals("new") && lastElement.startsWith("kill")) {
                    i--; // Saltear esta instrucción, reintentar
                    continue;
                }

                // Validación para "use" y "delete", solo si hay PTRs
                if ((instruction.equals("use") || instruction.equals("delete")) && ptrIds.size() == 0) {
                    i--; // Saltear esta instrucción, reintentar
                    continue;
                }

                // Validación para "kill", solo si el proceso fue creado
                if (instruction.equals("kill") && !activeProcesses.contains(pid) && killedPid) {
                    i--; // Saltear esta instrucción, reintentar
                    continue;
                }

                switch (instruction) {
                    case "new":
                        int size = random.nextInt(81920) + 1;
                        if (!processMap.containsKey(pid)) {
                        processMap.put(pid, new LinkedList<>());
                        processInstructions.add(String.format("new(%d, %d)", pid, size));
                        actualPtrId++;
                        ptrIds.add(actualPtrId);
                        List<Integer> actualList = processMap.get(pid);
                        actualList.add(actualPtrId);
                        processMap.put(pid, actualList);
                        activeProcesses.add(pid);
                            }
                            else{
                        List<Integer> pids2beSelected = new ArrayList<>(processMap.keySet());
                        Integer pidSelected = pids2beSelected.get(random.nextInt(pids2beSelected.size())); 
                        processInstructions.add(String.format("new(%d, %d)", pidSelected, size));
                        actualPtrId++;
                        ptrIds.add(actualPtrId);
                        List<Integer> actualList = processMap.get(pidSelected);
                        actualList.add(actualPtrId);
                        processMap.put(pidSelected, actualList);
                        activeProcesses.add(pidSelected); // Marcar el proceso como activo
                            }
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
                        List<Integer> pids2beSelectedkill = new ArrayList<>(processMap.keySet());
                        Integer pidSelected2kill = pids2beSelectedkill.get(random.nextInt(pids2beSelectedkill.size())); 
                        processInstructions.add(String.format("kill(%d)", pidSelected2kill));
                        List<Integer> list2delete = processMap.get(pidSelected2kill);
                        ptrIds.removeAll(list2delete); // Eliminar todos los PTRs asociados al proceso
                        processMap.remove(pidSelected2kill); // Eliminar el proceso del mapa
                        activeProcesses.remove(pidSelected2kill); // Marcar el proceso como inactivo
                        killedPid = true;
                        break;
                }

            }
            instructions.addAll(processInstructions);
        }
        return instructions;
    }

    // Método para obtener una instrucción aleatoria con probabilidades
    private static String getRandomInstruction(Random random) {
        // Generar un número aleatorio entre 0 y 100
        int randomValue = random.nextInt(100) + 1; // Entre 1 y 100
    
        // Asignar rangos a cada instrucción según sus porcentajes
        if (randomValue <= 40) {
            return "new";  // 40% probabilidad
        } else if (randomValue <=  75) {
            return "use";  // 40% 
        } else if (randomValue <= 98) {
            return "delete";  // 10% 
        } else {
            return "kill";  // 10% 
        }
    }
}