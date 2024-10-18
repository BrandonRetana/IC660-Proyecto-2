package tec.ic660.pagination.presentation.controller;

import tec.ic660.pagination.aplication.SimulationService;
import tec.ic660.pagination.domain.entity.memory.PageEntity;
import tec.ic660.pagination.domain.valueObjects.PTR;
import tec.ic660.pagination.infraestructure.SaveGlobalStaticCounters;
import tec.ic660.pagination.presentation.dto.ConfigRandomDTO;
import tec.ic660.pagination.presentation.dto.DualSimulationReportDTO;
import tec.ic660.pagination.presentation.dto.InstructionsListDTO;
import tec.ic660.pagination.presentation.dto.SimulationReportDTO;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    private SimulationService service = new SimulationService();

    private SimulationService serviceOPT = new SimulationService();

    @PostMapping("/sent/config")
    public ResponseEntity<List<String>> setConfigRandom(@Validated @RequestBody ConfigRandomDTO config) {
        try {
            resetAllServices();
            Queue<String> randomInstructions = this.service.setSimulationConfig(config);
            Queue<String> copiedInstructionsQueue = new LinkedList<>(randomInstructions);

            serviceOPT.setInstructionsQueue(copiedInstructionsQueue);
            serviceOPT.setOPTAlgorithm();

            List<String> instructionList = new LinkedList<>(randomInstructions);
            return ResponseEntity.ok(instructionList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @PostMapping("/sent/instructions")
    public ResponseEntity<String> setInstructionsConfig(@RequestBody InstructionsListDTO request) {
        try {
            if (request.getInstructions() == null || request.getInstructions().isEmpty()) {
                return new ResponseEntity<String>("The list of strings is empty", HttpStatus.BAD_REQUEST);
            }
            resetAllServices();

            Queue<String> stringQueueOPT = new LinkedList<String>(request.getInstructions());
            Queue<String> stringQueue = new LinkedList<String>(request.getInstructions());

            service.setInstructionsQueue(stringQueue);
            serviceOPT.setInstructionsQueue(stringQueueOPT);

            service.setSelectedAlgorithm(request.getAlgorithm());
            serviceOPT.setOPTAlgorithm();

            return new ResponseEntity<String>("Strings added to the queue", HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<String>("An error occurred while processing the request",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/execute/step")
    public ResponseEntity<DualSimulationReportDTO> executeStep() {
        SaveGlobalStaticCounters counters;
        try {
            counters = new SaveGlobalStaticCounters(PTR.getCounter(), PageEntity.getCounter());
            this.service.executeNextStep();
            PageEntity.setCounter(counters.getPageCounter());
            PTR.setCounter(counters.getPTRCounter());
            this.serviceOPT.executeNextStep();

            // Generar los dos informes de simulación
            SimulationReportDTO report1 = service.getSimulationReport();
            SimulationReportDTO report2 = serviceOPT.getSimulationReport();

            // Crear el DTO que contiene ambos informes
            DualSimulationReportDTO dualReport = new DualSimulationReportDTO();
            dualReport.setSimulationReport1(report1);
            dualReport.setSimulationReport2(report2);

            // Devolver respuesta 200 OK con los informes
            return ResponseEntity.ok(dualReport);

        } catch (Exception e) {
            // Registra el error (opcional)
            e.printStackTrace();  // O usa un logger

            // En caso de error, devolver respuesta 500 con el mensaje de error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null); // Devuelve null o podrías devolver un objeto vacío o con detalles
        }
    }

    private void resetAllServices(){
        this.service.resetAll();
        this.serviceOPT.resetAll();
    }

    @GetMapping("/ping")
    public String getPing() {
        return "pong";
    }

}
