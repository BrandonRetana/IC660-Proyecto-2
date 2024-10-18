package tec.ic660.pagination.presentation.dto;

import java.util.List;

public class InstructionsListDTO {
    private List<String> instructions;
    private Integer algorithm;

    public List<String> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<String> instructions) {
        this.instructions = instructions;
    }

    public Integer getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(Integer algorithm) {
        this.algorithm = algorithm;
    }
}
