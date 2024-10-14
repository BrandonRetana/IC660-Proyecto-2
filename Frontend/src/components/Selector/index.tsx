import React from "react";
import "./styles.scss";

interface OptionSelectorProps {
  options: string[];
  selectedOption: string;
  onOptionChange: (value: string) => void;
  name: string;
}

export default function OptionSelector({
  options,
  selectedOption,
  onOptionChange,
  name,
}: OptionSelectorProps) {
  return (
    <div className="option-selector">
      {options.map((option, index) => (
        <div className="option" key={index}>
          <input
            type="radio"
            id={`${name}-${index}`}
            name={name} // Asigna el `name` dinámico aquí
            value={option}
            checked={selectedOption === option}
            onChange={() => onOptionChange(option)}
          />
          <label htmlFor={`${name}-${index}`}>{option}</label>
        </div>
      ))}
    </div>
  );
}
