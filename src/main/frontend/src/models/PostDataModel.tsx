import { TagModel } from "./TagModel";

export interface PostDataModel {
  id: number;
  title: string;
  content: string;
  createdAt: string; // ISO date as string
  updatedAt: string | null; // ISO date as string or null
  tags: TagModel[];
  authorId: number;
  authorUserName: string;
}
