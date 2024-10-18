import React from "react";
import "./styles.scss";

interface PagesThrashingProps {
  internalFragmentation: number | undefined;
  trashingDuration: number | undefined;
  trashingPercentage: number | undefined;
  pagesLoadedInMemory: number | undefined;
  pagesInVirtualMemory: number | undefined;
}

export default function PagesThrashing({
  internalFragmentation,
  trashingDuration,
  trashingPercentage,
  pagesLoadedInMemory,
  pagesInVirtualMemory,
}: PagesThrashingProps) {
  return (
    <table className="pagesThrasing-table tableView border">
      <thead>
        <tr>
          <th colSpan={2}>PAGES</th>
          <th
            colSpan={2}
            rowSpan={2}
            className={` ${
              trashingPercentage && trashingPercentage >= 50 ? "Danger" : ""
            }`}
          >
            Thrashing{" "}
            {trashingPercentage && trashingPercentage >= 50 && (
              <i
                className="fa-solid fa-triangle-exclamation"
                style={{ marginLeft: "0.5rem" }}
              ></i>
            )}
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
          <td>{pagesLoadedInMemory && pagesLoadedInMemory}</td>
          <td>{pagesInVirtualMemory && pagesInVirtualMemory}</td>
          <td
            className={`thrashing ${
              trashingPercentage && trashingPercentage >= 50 ? "Danger" : ""
            }`}
          >
            {trashingDuration && trashingDuration + "s"}
          </td>
          <td
            className={`thrashing ${
              trashingPercentage && trashingPercentage >= 50 ? "Danger" : ""
            }`}
          >
            {trashingPercentage && trashingPercentage + "%"}
          </td>
          <td>{internalFragmentation && internalFragmentation + "KB"}</td>
        </tr>
      </tbody>
    </table>
  );
}
