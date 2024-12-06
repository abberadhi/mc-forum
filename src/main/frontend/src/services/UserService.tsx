import { UserDataModel } from "../models/UserDataModel";
import api from "./api";

export const UserService = {
  getUserDataById,
  getUserDataByUsername,
  updateUserData,
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

async function getUserDataByUsername(username: string) {
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
