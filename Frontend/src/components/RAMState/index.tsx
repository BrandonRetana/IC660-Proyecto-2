import React from "react";
import useStyle from "../../hooks/useStyles";
import "./styles.scss";

interface PageTableEntry {
  pageId: number;
  pid: number;
  loaded: string;
  laddr: number;
  maddr: number;
  daddr: number;
  loadedT: string;
  mark: string;
}

interface Props {
  title: string;
  data?: {
    pageTable: PageTableEntry[];
  };
}

export default function RamState({ title, data }: Props) {
  const styles = useStyle();
  const cells = Array.from({ length: 100 }, (_, i) => i + 1);

  const ramData = data?.pageTable.reduce((acc, { pid, maddr }) => {
    if (!acc[pid]) {
      acc[pid] = { pages: [] };
    }
    acc[pid].pages.push(maddr);
    return acc;
  }, {} as Record<number, { pages: number[] }>);

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
              const cellStyle = ramData
                ? Object.keys(ramData).reduce((style, id) => {
                    const numericId = parseInt(id, 10);
                    return ramData[numericId].pages.includes(cell)
                      ? styles[numericId as keyof typeof styles]
                      : style;
                  }, {} as React.CSSProperties)
                : {};

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
