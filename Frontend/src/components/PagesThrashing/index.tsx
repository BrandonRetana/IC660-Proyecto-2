import React from "react";
import "./styles.scss";

export default function PagesThrashing() {
  return (
    <table className="pagesThrasing-table tableView border">
      <thead>
        <tr>
          <th colSpan={2}>PAGES</th>
          <th colSpan={2} rowSpan={2}>
            Thrashing
          </th>
          <th rowSpan={2}>Fragmentación</th>
        </tr>
        <tr>
          <th>LOADED</th>
          <th>UNLOADED</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td>61</td>
          <td>85</td>
          <td className="thrashing">150s</td>
          <td className="thrashing">60%</td>
          <td>256KB</td>
        </tr>
      </tbody>
    </table>
  );
}
