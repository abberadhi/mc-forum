export interface CommentModel {
  id: string;
  user: {
    name: string;
    avatar: string;
  };
  content: string;
  date: string;
  replies?: CommentModel[];
}
