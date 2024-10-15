import React, { useState, useEffect } from "react";
import "./styles.scss";
import DragDrop from "./DragDrop";
import OptionSelector from "../Selector";
import { sendConfig } from "../../service/config.service";

export default function PopUp({ handleClose }: { handleClose: () => void }) {
  const [selectedOption, setSelectedOption] = useState<string>("");
  const [selectedMethod, setSelectedMethod] = useState<string>("Archivo");
  const [seed, setSeed] = useState<string | number>("");
  const [processes, setProcesses] = useState<number | undefined>(undefined);
  const [operations, setOperations] = useState<number | undefined>(undefined);
  const [validForm, setValidForm] = useState(false);

  const options = ["FIFO", "SC", "MRU", "RND"];
  const methods = ["Archivo", "Automática"];
  const [fileGeneration, setFileGeneration] = useState(
    selectedMethod === "Archivo"
  );
  const [automaticGeneration, setAutomaticGeneration] = useState(
    selectedMethod === "Automática"
  );

  const algorithmMapping: { [key: string]: number } = {
    FIFO: 1,
    SC: 2,
    MRU: 3,
    RND: 4,
  };

  const handleMethodChange = (method: string) => {
    setSelectedMethod(method);
    setFileGeneration(method === "Archivo");
    setAutomaticGeneration(method === "Automática");
  };

  useEffect(() => {
    if (selectedMethod === "Archivo") {
      setSeed("");
    }
  }, [selectedMethod]);

  const handleSubmit = async () => {
    const algorithmValue = algorithmMapping[selectedOption] || 0;
    const configData = {
      generationMethod: selectedMethod,
      seed: automaticGeneration ? seed : undefined,
      algorithm: algorithmValue,
      process: processes ?? 0,
      operations: operations ?? 0,
    };
    console.warn(configData);

    try {
      const response = await sendConfig(configData);
      console.log("Configuración enviada con éxito:", response);
    } catch (error) {
      console.error("Error al enviar la configuración:", error);
    }
  };

  useEffect(() => {
    const isValid =
      selectedOption !== "" &&
      processes !== undefined &&
      operations !== undefined &&
      processes > 0 &&
      operations > 0 &&
      (!automaticGeneration || (automaticGeneration && seed !== ""));
    setValidForm(isValid);
  }, [selectedOption, processes, operations, seed, automaticGeneration]);

  return (
    <div className="background">
      <div className="popUp VStack">
        <i className="fa-solid fa-minus close" onClick={handleClose}></i>

        <div className="HStack">
          <div className="popUpLeft VStack">
            <form action="#">
              <div className="VStack method">
                <label htmlFor="">Método de generación:</label>
                <OptionSelector
                  options={methods}
                  selectedOption={selectedMethod}
                  onOptionChange={handleMethodChange}
                  name="generationMethod"
                />
              </div>

              {automaticGeneration && (
                <div className="inputContainer">
                  <label htmlFor="seed">Semilla:</label>
                  <input
                    name="seed"
                    type="number"
                    placeholder=""
                    value={seed}
                    onChange={(e) => setSeed(e.target.value)}
                  />
                </div>
              )}

              {fileGeneration && (
                <>
                  <p className="hint">Carga un archivo</p>
                  <div className="dropContainer">
                    <DragDrop />
                  </div>
                </>
              )}

              <div className="inputContainer">
                <label htmlFor="">Algoritmo:</label>
                <OptionSelector
                  options={options}
                  selectedOption={selectedOption}
                  onOptionChange={setSelectedOption}
                  name="algorithm"
                />
              </div>

              <div className="HStack optionsContainer">
                <div className="inputContainer">
                  <label htmlFor="processes">Procesos:</label>
                  <input
                    name="processes"
                    type="number"
                    placeholder=""
                    value={processes || ""}
                    onChange={(e) => setProcesses(Number(e.target.value))}
                  />
                </div>
                <div className="inputContainer">
                  <label htmlFor="operations">Operaciones:</label>
                  <input
                    name="operations"
                    type="number"
                    placeholder=""
                    value={operations || ""}
                    onChange={(e) => setOperations(Number(e.target.value))}
                  />
                </div>
              </div>

              {automaticGeneration && (
                <button className="button download" type="button">
                  Descargar archivo
                </button>
              )}

              <button
                className={`button ${validForm ? "start" : "disable"}`}
                type="button"
                onClick={validForm ? handleSubmit : undefined}
              >
                {automaticGeneration ? "Generar" : "Empezar"}
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}
