import api from "./api";

export const PostService = {
  getPosts,
  getBikeModels,
};

async function getPosts() {
  try {
    const response = await api.get("/posts");
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
