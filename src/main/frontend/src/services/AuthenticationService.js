import api from "./api";

export const AuthenticationService = {
  login,
  getToken,
  logout,
};


async function login(credentials) {
    if (credentials.password == "" || credentials.password == null) {
        return;
    }
  try {
    const response = await api.post("/auth/authenticate", credentials);
    const { token } = response.data;

    // Save the JWT token to localStorage
    localStorage.setItem("jwtToken", token);

    return response.data;
  } catch (error) {
    throw error.response?.data || new Error("Login failed");
  }
}

function getToken() {
  return localStorage.getItem("jwtToken");
}

function logout() {
  localStorage.removeItem("jwtToken");
}
