import api from "./api";
import { jwtDecode } from "jwt-decode";
import { UserService } from "./UserService";
import { UserDataModel } from "../models/UserDataModel";

export const AuthenticationService = {
  login,
  register,
  getToken,
  getUserData,
  isLoggedIn,
  logout,
};

async function login(credentials: any) {
  if (credentials.password === "" || credentials.password === null) return;
  if (credentials.username === "" || credentials.username === null) return;

  try {
    const response = await api.post("/auth/authenticate", credentials);
    const { token } = response.data;
    localStorage.setItem("jwtToken", token);
    let userData = JSON.stringify(
      await UserService.getUserDataByUsername(getUsernameFromToken(token))
    );

    localStorage.setItem("userData", userData);

    return response.data;
  } catch (error: any) {
    throw error.response?.data || new Error("Login failed");
  }
}

async function register(credentials: any) {
  if (credentials.password === "" || credentials.password === null) return;
  if (credentials.username === "" || credentials.username === null) return;

  try {
    const response = await api.post("/auth/register", credentials);
    const { token } = response.data;

    localStorage.setItem("jwtToken", token);

    let userData = JSON.stringify(
      await UserService.getUserDataByUsername(getUsernameFromToken(token))
    );

    localStorage.setItem("userData", userData);

    return response.data;
  } catch (error: any) {
    throw error.response?.data || new Error("Register failed");
  }
}

function isLoggedIn(): boolean {
  let token: any = localStorage.getItem("jwtToken");

  // token exists
  if (!token) {
    return false;
  }
  let decodedToken: any = jwtDecode(token);
  let currentDate = new Date();

  // token expired
  if (decodedToken.exp * 1000 < currentDate.getTime()) {
    return false;
  }

  return true;
}

function getUsernameFromToken(token: any) {
  let decodedToken: any = jwtDecode(token);
  return decodedToken.sub;
}

function getToken() {
  return localStorage.getItem("jwtToken");
}

function getUserData(): UserDataModel {
  return JSON.parse(localStorage.getItem("userData") ?? "");
}

function logout() {
  localStorage.removeItem("jwtToken");
  localStorage.removeItem("userData");
}
