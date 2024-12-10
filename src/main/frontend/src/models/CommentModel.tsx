export interface CommentModel {
  id: string;
  user: string;
  content: string;
  date: string;
  upvoteCount: number;
  hasLiked: boolean;
  replies?: CommentModel[];
}
