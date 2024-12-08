import api from "./api";

export const PostService = {
  getPosts,
  getBikeModels,
};

async function getPosts(
  username: string,
  title: string,
  tag: string,
  pageNumber: number
) {
  try {
    const response = await api.get(
      `/posts?${username ? `username=${username}&` : ""}${
        title ? `title=${title}&` : ""
      }${tag ? `tag=${tag}&` : ""}${
        pageNumber ? `pageNumber=${pageNumber}&` : ""
      }`
    );
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
