import axios from "axios";
axios.defaults.baseURL = "http://localhost:8080/api";

interface ConfigData {
  seed?: number;
  algorithm: number;
  process: number;
  operations: number;
}

const API_URL = "/sent/config";
const EXECUTE_STEP_URL = "/execute/step";
const SENT_INSTRUCTIONS = "/sent/instructions";

export const sendConfig = async (configData: ConfigData) => {
  try {
    const response = await axios.post(API_URL, configData);
    return response.data;
  } catch (error) {
    console.error("Error al enviar la configuración:", error);
    throw error;
  }
};

export const executeStep = async () => {
  try {
    const response = await axios.get(EXECUTE_STEP_URL);
    return response.data;
  } catch (error) {
    console.error("Error al ejecutar el siguiente paso:", error);
    throw error;
  }
};

export const sendInstructions = async (data: object) => {
  const response = await axios.post(SENT_INSTRUCTIONS, data);
  return response.data;
};
