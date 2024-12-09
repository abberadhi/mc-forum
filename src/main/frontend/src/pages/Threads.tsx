import { useEffect, useState } from "react";
import Post from "../components/Post";
import SearchBar from "../components/SearchBar";
import { AuthenticationService } from "../services/AuthenticationService";
import api from "../services/api";
import { Navigate } from "react-router-dom";
import { PostService } from "../services/PostService";

const Threads = () => {
  const [posts, setPosts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [searchTerm, setSearchTerm] = useState<string>("");

  const onSearchChanged = (data: string) => {
    setSearchTerm(data);
  };

  useEffect(() => {
    setLoading(true);

    const tag = searchTerm.startsWith("#") ? searchTerm.substring(1) : "";

    PostService.getPosts("", !tag ? searchTerm : "", tag, 1)
      .then((posts) => {
        setPosts(posts);
        setLoading(false);
      })
      .catch(() => setLoading(false)); // Handle errors to prevent infinite loading
  }, [searchTerm]);

  if (!AuthenticationService.isLoggedIn()) {
    return <Navigate to="/signin" replace />;
  }

  return (
    <>
      <main>
        <div className="flex">
          <div className="flex-1 w-64">
            <SearchBar onSearchChanged={onSearchChanged} />

            {loading ? (
              <p>Loading posts...</p>
            ) : error ? (
              <p>{error}</p>
            ) : (
              posts.map((post: any) => <Post key={post.id} post={post} />)
            )}
          </div>
          {/* <div className="flex-initial w-64">side</div> */}
        </div>
      </main>
    </>
  );
};

export default Threads;
