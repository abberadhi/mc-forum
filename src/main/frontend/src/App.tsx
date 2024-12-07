import { Route, Routes } from "react-router-dom";
import NavBar from "./components/NavBar";
import Post from "./components/Post";
import Threads from "./pages/Threads";
import SignUp from "./pages/SignUp";
import SignIn from "./pages/SignIn";
import UserUpdate from "./pages/UserUpdate";
import UserProfile from "./pages/UserProfile";
import NotFound from "./pages/NotFound";

const App = () => {
  return (
    <>
      <NavBar />
      <Routes>
        <Route path="/threads" element={<Threads />} />
        <Route path="/signup" element={<SignUp />} />
        <Route path="/signin" element={<SignIn />} />
        <Route path="/user/profile/:id" element={<UserProfile />} />
        <Route path="/user/update/" element={<UserUpdate />} />
        <Route path="/404" element={<NotFound />} />
        {/* add 404 page */}
        {/* <Route path="*" element={<Post />} /> */}
      </Routes>
    </>
  );
};

export default App;
