import "./App.css";
import MMUTable from "./components/MMUTable";
import RamState from "./components/RAMState";
import PagesThrashing from "./components/PagesThrashing";
import RAMDetails from "./components/RAMDetails";
import ProcessesSimTime from "./components/ProcessesSimTime";
import PopUp from "./components/PopUp";

import { useEffect, useState } from "react";

function App() {
  const [showPopUp, setShowPopUp] = useState(true);

  // useEffect(() => {
  //   const fetchData = async () => {
  //     try {
  //       const response = await fetch("");
  //       const data = await response.json();
  //       console.log(data);
  //     } catch (error) {
  //       console.error("Error al obtener los datos:", error);
  //     }
  //   };

  //   const intervalId = setInterval(fetchData, 1000);
  //   return () => clearInterval(intervalId);
  // }, []);

  return (
    <>
      {showPopUp && (
        <PopUp
          handleClose={() => {
            setShowPopUp(false);
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

      <div className="menu" onClick={() => setShowPopUp(!showPopUp)}>
        <i className="fa-solid fa-sliders"></i>
      </div>
    </>
  );
}

export default App;
