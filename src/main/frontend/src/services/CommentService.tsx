import api from "./api";

export const CommentService = {
  getCommentByPostId,
  createComment,
  likeCommentById,
};

async function getCommentByPostId(id: number) {
  let data: any;
  try {
    const response = await api.get(`/comments/post/${id}`);
    data = response.data;
  } catch (error: any) {
    throw error.response?.data || new Error("Fetching posts failed");
  }

  return commentDTO(data);
}

async function createComment(
  commentContent: string,
  postId: number,
  commentId: number | null = null
) {
  let data: any;
  try {
    const response = await api.post(
      `/comments/reply/${postId}${commentId ? `/${commentId}` : ""}`,
      { commentContent }
    );
    data = response.data;
  } catch (error: any) {
    throw error.response?.data || new Error("Fetching posts failed");
  }

  return data;
}

async function likeCommentById(id: string, likeAdded: boolean) {
  try {
    const response = await api.post(
      `/comments/${id}/${!likeAdded ? "un" : ""}like`
    );
    const data = response.data;

    return data;
  } catch (error: any) {
    throw error.response?.data || new Error("Fetching posts failed");
  }
}

function commentDTO(comments: any) {
  const map = new Map();

  // First pass: Create basic comment objects and store them in the map
  comments.forEach((comment: any) => {
    map.set(comment.id, {
      id: comment.id.toString(),
      user: comment.username,
      content: comment.commentContent,
      upvoteCount: comment.upvoteCount,
      hasLiked: comment.hasLiked,
      date: new Date(comment.createdAt).toISOString(),
      replies: [],
    });
  });

  // Second pass: Link replies to their parent comments
  let result: any = [];
  comments.forEach((comment: any) => {
    if (comment.parent_id) {
      const parent = map.get(comment.parent_id);
      if (parent) {
        parent.replies.push(map.get(comment.id));
      }
    } else {
      result.push(map.get(comment.id));
    }
  });

  return result;
}
