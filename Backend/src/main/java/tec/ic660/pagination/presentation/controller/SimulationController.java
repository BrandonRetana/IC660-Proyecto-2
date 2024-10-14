package tec.ic660.pagination.presentation.controller;

import tec.ic660.pagination.aplication.SimulationService;
import tec.ic660.pagination.presentation.dto.InstructionsListDTO;
import tec.ic660.pagination.presentation.dto.TableRawDTO;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Queue;
import java.util.LinkedList;
import java.util.List;

@RequestMapping("/api")
public class SimulationController {

    @Autowired
    private SimulationService service;

    private Queue<String> stringQueue = new LinkedList<String>();

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

    @GetMapping("/get/data/algorithm")
    public List<TableRawDTO> getDataSelctedAlgorithm(){
        List<TableRawDTO> tableData = service.getDataTable(1);
        return tableData;
    }

    @GetMapping("/get/data/opt")
    public List<TableRawDTO> getDataOPT(){
        List<TableRawDTO> tableData = service.getDataTable(2);
        return tableData;
    }

}
