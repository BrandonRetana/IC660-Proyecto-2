import React from "react";
import "./styles.scss";

interface RamDetailsProps {
  realMemoryUsageInKb: number | undefined;
  realMemoryUsagePercentage: number | undefined;
  virtualMemoryUsageInKb: number | undefined;
  virtualMemoryUsagePercentage: number | undefined;
}

export default function RAMDetails({
  realMemoryUsageInKb,
  realMemoryUsagePercentage,
  virtualMemoryUsageInKb,
  virtualMemoryUsagePercentage,
}: RamDetailsProps) {
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
          <td>{realMemoryUsageInKb && realMemoryUsageInKb}</td>
          <td>{realMemoryUsagePercentage && realMemoryUsagePercentage}</td>
          <td>{virtualMemoryUsageInKb && virtualMemoryUsageInKb}</td>
          <td>
            {virtualMemoryUsagePercentage && virtualMemoryUsagePercentage}
          </td>
        </tr>
      </tbody>
    </table>
  );
}
