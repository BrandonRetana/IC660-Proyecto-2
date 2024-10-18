import React from "react";
import "./styles.scss";
import useStyle from "../../hooks/useStyles";

interface MMUTableProps {
  title: string;
  data?: {
    pageId: number;
    pid: number;
    loaded: string;
    laddr: number;
    maddr: number;
    daddr: number;
    loadedT: string;
    mark: string;
  }[];
}

export default function MMUTable({ title, data }: MMUTableProps) {
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
          {data !== undefined &&
            data.map((row, index) => (
              <tr
                key={index}
                style={styles[row.pid as unknown as keyof typeof styles]}
              >
                <td>{row.pageId}</td>
                <td>{row.pid}</td>
                <td>{row.loaded}</td>
                <td>{row.laddr}</td>
                <td>{row.maddr === -1 ? "" : row.maddr}</td>
                <td>{row.daddr}</td>
                <td>{row.loadedT}</td>
                <td>{row.mark}</td>
              </tr>
            ))}
        </tbody>
      </table>
    </div>
  );
}
