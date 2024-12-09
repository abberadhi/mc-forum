import { useEffect, useState } from "react";
import { PostService } from "../services/PostService";
import { Navigate, useParams } from "react-router-dom";
import Post from "../components/Post";
import { PostDataModel } from "../models/PostDataModel";
import CommentSection from "../components/CommentSection";
import CommentInput from "../components/CommentInput";
import { AuthenticationService } from "../services/AuthenticationService";

const PostDetail = () => {
  const [post, setPost] = useState<PostDataModel | null>(null);

  const param: any = useParams();
  useEffect(() => {
    PostService.getPostById(param.id).then((post) => {
      setPost(post);
    });
  }, []);

  if (!AuthenticationService.isLoggedIn()) {
    return <Navigate to="/signin" replace />;
  }

  return (
    <>
      <main className="flex flex-col">
        <div>{post ? <Post post={post}></Post> : <></>}</div>
        <div>
          <CommentInput></CommentInput>
        </div>
        <div>
          <CommentSection></CommentSection>
        </div>
      </main>
    </>
  );
};

export default PostDetail;
