export interface CommentModel {
  id: string;
  user: string;
  content: string;
  date: string;
  replies?: CommentModel[];
}
