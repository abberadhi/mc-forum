import { UserDataModel } from "../models/UserDataModel";
import api from "./api";

export const UserService = {
  getUserDataById,
  getUserDataByUsername,
  updateUserData,
  getUserActivity,
};

async function getUserDataById(id: any): Promise<UserDataModel> {
  try {
    const response = await api.get(`/user/${id}`);
    const data = response.data;

    return data;
  } catch (error: any) {
    throw error.response?.data || new Error("Register failed");
  }
}

async function getUserDataByUsername(username: string | any) {
  try {
    const response = await api.get(`/user/details/${username}`);
    const data = response.data;

    return data;
  } catch (error: any) {
    throw error.response?.data || new Error("Register failed");
  }
}

async function updateUserData(id: any, userData: any) {
  try {
    const response: any = await api.patch(`/user/${id}`, userData);
    const data: any = response.data;

    return data;
  } catch (error: any) {
    throw error.response?.data || new Error("Register failed");
  }
}

async function getUserActivity(username: string) {
  let data: any;
  try {
    const response = await api.get(`/user/activity/${username}`);
    data = response.data;
  } catch (error: any) {
    throw error.response?.data || new Error("Fetching posts failed");
  }

  return data;
}
