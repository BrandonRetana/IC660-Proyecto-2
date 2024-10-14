package tec.ic660.pagination.infraestructure;

import java.util.*;

import org.springframework.stereotype.Component;

@Component
public class InstructionGenerator {

    public Queue<String> generateInstructions(int seed, int numberOfProcesses, int numberOfOperations) {
        Random random = new Random(seed);
        LinkedList<String> instructions = new LinkedList<>();
        List<Integer> pids = new ArrayList<>();
        Map<Integer, List<Integer>> ptrs = new HashMap<>(); // Map to track ptrs by process
        Set<Integer> killedProcesses = new HashSet<>();

        for (int i = 1; i <= numberOfProcesses; i++) {
            pids.add(i);
        }

        // Ensure each process has at least two instructions
        for (int pid : pids) {
            // First instruction must always be 'new'
            int size = (random.nextInt(400) + 1) * 4; // Size in B (multiple of 4KB)
            int ptr = instructions.size() + 1;
            instructions.add("new(" + pid + ", " + size + ")");
            ptrs.computeIfAbsent(pid, k -> new ArrayList<>()).add(ptr);

            // Second instruction must be 'use' of the newly created ptr
            instructions.add("use(" + ptr + ")");
        }

        int remainingOperations = numberOfOperations - instructions.size();

        while (remainingOperations > 0) {
            int pid = pids.get(random.nextInt(pids.size()));
            List<String> possibleInstructions = new ArrayList<>(Arrays.asList("new", "use", "delete", "kill"));

            if (killedProcesses.contains(pid)) {
                continue; // Skip processes that have already been killed
            }
            if (ptrs.isEmpty() || ptrs.values().stream().allMatch(List::isEmpty)) {
                possibleInstructions.remove("use");
                possibleInstructions.remove("delete");
            }

            // Ensure 'new' cannot be executed after a 'kill'
            if (instructions.size() > 0 && instructions.getLast().startsWith("kill(")) {
                possibleInstructions.remove("new");
            }

            if (possibleInstructions.isEmpty()) {
                continue; // Skip if no valid instructions are available
            }

            // Assign lower probability to 'kill' instruction
            if (possibleInstructions.contains("kill") && random.nextDouble() > 0.1) { // 10% chance to pick 'kill'
                possibleInstructions.remove("kill");
            }

            String instruction = possibleInstructions.get(random.nextInt(possibleInstructions.size()));

            switch (instruction) {
                case "new":
                    int size = (random.nextInt(400) + 1) * 4; // Size in B (multiple of 4KB)
                    int ptr = instructions.size() + 1;
                    instructions.add("new(" + pid + ", " + size + ")");
                    ptrs.computeIfAbsent(pid, k -> new ArrayList<>()).add(ptr);
                    break;
                case "use":
                    List<Integer> availablePtrs = new ArrayList<>();
                    ptrs.values().forEach(availablePtrs::addAll);
                    if (!availablePtrs.isEmpty()) {
                        int ptrUse = availablePtrs.get(random.nextInt(availablePtrs.size()));
                        instructions.add("use(" + ptrUse + ")");
                    }
                    break;
                case "delete":
                    availablePtrs = new ArrayList<>();
                    ptrs.values().forEach(availablePtrs::addAll);
                    if (!availablePtrs.isEmpty()) {
                        int ptrDelete = availablePtrs.get(random.nextInt(availablePtrs.size()));
                        instructions.add("delete(" + ptrDelete + ")");
                        ptrs.values().forEach(list -> list.remove((Integer) ptrDelete));
                        ptrs.entrySet().removeIf(entry -> entry.getValue().isEmpty());
                    }
                    break;
                case "kill":
                    instructions.add("kill(" + pid + ")");
                    ptrs.remove(pid);
                    killedProcesses.add(pid);
                    break;
            }

            remainingOperations--;
        }

        // Ensure the last instruction for each process is 'kill'
        for (int pid : pids) {
            if (!killedProcesses.contains(pid)) {
                instructions.add("kill(" + pid + ")");
                killedProcesses.add(pid);
            }
        }

        return instructions;
    }

}