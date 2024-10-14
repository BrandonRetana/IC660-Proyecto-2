import React, { useState, useEffect } from "react";
import "./styles.scss";
import DragDrop from "./DragDrop";
import OptionSelector from "../Selector";

export default function PopUp({ handleClose }: { handleClose: () => void }) {
  const [selectedOption, setSelectedOption] = useState<string>("");
  const [selectedMethod, setSelectedMethod] = useState<string>("Archivo");
  const options = ["FIFO", "SC", "MRU", "RND"];
  const methods = ["Archivo", "Automática"];
  const [seed, setSeed] = useState<string | number>("");

  const [fileGeneration, setFileGeneration] = useState(
    selectedMethod === "Archivo"
  );
  const [automaticGeneration, setAutomaticGeneration] = useState(
    selectedMethod === "Automatica"
  );

  const handleMethodChange = (method: string) => {
    setSelectedMethod(method);
    setFileGeneration(method === "Archivo");
    setAutomaticGeneration(method === "Automatica");
  };

  useEffect(() => {
    if (selectedMethod === "Archivo") {
      setSeed("");
    }
  }, [selectedMethod]);

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
                  <input name="processes" type="number" placeholder="" />
                </div>
                <div className="inputContainer">
                  <label htmlFor="operations">Operaciones:</label>
                  <input name="operations" type="number" placeholder="" />
                </div>
              </div>

              {automaticGeneration && (
                <button className="button download" type="button">
                  Descargar archivo
                  {/* <i className="fa-solid fa-download"></i> */}
                </button>
              )}

              <button className="button start" type="button">
                {automaticGeneration ? "Generar" : "Empezar"}
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}
