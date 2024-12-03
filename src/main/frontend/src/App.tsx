import { Route, Routes } from "react-router-dom";
import NavBar from "./components/NavBar";
import Post from "./components/Post";
import Threads from "./pages/Threads";

const App = () => {
  return (
    <>
      <NavBar />
      <Routes>
        <Route path="/posts" element={<Post />} />
        <Route path="/threads" element={<Threads />} />
        {/* add 404 page */}
        <Route path="*" element={<Post />} />
      </Routes>
    </>
  );
};

export default App;
