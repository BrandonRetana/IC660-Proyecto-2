import React, { useState, useEffect } from "react";
import "./styles.scss";
import DragDrop from "./DragDrop";
import OptionSelector from "../Selector";
import { sendConfig } from "../../service/config.service";

interface PopUpProps {
  handleClose: () => void;
  handleStart: () => void;
  handleShowController: () => void;
}

function PopUp({ handleClose, handleStart, handleShowController }: PopUpProps) {
  const [selectedOption, setSelectedOption] = useState("");
  const [selectedMethod, setSelectedMethod] = useState("Archivo");
  const [seed, setSeed] = useState<string | number>("");
  const [process, setProcess] = useState<number | undefined>();
  const [operations, setOperations] = useState<number | undefined>();
  const [validForm, setValidForm] = useState(false);
  const [instructions, setInstructions] = useState<string | null>(null);

  const options = ["FIFO", "SC", "MRU", "RND"];
  const methods = ["Archivo", "Automática"];
  const algorithmMapping: { [key: string]: number } = {
    FIFO: 1,
    SC: 2,
    MRU: 3,
    RND: 4,
  };

  const isAutomatic = selectedMethod === "Automática";
  const isFileMethod = selectedMethod === "Archivo";

  useEffect(() => {
    if (isFileMethod) {
      setSeed("");
    }
  }, [selectedMethod]);

  useEffect(() => {
    const isValid =
      selectedOption !== "" &&
      (!isAutomatic ||
        (process &&
          operations &&
          process > 0 &&
          operations > 0 &&
          seed !== ""));
    setValidForm(Boolean(isValid));
  }, [selectedOption, process, operations, seed, isAutomatic]);

  const handleMethodChange = (method: string) => setSelectedMethod(method);

  const handleSubmit = async () => {
    if (!instructions) {
      const configData = {
        seed: isAutomatic ? Number(seed) : undefined,
        algorithm: algorithmMapping[selectedOption] || 0,
        process: process || 0,
        operations: operations || 0,
      };
      try {
        const response = await sendConfig(configData);
        setInstructions(response);
        console.log("Configuración enviada con éxito:", response);
        setInstructions(response);
      } catch (error) {
        console.error("Error al enviar la configuración:", error);
      }
    } else {
      handleClose();
      handleStart();
      handleShowController();
    }
  };

  return (
    <div className="background">
      <div className="popUp VStack">
        <i className="fa-solid fa-minus close" onClick={handleClose}></i>

        <div className="HStack">
          <div className="popUpLeft VStack">
            <form>
              <div className="VStack method">
                <label>Método de generación:</label>
                <OptionSelector
                  options={methods}
                  selectedOption={selectedMethod}
                  onOptionChange={handleMethodChange}
                  name="generationMethod"
                />
              </div>

              {isAutomatic && (
                <div className="inputContainer">
                  <label>Semilla:</label>
                  <input
                    name="seed"
                    type="number"
                    value={seed}
                    onChange={(e) => setSeed(Number(e.target.value))}
                  />
                </div>
              )}

              {isFileMethod && (
                <>
                  <p className="hint">Carga un archivo</p>
                  <div className="dropContainer">
                    <DragDrop />
                  </div>
                </>
              )}

              <div className="inputContainer">
                <label>Algoritmo:</label>
                <OptionSelector
                  options={options}
                  selectedOption={selectedOption}
                  onOptionChange={setSelectedOption}
                  name="algorithm"
                />
              </div>

              {isAutomatic && (
                <div className="HStack optionsContainer">
                  <div className="inputContainer">
                    <label>Procesos:</label>
                    <input
                      name="process"
                      type="number"
                      value={process || ""}
                      onChange={(e) => setProcess(Number(e.target.value))}
                    />
                  </div>
                  <div className="inputContainer">
                    <label>Operaciones:</label>
                    <input
                      name="operations"
                      type="number"
                      value={operations || ""}
                      onChange={(e) => setOperations(Number(e.target.value))}
                    />
                  </div>
                </div>
              )}

              {isAutomatic && (
                <button className="button download" type="button">
                  Descargar archivo
                </button>
              )}

              <button
                className={`button ${validForm ? "start" : "disable"}`}
                type="button"
                onClick={validForm ? handleSubmit : undefined}
              >
                {isAutomatic && !instructions ? "Generar" : "Empezar"}
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}

export default PopUp;
