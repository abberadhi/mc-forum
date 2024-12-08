import { Navigate, useParams } from "react-router-dom";
import { AuthenticationService } from "../services/AuthenticationService";
import UserProfileCard from "../components/UserProfileCard";
import SearchBar from "../components/SearchBar";
import { useEffect, useState } from "react";
import Post from "../components/Post";
import { PostService } from "../services/PostService";
import { UserService } from "../services/UserService";
import { UserDataModel } from "../models/UserDataModel";
import NotFound from "./NotFound";
import NoPosts from "../components/NoPosts";

const UserProfile = () => {
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(true);
  const [posts, setPosts] = useState<[]>([]);
  const [user, setUser] = useState<UserDataModel | null>();

  const params: any = useParams();

  useEffect(() => {
    PostService.getPosts(params.username, "", "", 1).then((posts) =>
      setPosts(posts)
    );
  }, [params.username]);

  useEffect(() => {
    if (!params.username) return;
    UserService.getUserDataByUsername(params.username)
      .then((user) => {
        setUser(user);
        setLoading(false);
      })
      .catch(() => {
        return <Navigate to="/404" replace />;
      });
  }, [params.username]);

  if (!AuthenticationService.isLoggedIn()) {
    return <Navigate to="/signin" replace />;
  }

  if (!user && !loading) {
    return <NotFound />;
  }

  return (
    <>
      <main className="flex">
        <div className="w-96">
          <UserProfileCard profile={user}></UserProfileCard>
        </div>
        <div className="flex-1 w-64">
          <SearchBar />

          {posts.length > 0 ? (
            posts?.map((post: any) => <Post key={post.id} post={post} />)
          ) : (
            <NoPosts />
          )}
        </div>
      </main>
    </>
  );
};

export default UserProfile;
