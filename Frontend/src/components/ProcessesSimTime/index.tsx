import React from "react";
import "./styles.scss";

interface processesSimTimeProps {
  simulationDuration: number | undefined;
  totalProcesses: number | undefined;
}

export default function ProcessesSimTime({
  simulationDuration,
  totalProcesses,
}: processesSimTimeProps) {
  return (
    <table className="SimTime-table tableView border">
      <thead>
        <tr>
          <th>Processes</th>
          <th>Sim-Time</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td>{totalProcesses && totalProcesses}</td>
          <td>{simulationDuration && simulationDuration + "s"}</td>
        </tr>
      </tbody>
    </table>
  );
}
