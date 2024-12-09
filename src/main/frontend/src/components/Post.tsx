import {
  ChatBubbleBottomCenterIcon,
  HandThumbUpIcon,
} from "@heroicons/react/24/solid";
import { dateFormatter } from "../utils/dateFormatter";
import { Link } from "react-router-dom";

const Post = ({ post }: any) => {
  return (
    <div className="mx-4 p-4 flex flex-row justify-center items-center">
      <div className="w-full bg-white max-w-full rounded overflow-hidden shadow-lg">
        <div className="flex px-6 pt-4">
          <Link to={`/user/profile/${post.authorUserName}`}>
            <div className="flex items-center">
              <img
                src="/profile-avatar.png"
                alt={`avatar`}
                width={35}
                height={35}
                className="rounded-full border-4 border-white shadow-lg"
              />
              <div className="px-2">{post.authorUserName}</div>
              <div className="px-2 text-gray text-xs">
                {dateFormatter.dateDistance(post.createdAt)}
              </div>
            </div>
          </Link>
        </div>
        <div className="px-6 py-4">
          <div className="font-bold text-xl mb-2">{post.title}</div>
          <p className="text-gray-700 text-base">{post.content}</p>
        </div>
        <div className="px-6 pb-2">
          {post.tags.map((tag: any, i: number) => (
            <span
              key={i}
              className="inline-block bg-gray-200 rounded-full px-3 py-1 text-sm font-semibold text-gray-700 mr-2 mb-2"
            >
              {tag.name}
            </span>
          ))}
        </div>

        <div className="px-6 pb-2">
          <span className="inline-block bg-gray-200 rounded-full px-3 py-1 text-sm font-semibold text-gray-700 mr-2 mb-2">
            <div className="flex items-center	">
              <HandThumbUpIcon className="size-6 text-white" />
              <p className="px-1 text-white">1</p>
            </div>
          </span>
          <Link to={`/post/${post.id}`}>
            <span className="inline-block bg-gray-200 rounded-full px-3 py-1 text-sm font-semibold text-gray-700 mr-2 mb-2">
              <div className="flex items-center	">
                <ChatBubbleBottomCenterIcon className="size-6 text-white" />
                <p className="px-1 text-white">{post.commentCount}</p>
              </div>
            </span>
          </Link>
        </div>
      </div>
    </div>
  );
};

export default Post;
