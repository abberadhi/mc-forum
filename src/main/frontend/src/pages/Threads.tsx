import { useEffect, useState } from "react";
import Post from "../components/Post";
import SearchBar from "../components/SearchBar";
import { AuthenticationService } from "../services/AuthenticationService";
import api from "../services/api";

const Threads = () => {
  const [posts, setPosts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<String | null>(null);
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error2, setError2] = useState<String | null>(null);

  useEffect(() => {
    const handleLogin = async () => {
      // e.preventDefault();
      setError(null);

      try {
        setUsername("motorcyclist2");
        setPassword("pass");
        const response = await AuthenticationService.login({
          username,
          password,
        });
        console.log("User Data:", response);
      } catch (err) {
        setError2("An error occurred");
      }
    };

    handleLogin();
  }, []);

  useEffect(() => {
    const fetchPosts = async () => {
      try {
        setLoading(true);
        const response = await api.get("/posts");
        setPosts(response.data);
      } catch (err) {
        setError("Failed to fetch posts. Please try again later.");
      } finally {
        setLoading(false);
      }
    };

    fetchPosts();
  }, []);

  return (
    <>
      <main>
        <div className="flex">
          <div className="flex-1 w-64">
            <SearchBar />

            {loading ? (
              <p>Loading posts...</p>
            ) : error ? (
              <p>{error}</p>
            ) : (
              posts.map((post: any) => <Post key={post.id} post={post} />)
            )}
          </div>
          <div className="flex-initial w-64">side</div>
        </div>
      </main>
    </>
  );
};

export default Threads;
