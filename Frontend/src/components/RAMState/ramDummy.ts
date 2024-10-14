interface DataType {
  [key: number]: {
    pages: number[];
  };
}

export const ramDummy: DataType = {
  1: { pages: [1, 5, 7, 9, 22] },
  2: { pages: [10, 56, 74, 92, 21] },
  3: { pages: [2, 4, 6, 8, 20] },
};
