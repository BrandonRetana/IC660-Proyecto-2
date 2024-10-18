package tec.ic660.pagination.infraestructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

public class InstructionGenerator {

    public Queue<String> generateInstructions(Integer seed, Integer numberOfProcesses, Integer numberOfInstructions) {
        Integer numberOfInstructionsMinusKills = numberOfInstructions-numberOfProcesses; 
        List<String> instructions = new ArrayList<>();
        List<Integer> activePtrs = new ArrayList<>();
        Random random = new Random(seed);

        // Mapa que almacena los PTRs de cada proceso
        Map<Integer, List<Integer>> processMap = new HashMap<>();
        Set<Integer> createdProcesses = new HashSet<>(); // Para rastrear los procesos creados
        int totalOperations = 0;
        int nextPtrId = 1;

        // Inicializar los procesos con una lista vacía de PTRs
        for (int pid = 1; pid <= numberOfProcesses; pid++) {
            processMap.put(pid, new ArrayList<>());
        }

        // Primera fase: Generar operaciones 'new', 'use' y 'delete'
        while (totalOperations < numberOfInstructionsMinusKills) {
            int pid = random.nextInt(numberOfProcesses) + 1; // Seleccionar un proceso aleatorio (entre 1 y numberOfProcesses)
            String instruction = getRandomInstruction(random);

            // Instrucciones normales (new, use, delete)
            switch (instruction) {
                case "new":
                    // Asignar un nuevo PTR al proceso
                    int size = random.nextInt(81920) + 1;
                    instructions.add(String.format("new(%d, %d)", pid, size));
                    processMap.get(pid).add(nextPtrId); // Añadir un nuevo PTR al proceso
                    activePtrs.add(nextPtrId);
                    nextPtrId++;
                    createdProcesses.add(pid); // Marcar el proceso como creado
                    totalOperations++;
                    break;

                case "use":
                    // Solo se puede usar si el proceso tiene PTRs disponibles
                    if (!processMap.get(pid).isEmpty()) {
                        int ptrToUse = processMap.get(pid).get(random.nextInt(processMap.get(pid).size()));
                        instructions.add(String.format("use(%d)", ptrToUse));
                        totalOperations++;
                    }
                    break;

                case "delete":
                    // Solo se puede eliminar si el proceso tiene PTRs activos
                    if (!processMap.get(pid).isEmpty()) {
                        int ptrToDelete = processMap.get(pid).remove(random.nextInt(processMap.get(pid).size()));
                        activePtrs.remove(Integer.valueOf(ptrToDelete));
                        instructions.add(String.format("delete(%d)", ptrToDelete));
                        totalOperations++;
                    }
                    break;
            }

            // Si hemos alcanzado el número de operaciones, detener
            if (totalOperations >= numberOfInstructions) {
                break;
            }
        }

        // Segunda fase: Generar las instrucciones 'kill' para todos los procesos creados
        for (Integer pid : createdProcesses) {
            instructions.add(String.format("kill(%d)", pid)); // Generar 'kill' para cada proceso creado
        }

        return new LinkedList<>(instructions);
    }


    // Método para obtener una instrucción aleatoria con probabilidades
    private static String getRandomInstruction(Random random) {
        // Generar un número aleatorio entre 0 y 100
        int randomValue = random.nextInt(100) + 1; // Entre 1 y 100
    
        // Asignar rangos a cada instrucción según sus porcentajes
        if (randomValue <= 15) {
            return "new";  // 60% probabilidad
        } else if (randomValue <= 70) {
            return "use";  // 35% probabilidad
        } else {
            return "delete";  // 23% probabilidad
        }
    }
}