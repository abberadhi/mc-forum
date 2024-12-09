import { PlusIcon } from "@heroicons/react/24/solid";
import React, { useState } from "react";
import { CommentModel } from "../models/CommentModel";
import { Button, Textarea } from "@headlessui/react";
import { CommentService } from "../services/CommentService";
import { useParams } from "react-router-dom";

const Comments: React.FC<{ comment: CommentModel; level?: number }> = ({
  comment,
  level = 0,
}) => {
  const [showReply, setShowReply] = useState(false);
  const [commentContent, setCommentContent] = useState<string>("");

  const param: any = useParams();

  const handleShowReply = () => {
    setShowReply(!showReply);
  };

  const handleCommentSubmit = (e: any, commentId: any) => {
    e.preventDefault();
    if (!commentContent) return;

    CommentService.createComment(commentContent, param.id, commentId).then(() =>
      window.location.reload()
    );
  };

  console.log(comment);

  return (
    <div className={`mb-4 ${level > 0 ? "ml-8" : ""}`}>
      <div className="flex items-start space-x-3">
        <img
          src="/profile-avatar.png"
          alt={comment.user.name}
          className="w-10 h-10 rounded-full"
        />
        <div className="flex-1">
          <div className="flex items-center mb-1">
            <span className="font-semibold mr-2">
              {comment.user.name}username
            </span>
            <span className="text-gray-500 text-sm">{comment.date}</span>
          </div>
          <p className="text-gray-700 mb-2">{comment.content}</p>
          <button
            onClick={handleShowReply}
            className="text-bold text-blue-500 text-sm flex items-center"
          >
            <PlusIcon className="w-4 h-4 mr-1" />
            Reply
          </button>
          {showReply ? (
            <form className="my-4 space-y-4">
              <div className="flex items-start space-x-4">
                <Textarea
                  value={commentContent}
                  onChange={(e) => setCommentContent(e.target.value)}
                  placeholder="Write your reply..."
                  className="flex-grow shadow py-2 px-3 border rounded"
                  rows={3}
                />
              </div>
              <div className="flex justify-end">
                <Button
                  onClick={(e) => handleCommentSubmit(e, comment.id)}
                  type="submit"
                >
                  Submit Reply
                </Button>
              </div>
            </form>
          ) : (
            <></>
          )}
        </div>
      </div>
      {comment.replies && (
        <div className="mt-4">
          {comment.replies.map((reply) => (
            <Comments key={reply.id} comment={reply} level={level + 1} />
          ))}
        </div>
      )}
    </div>
  );
};

export default Comments;