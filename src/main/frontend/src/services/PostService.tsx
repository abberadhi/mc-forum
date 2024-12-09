import api from "./api";

export const PostService = {
  getPosts,
  createPost,
  getPostById,
};

async function getPosts(
  username: string,
  title: string,
  tag: string,
  pageNumber: number
) {
  try {
    // Build query parameters dynamically
    const queryParams = new URLSearchParams();

    if (username) queryParams.append("username", username);
    if (title) queryParams.append("title", title);
    if (tag) queryParams.append("tag", tag);
    if (pageNumber) queryParams.append("pageNumber", pageNumber.toString());

    const response = await api.get(`/posts?${queryParams.toString()}`);
    const data = response.data;

    return data;
  } catch (error: any) {
    throw error.response?.data || new Error("Fetching posts failed");
  }
}

async function getPostById(id: number) {
  try {
    const response = await api.get(`/posts/${id}`);
    const data = response.data;

    return data;
  } catch (error: any) {
    throw error.response?.data || new Error("Fetching posts failed");
  }
}

async function createPost(title: string, content: string, tags: string[]) {
  try {
    // Build query parameters dynamically
    const response = await api.post(`/posts`, {
      title,
      content,
      tags,
    });
    const data = response.data;

    return data;
  } catch (error: any) {
    throw error.response?.data || new Error("Creating posts failed");
  }
}
