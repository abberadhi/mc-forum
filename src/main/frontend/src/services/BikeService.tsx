import api from "./api";

export const BikeService = {
  getBikeBrands,
  getBikeModels,
};

async function getBikeBrands() {
  try {
    const response = await api.get("/bikes/brands");
    const data = response.data;

    return data;
  } catch (error: any) {
    throw error.response?.data || new Error("Register failed");
  }
}

async function getBikeModels(brand: String) {
  try {
    const response = await api.get(`/bikes/models/${brand}`);
    const data = response.data;

    return data;
  } catch (error: any) {
    throw error.response?.data || new Error("Register failed");
  }
}
