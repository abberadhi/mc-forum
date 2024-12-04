import { Route, Routes } from "react-router-dom";
import NavBar from "./components/NavBar";
import Post from "./components/Post";
import Threads from "./pages/Threads";
import SignUp from "./pages/SignUp";
import SignIn from "./pages/SignIn";

const App = () => {
  return (
    <>
      <NavBar />
      <Routes>
        <Route path="/threads" element={<Threads />} />
        <Route path="/signup" element={<SignUp />} />
        <Route path="/signin" element={<SignIn />} />
        {/* add 404 page */}
        {/* <Route path="*" element={<Post />} /> */}
      </Routes>
    </>
  );
};

export default App;
