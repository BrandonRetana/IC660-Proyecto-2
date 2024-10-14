import { useMemo } from "react";

const useStyle = () => {
  return useMemo(
    () => ({
      1: { backgroundColor: "#FF8A92" },
      2: { backgroundColor: "#A5D3EB" },
      3: { backgroundColor: "#70E3B7" },
      4: { backgroundColor: "#BFE680" },
      5: { backgroundColor: "#EFA4A6" },
      6: { backgroundColor: "#EBB68F" },
      7: { backgroundColor: "#C393F2" },
      8: { backgroundColor: "#8AD39A" },
      9: { backgroundColor: "#E5E6A6" },
      10: { backgroundColor: "#8CD4CD" },
      11: { backgroundColor: "#E2989A" },
      12: { backgroundColor: "#ADB5CD" },
      13: { backgroundColor: "#A3E1D7" },
      14: { backgroundColor: "#E3EA7A" },
      15: { backgroundColor: "#D2E08F" },
      16: { backgroundColor: "#94E06B" },
      17: { backgroundColor: "#EAD26D" },
      18: { backgroundColor: "#E5C375" },
      19: { backgroundColor: "#E5866B" },
      20: { backgroundColor: "#9BBFE0" },
    }),
    []
  );
};

export default useStyle;
