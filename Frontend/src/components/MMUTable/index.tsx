import React, { useState } from "react";
import "./styles.scss";
import dataDummy from "./dataDummy";
import useStyle from "../../hooks/useStyles";

interface MMUTableProps {
  title: string;
}

export default function MMUTable({ title }: MMUTableProps) {
  const [data, setData] = useState(dataDummy);
  const styles = useStyle();

  return (
    <div className="mmu-table-container border">
      <table className="mmu-table">
        <thead>
          <tr>
            <th colSpan={8} className="table-title">
              {title}
            </th>
          </tr>
          <tr>
            <th>PAGE ID</th>
            <th>PID</th>
            <th>LOADED</th>
            <th>L-ADDR</th>
            <th>M-ADDR</th>
            <th>D-ADDR</th>
            <th>LOADED-T</th>
            <th>MARK</th>
          </tr>
        </thead>
        <tbody>
          {data.map((row, index) => (
            <tr key={index} style={styles[row.pid as keyof typeof styles]}>
              <td>{row.pageId}</td>
              <td>{row.pid}</td>
              <td>{row.loaded}</td>
              <td>{row.lAddr}</td>
              <td>{row.mAddr}</td>
              <td>{row.dAddr}</td>
              <td>{row.loadedT}</td>
              <td>{row.mark}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
