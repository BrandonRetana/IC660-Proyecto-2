import axios from "axios";
axios.defaults.baseURL = "http://localhost:8080/api";

interface ConfigData {
  generationMethod: string;
  seed?: string | number;
  algorithm: number;
  processes: number;
  operations: number;
}

const API_URL = "/sent/config";

export const sendConfig = async (configData: ConfigData) => {
  try {
    const response = await axios.post(API_URL, configData);
    return response.data;
  } catch (error) {
    console.error("Error al enviar la configuración:", error);
    throw error;
  }
};
