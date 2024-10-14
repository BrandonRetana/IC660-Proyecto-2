import React from "react";
import "./styles.scss";

export default function ProcessesSimTime() {
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
          <td>5</td>
          <td>250s</td>
        </tr>
      </tbody>
    </table>
  );
}
