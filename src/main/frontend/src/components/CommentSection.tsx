import { PlusIcon } from "@heroicons/react/24/solid";
import React, { useEffect, useState } from "react";
import { CommentModel } from "../models/CommentModel";
import Comments from "./Comments";
import { CommentService } from "../services/CommentService";
import { useParams } from "react-router-dom";

// const comments: CommentModel[] = [
//   {
//     id: "1",
//     user: {
//       name: "Alice Johnson",
//       avatar: "/placeholder.svg?height=40&width=40",
//     },
//     content: "This is a great post! Thanks for sharing.",
//     date: "2023-05-15",
//     replies: [
//       {
//         id: "2",
//         user: {
//           name: "Bob Smith",
//           avatar: "/placeholder.svg?height=40&width=40",
//         },
//         content: "I agree, very informative!",
//         date: "2023-05-16",
//         replies: [
//           {
//             id: "3",
//             user: {
//               name: "Charlie Brown",
//               avatar: "/placeholder.svg?height=40&width=40",
//             },
//             content: "Thanks for the additional insights, Bob!",
//             date: "2023-05-17",
//             replies: [
//               {
//                 id: "3",
//                 user: {
//                   name: "Charlie Brown",
//                   avatar: "/placeholder.svg?height=40&width=40",
//                 },
//                 content: "Thanks for the additional insights, Bob!",
//                 date: "2023-05-17",
//               },
//             ],
//           },
//         ],
//       },
//     ],
//   },
//   {
//     id: "4",
//     user: {
//       name: "Diana Prince",
//       avatar: "/placeholder.svg?height=40&width=40",
//     },
//     content: "I have a question about this topic. Can anyone help?",
//     date: "2023-05-18",
//   },
// ];

const CommentSection = () => {
  const [comments, setComments] = useState<CommentModel[]>([]);

  const param: any = useParams();

  useEffect(() => {
    CommentService.getCommentByPostId(param.id).then((data) => {
      setComments(data);
    });
  }, []);

  return (
    <>
      <div className="mx-4 p-4 flex flex-row justify-center items-center">
        <div className="w-full bg-white max-w-full rounded p-4 overflow-hidden shadow-lg">
          {comments.length > 0 ? (
            comments.map((comment: any) => (
              <Comments key={comment.id} comment={comment} />
            ))
          ) : (
            <></>
          )}
        </div>
      </div>
    </>
  );
};

export default CommentSection;
