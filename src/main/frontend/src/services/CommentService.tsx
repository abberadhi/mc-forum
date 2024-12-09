import api from "./api";

export const CommentService = {
  getCommentByPostId,
  createComment,
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

function commentDTO(comments: any) {
  const map = new Map();

  // First pass: Create basic comment objects and store them in the map
  comments.forEach((comment: any) => {
    map.set(comment.id, {
      id: comment.id.toString(),
      user: comment.username,
      content: comment.commentContent,
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
