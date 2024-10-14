import React, { useState } from "react";
import { FileUploader } from "react-drag-drop-files";
import "./styles.scss";

// Cambiar el tipo de archivo a CSV
const fileTypes = ["CSV"];

function DragDrop() {
  const [file, setFile] = useState<File | undefined>(undefined);
  const [error, setError] = useState<string>("");

  const handleChange = (file: File) => {
    const fileExtension = file.name.split(".").pop()?.toLowerCase();
    if (fileExtension === "csv") {
      setFile(file);
      setError("");
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
    </div>
  );
}

export default DragDrop;
