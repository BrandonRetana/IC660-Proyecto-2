import React from "react";
import useStyle from "../../hooks/useStyles";
import "./styles.scss";
import { ramDummy } from "./ramDummy";

interface Props {
  title: string;
}

export default function RamState({ title }: Props) {
  const styles = useStyle();
  const cells = Array.from({ length: 100 }, (_, i) => i + 1);

  return (
    <div className="table-container">
      <table className="color-table">
        <thead>
          <tr>
            <th colSpan={100} className="table-title">
              {title}
            </th>
          </tr>
        </thead>
        <tbody>
          <tr>
            {cells.map((cell) => {
              const cellStyle = Object.keys(ramDummy).reduce((style, id) => {
                const numericId = parseInt(id);
                return ramDummy[numericId].pages.includes(cell)
                  ? styles[numericId as keyof typeof styles]
                  : style;
              }, {} as React.CSSProperties);

              return (
                <td
                  key={cell}
                  style={{
                    width: "20px",
                    height: "20px",
                    ...cellStyle,
                  }}
                ></td>
              );
            })}
          </tr>
        </tbody>
      </table>
    </div>
  );
}
