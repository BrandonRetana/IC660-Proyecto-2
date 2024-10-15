import "./App.css";
import MMUTable from "./components/MMUTable";
import RamState from "./components/RAMState";
import PagesThrashing from "./components/PagesThrashing";
import RAMDetails from "./components/RAMDetails";
import ProcessesSimTime from "./components/ProcessesSimTime";
import PopUp from "./components/PopUp";
import { executeStep } from "./service/config.service";

import { useEffect, useState } from "react";
import React from "react";

function App() {
  const [showPopUp, setShowPopUp] = useState(true);
  const [data, setData] = useState(undefined);
  const [executing, setExecuting] = useState(false);
  const [controllerView, setControllerView] = useState(false);

  useEffect(() => {
    const execute = async () => {
      try {
        if (executing) {
          const result = await executeStep();
          setData(result);
          console.warn(data);
          await execute();
        }
      } catch (error) {
        console.error("Error al ejecutar el paso:", error);
      }
    };
    if (executing) {
      execute();
    }
  }, [data, executing]);

  return (
    <>
      {showPopUp && (
        <PopUp
          handleClose={() => {
            setShowPopUp(false);
          }}
          handleStart={() => {
            setExecuting(true);
          }}
          handleShowController={() => {
            setControllerView(true);
          }}
        />
      )}
      <div className="Monitor">
        <div className="VStack RamSummary">
          <RamState title="RAM - OTP" />
          <RamState title="RAM - OTRA" />
        </div>
        <div className="HStack tables">
          <div className="left VStack">
            <MMUTable title="MMU - OTP" />
            <ProcessesSimTime />
            <RAMDetails />
            <PagesThrashing />
          </div>
          <div className="right VStack">
            <MMUTable title="MMU - OTRA" />
            <ProcessesSimTime />
            <RAMDetails />
            <PagesThrashing />
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
