package tec.ic660.pagination.presentation.controller;

import tec.ic660.pagination.aplication.SimulationService;
import tec.ic660.pagination.presentation.dto.ConfigRandomDTO;
import tec.ic660.pagination.presentation.dto.InstructionsListDTO;
import tec.ic660.pagination.presentation.dto.TableRawDTO;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import java.util.Queue;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SimulationController {

    @Autowired
    private SimulationService service;

    private Queue<String> stringQueue = new LinkedList<String>();

    @PostMapping("/sent/config")
    public ResponseEntity<List<String>> setConfig(@Validated @RequestBody ConfigRandomDTO config) {
        try {
            Queue<String> randomInstructions = this.service.setSimulationConfig(config);
            List<String> instructionList = new LinkedList<>(randomInstructions);
            return ResponseEntity.ok(instructionList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @PostMapping("/sent/instructions")
    public ResponseEntity<String> getInstructions(@RequestBody InstructionsListDTO request) {
        try {
            if (request.getInstructions() == null || request.getInstructions().isEmpty()) {
                return new ResponseEntity<String>("The list of strings is empty", HttpStatus.BAD_REQUEST);
            }

            stringQueue.addAll(request.getInstructions());

            service.setInstructionsQueue(stringQueue);

            return new ResponseEntity<String>("Strings added to the queue", HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<String>("An error occurred while processing the request",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/execute/step")
    public void execute() {
        this.service.executeNextStep();
    }

    @GetMapping("/get/data/algorithm")
    public List<TableRawDTO> getDataSelctedAlgorithm() {
        List<TableRawDTO> tableData = service.getDataTable(1);
        return tableData;
    }

    @GetMapping("/get/data/opt")
    public List<TableRawDTO> getDataOPT() {
        List<TableRawDTO> tableData = service.getDataTable(2);
        return tableData;
    }

    @GetMapping("/ping")
    public String getPing() {
        return "pong";
    }

}
