import React, { useState, useEffect } from "react";
import "./styles.scss";
import DragDrop from "./DragDrop";
import OptionSelector from "../Selector";
import { sendConfig, sendInstructions } from "../../service/config.service";

interface PopUpProps {
  handleClose: () => void;
  handleStart: () => void;
  handleShowController: () => void;
  handleSetProcess: (amount: number) => void;
  handleSetAlgorithm: (name: string) => void;
}

function PopUp({
  handleClose,
  handleStart,
  handleShowController,
  handleSetProcess,
  handleSetAlgorithm,
}: PopUpProps) {
  const [selectedOption, setSelectedOption] = useState("");
  const [selectedMethod, setSelectedMethod] = useState("Archivo");
  const [seed, setSeed] = useState<string | number>("");
  const [process, setProcess] = useState<number | undefined>();
  const [operations, setOperations] = useState<number | undefined>();
  const [validForm, setValidForm] = useState(false);
  const [instructions, setInstructions] = useState<string | null>(null);
  const [fileContent, setFileContent] = useState<string | null>(null);

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
    const processFileContent = async () => {
      if (fileContent) {
        const formattedContent = fileContent
          .split("\n")
          .map((line) => line.trim());

        const instructionsArray = formattedContent.filter(
          (line) => line !== ""
        );
        setInstructions(fileContent);
        setOperations(instructionsArray.length);

        const configData = {
          instructions: instructionsArray,
          algorithm: algorithmMapping[selectedOption] || 0,
        };

        try {
          const response = await sendInstructions(configData);
          console.warn(response);
        } catch (error) {
          console.error("Error enviando instrucciones:", error);
        }
      }
    };

    processFileContent();
  }, [fileContent, selectedOption]);

  useEffect(() => {
    const isValid =
      selectedOption !== "" &&
      ((!isAutomatic && fileContent !== "") ||
        (process &&
          operations &&
          process > 0 &&
          operations > 0 &&
          seed !== ""));
    setValidForm(Boolean(isValid));
  }, [selectedOption, process, operations, seed, isAutomatic, fileContent]);

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
        const formattedInstructions = response.join("\n");

        setInstructions(formattedInstructions);
      } catch (error) {
        console.error("Error al enviar la configuración:", error);
      }
    } else {
      handleClose();
      handleStart();
      handleShowController();
      handleSetProcess(operations || 0);
      handleSetAlgorithm(selectedOption);
    }
  };

  const handleDownload = () => {
    if (instructions) {
      const blob = new Blob([instructions], { type: "text/plain" });
      const url = URL.createObjectURL(blob);
      const link = document.createElement("a");
      link.href = url;
      link.download = "instrucciones.txt";
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
      URL.revokeObjectURL(url);
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
                    <DragDrop onFileCharge={setFileContent} />
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
                    <select
                      name="process"
                      value={process || ""}
                      onChange={(e) => setProcess(Number(e.target.value))}
                    >
                      <option value=""></option>
                      <option value={10}>10</option>
                      <option value={50}>50</option>
                      <option value={100}>100</option>
                    </select>
                  </div>
                  <div className="inputContainer">
                    <label>Operaciones:</label>
                    <select
                      name="operations"
                      value={operations || ""}
                      onChange={(e) => setOperations(Number(e.target.value))}
                    >
                      <option value=""></option>
                      <option value={500}>500</option>
                      <option value={1000}>1000</option>
                      <option value={5000}>5000</option>
                    </select>
                  </div>
                </div>
              )}

              {isAutomatic && (
                <button
                  className={`button ${instructions ? "download" : "disable"}`}
                  type="button"
                  onClick={handleDownload}
                  disabled={!instructions}
                >
                  Descargar archivo{" "}
                  <i
                    className="fa-solid fa-download"
                    style={{ marginLeft: "0.5rem" }}
                  ></i>
                </button>
              )}

              <button
                className={`button ${validForm ? "start" : "disable"}`}
                type="button"
                onClick={validForm ? handleSubmit : undefined}
              >
                {isAutomatic && !instructions ? "Generar" : `Empezar`}
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}

export default PopUp;
