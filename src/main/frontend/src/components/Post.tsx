import {
  ChatBubbleBottomCenterIcon,
  HandThumbUpIcon,
} from "@heroicons/react/24/solid";

const Post = () => {
  return (
    <div className="m-4 p-4 flex flex-row justify-center items-center">
      <div className=" bg-white max-w-full rounded overflow-hidden shadow-lg">
        <div className="flex px-6 pt-4">
          <div></div>
          <div className="flex items-center">
            <div
              style={{
                borderRadius: "50%",
                width: 45,
                height: 45,
                background: "red",
                display: "block",
              }}
              className="w-1/2 h-10 rounded-full bg-black flex-none"
            ></div>
            <div className="px-2">Username</div>
            <div className="px-2 text-gray text-xs">1 hour ago</div>
          </div>
        </div>
        <div className="px-6 py-4">
          <div className="font-bold text-xl mb-2">The Coldest Sunset</div>
          <p className="text-gray-700 text-base">
            Lorem ipsum dolor sit amet, consectetur adipisicing elit.
            Voluptatibus quia, nulla! Maiores et perferendis eaque,
            exercitationem praesentium nihil.
          </p>
        </div>
        <div className="px-6 pb-2">
          <span className="inline-block bg-gray-200 rounded-full px-3 py-1 text-sm font-semibold text-gray-700 mr-2 mb-2">
            #photography
          </span>
          <span className="inline-block bg-gray-200 rounded-full px-3 py-1 text-sm font-semibold text-gray-700 mr-2 mb-2">
            #travel
          </span>
          <span className="inline-block bg-gray-200 rounded-full px-3 py-1 text-sm font-semibold text-gray-700 mr-2 mb-2">
            #winter
          </span>
        </div>

        <div className="px-6 pb-2">
          <span className="inline-block bg-gray-200 rounded-full px-3 py-1 text-sm font-semibold text-gray-700 mr-2 mb-2">
            <div className="flex items-center	">
              <HandThumbUpIcon className="size-6 text-white" />
              <p className="px-1 text-white">1</p>
            </div>
          </span>
          <span className="inline-block bg-gray-200 rounded-full px-3 py-1 text-sm font-semibold text-gray-700 mr-2 mb-2">
            <div className="flex items-center	">
              <ChatBubbleBottomCenterIcon className="size-6 text-white" />
              <p className="px-1 text-white">1</p>
            </div>
          </span>
        </div>
      </div>
    </div>
  );
};

export default Post;
