import React, { useEffect, useState } from "react";
import { FileUploader } from "react-drag-drop-files";
import "./styles.scss";

// Cambiar el tipo de archivo a TXT
const fileTypes = ["TXT"];

interface dragDropProps {
  onFileCharge: (value: string) => void;
}

function DragDrop({ onFileCharge }: dragDropProps) {
  const [fileContent, setFileContent] = useState<string>("");
  const [error, setError] = useState<string>("");

  useEffect(() => {
    onFileCharge(fileContent);
  }, [fileContent]);

  const handleChange = (file: File) => {
    const fileExtension = file.name.split(".").pop()?.toLowerCase();
    if (fileExtension === "txt") {
      setError("");

      const reader = new FileReader();
      reader.onload = (event) => {
        setFileContent((event.target?.result as string) || "");
      };
      reader.onerror = () => {
        setError("Hubo un error al leer el archivo");
      };

      reader.readAsText(file);
    } else {
      setError("El archivo debe ser un .txt");
    }
  };

  return (
    <div className="dragDrop">
      <FileUploader
        handleChange={handleChange}
        name="file"
        types={fileTypes}
        classes="dragDrop"
        label="Sube un archivo con instrucciones"
        onTypeError=""
      />
      {error && <p className="error">{error}</p>}
    </div>
  );
}

export default DragDrop;
