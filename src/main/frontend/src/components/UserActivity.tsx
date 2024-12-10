import { ChatBubbleLeftIcon, ClockIcon } from "@heroicons/react/24/solid";
import React, { useEffect, useState } from "react";
import { dateFormatter } from "../utils/dateFormatter";
import { UserService } from "../services/UserService";
import { useParams } from "react-router-dom";

const UserActivity = () => {
  const [comments, setComments] = useState<any>([]);
  const params: any = useParams();

  useEffect(() => {
    UserService.getUserActivity(params.username).then((data) => {
      setComments(data);
    });
  }, []);

  return (
    <div className="m-4 bg-white shadow-lg rounded-lg overflow-hidden">
      <div className="bg-gray-50 px-4 py-3 border-b border-gray-200">
        <h2 className="text-lg font-semibold text-gray-800">Recent Activity</h2>
      </div>
      <ul className="divide-y divide-gray-200">
        {comments.length == 0 ? (
          <span className="text-sm m-4 p-4 text-gray-800">No activiy</span>
        ) : (
          <></>
        )}
        {comments.map((comment: any) => (
          <li
            key={comment.id}
            className="px-4 py-3 hover:bg-gray-50 transition duration-150 ease-in-out"
          >
            <div className="flex items-start space-x-3">
              <ChatBubbleLeftIcon className="h-6 w-6 text-gray-400" />
              <div className="flex-1 space-y-1">
                <p className="text-sm text-gray-700 line-clamp-2">
                  {comment.commentContent}
                </p>
                <div className="flex items-center text-xs text-gray-500">
                  <ClockIcon className="h-4 w-4 mr-1" />
                  <span>{dateFormatter.dateDistance(comment.createdAt)}</span>
                </div>
              </div>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default UserActivity;
