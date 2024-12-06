import { Bike } from "./Bike";

export interface UserDataModel {
  id: number;
  username: string;
  description: string;
  dateJoined: string; // ISO date as string
  bike: Bike;
}
