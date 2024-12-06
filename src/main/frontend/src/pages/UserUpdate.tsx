import { Navigate } from "react-router-dom";
import SignUpForm from "../components/SignUpForm";
import UserUpdateForm from "../components/UserUpdateForm";
import { AuthenticationService } from "../services/AuthenticationService";

const UserUpdate = () => {
  if (!AuthenticationService.isLoggedIn()) {
    return <Navigate to="/signin" replace />;
  }
  return (
    <>
      <main>
        <UserUpdateForm />
      </main>
    </>
  );
};

export default UserUpdate;
