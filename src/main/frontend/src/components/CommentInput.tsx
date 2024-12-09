import { PlusIcon } from "@heroicons/react/24/solid";
import React, { useEffect, useState } from "react";
import { CommentModel } from "../models/CommentModel";
import { Button, Textarea } from "@headlessui/react";
import { CommentService } from "../services/CommentService";
import { useParams } from "react-router-dom";

const CommentInput = () => {
  const [commentContent, setCommentContent] = useState<string>("");

  const param: any = useParams();

  const handleCommentSubmit = (e: any) => {
    e.preventDefault();
    if (!commentContent) return;

    CommentService.createComment(commentContent, param.id).then(() =>
      window.location.reload()
    );
  };

  return (
    <div className="mx-4 px-4 flex flex-row justify-center items-center">
      <div className="w-full bg-white max-w-full rounded p-4 overflow-hidden shadow-lg">
        <form className="my-4 space-y-4">
          <div className="flex items-start space-x-4">
            <Textarea
              value={commentContent}
              onChange={(e) => setCommentContent(e.target.value)}
              placeholder="Write comment on this post..."
              className="flex-grow shadow py-2 px-3 border rounded"
              rows={3}
            />
          </div>
          <div className="flex justify-end">
            <Button onClick={handleCommentSubmit} type="submit">
              Send Comment
            </Button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default CommentInput;
