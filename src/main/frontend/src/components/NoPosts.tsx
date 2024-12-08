import {
  ChatBubbleBottomCenterIcon,
  HandThumbUpIcon,
} from "@heroicons/react/24/solid";
import { dateFormatter } from "../utils/dateFormatter";

const NoPosts = () => {
  return (
    <div className="container mx-auto p-4">
      <div className="flex flex-col items-center justify-center h-64 bg-white rounded-lg p-8 text-center">
        <div className="text-4xl mb-4">📭</div>
        <h2 className="text-2xl font-semibold text-gray-800 mb-2">
          No posts yet
        </h2>
        <p className="text-gray-600">Check back later for new content!</p>
      </div>
    </div>
  );
};

export default NoPosts;
