import React, { useEffect, useState } from "react";
import "./App.css";
import MMUTable from "./components/MMUTable";
import RamState from "./components/RAMState";
import PagesThrashing from "./components/PagesThrashing";
import RAMDetails from "./components/RAMDetails";
import ProcessesSimTime from "./components/ProcessesSimTime";
import PopUp from "./components/PopUp";
import { executeStep } from "./service/config.service";

// Definir los tipos esperados
interface SimulationReport {
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  pageTable: any[];
  simulationDuration: number;
  totalProcesses: number;
  realMemoryUsageInKb: number;
  realMemoryUsagePercentage: number;
  virtualMemoryUsageInKb: number;
  virtualMemoryUsagePercentage: number;
  internalFragmentation: number;
  trashingDuration: number;
  trashingPercentage: number;
  pagesLoadedInMemory: number;
  pagesInVirtualMemory: number;
}

interface Data {
  simulationReport1: SimulationReport;
  simulationReport2: SimulationReport;
}

function App() {
  const [showPopUp, setShowPopUp] = useState(true);
  const [data, setData] = useState<Data | undefined>(undefined);
  const [executing, setExecuting] = useState(false);
  const [controllerView, setControllerView] = useState(false);
  const [process, setProcess] = useState(0);

  useEffect(() => {
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    let interval: any;

    if (executing && process > 0) {
      interval = setInterval(async () => {
        try {
          const result = await executeStep();
          setData(result);
          setProcess((prevProcess) => prevProcess - 1);

          if (process <= 1) {
            setExecuting(false);
          }
        } catch (error) {
          console.error("Error al ejecutar el paso:", error);
          setExecuting(false);
        }
      }, 100);
    }

    return () => clearInterval(interval);
  }, [executing, process]);

  return (
    <>
      {showPopUp && (
        <PopUp
          handleClose={() => setShowPopUp(false)}
          handleStart={() => setExecuting(true)}
          handleShowController={() => setControllerView(true)}
          handleSetProcess={(value: number) => setProcess(value)}
        />
      )}
      <div className="Monitor">
        <div className="VStack RamSummary">
          <RamState
            title="RAM - OTP"
            data={{ pageTable: data?.simulationReport1.pageTable || [] }}
          />
          <RamState
            title="RAM - OTRA"
            data={{ pageTable: data?.simulationReport2.pageTable || [] }}
          />
        </div>
        <div className="HStack tables">
          <div className="left VStack">
            <MMUTable
              title="MMU - OTP"
              data={data?.simulationReport1.pageTable}
            />
            <ProcessesSimTime
              simulationDuration={data?.simulationReport1.simulationDuration}
              totalProcesses={data?.simulationReport1.totalProcesses}
            />
            <RAMDetails
              realMemoryUsageInKb={data?.simulationReport1.realMemoryUsageInKb}
              realMemoryUsagePercentage={
                data?.simulationReport1.realMemoryUsagePercentage
              }
              virtualMemoryUsageInKb={
                data?.simulationReport1.virtualMemoryUsageInKb
              }
              virtualMemoryUsagePercentage={
                data?.simulationReport1.virtualMemoryUsagePercentage
              }
            />
            <PagesThrashing
              internalFragmentation={
                data?.simulationReport1.internalFragmentation
              }
              trashingDuration={data?.simulationReport1.trashingDuration}
              trashingPercentage={data?.simulationReport1.trashingPercentage}
              pagesLoadedInMemory={data?.simulationReport1.pagesLoadedInMemory}
              pagesInVirtualMemory={
                data?.simulationReport1.pagesInVirtualMemory
              }
            />
          </div>
          <div className="right VStack">
            <MMUTable
              title="MMU - OTRA"
              data={data?.simulationReport2.pageTable}
            />
            <ProcessesSimTime
              simulationDuration={data?.simulationReport2.simulationDuration}
              totalProcesses={data?.simulationReport2.totalProcesses}
            />
            <RAMDetails
              realMemoryUsageInKb={data?.simulationReport2.realMemoryUsageInKb}
              realMemoryUsagePercentage={
                data?.simulationReport2.realMemoryUsagePercentage
              }
              virtualMemoryUsageInKb={
                data?.simulationReport2.virtualMemoryUsageInKb
              }
              virtualMemoryUsagePercentage={
                data?.simulationReport2.virtualMemoryUsagePercentage
              }
            />
            <PagesThrashing
              internalFragmentation={
                data?.simulationReport2.internalFragmentation
              }
              trashingDuration={data?.simulationReport2.trashingDuration}
              trashingPercentage={data?.simulationReport2.trashingPercentage}
              pagesLoadedInMemory={data?.simulationReport2.pagesLoadedInMemory}
              pagesInVirtualMemory={
                data?.simulationReport2.pagesInVirtualMemory
              }
            />
          </div>
        </div>
      </div>

      <div className="optionsMenu VStack">
        {controllerView && (
          <div className="controller" onClick={() => setExecuting(!executing)}>
            {executing ? (
              <i className="fa-solid fa-pause"></i>
            ) : (
              <i className="fa-solid fa-play"></i>
            )}
          </div>
        )}

        <div className="menu" onClick={() => setShowPopUp(!showPopUp)}>
          <i className="fa-solid fa-sliders"></i>
        </div>
      </div>
    </>
  );
}

export default App;
