import { Navigate } from "react-router-dom";
import CreateNewPostForm from "../components/CreateNewPostForm";
import { AuthenticationService } from "../services/AuthenticationService";

const NewPost = () => {
  if (!AuthenticationService.isLoggedIn()) {
    return <Navigate to="/signin" replace />;
  }

  return (
    <>
      <main>
        <CreateNewPostForm />
      </main>
    </>
  );
};

export default NewPost;
