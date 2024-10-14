import React from "react";
import "./styles.scss";

export default function RAMDetails() {
  return (
    <table className="RamDetails-table tableView border">
      <thead>
        <tr>
          <th>RAM KB</th>
          <th>RAM %</th>
          <th>V-RAM KB</th>
          <th>V-RAM %</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td>244</td>
          <td>61</td>
          <td>40</td>
          <td>4%</td>
        </tr>
      </tbody>
    </table>
  );
}
