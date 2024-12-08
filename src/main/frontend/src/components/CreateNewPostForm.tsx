import React, { useState } from "react";
import { Button } from "@headlessui/react";
import { PostService } from "../services/PostService";
import { useNavigate } from "react-router-dom";
import { AuthenticationService } from "../services/AuthenticationService";

const CreateNewPostForm: React.FC = () => {
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [tags, setTags] = useState<string[]>([]);
  const [currentTag, setCurrentTag] = useState("");
  const [error, setError] = useState("");

  let navigate: any = useNavigate();
  const routeChange = () => {
    let path = `/user/profile/${AuthenticationService.getUserData().username}`;
    navigate(path);
  };

  const handleAddTag = () => {
    if (currentTag && !tags.includes(currentTag)) {
      setTags([...tags, currentTag]);
      setCurrentTag("");
    }
  };

  const handleRemoveTag = (tagToRemove: string) => {
    setTags(tags.filter((tag) => tag !== tagToRemove));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError("");

    if (!title || !content) {
      setError("Please fill in all fields and add at least one tag.");
      return;
    }

    try {
      PostService.createPost(title, content, tags).then(() => routeChange());
    } catch (error) {
      setError("Something went wrong. Please try again.");
    }
  };

  return (
    <div className="flex flex-row justify-center items-center">
      <div className="m-4 w-full max-w-md">
        <form
          onSubmit={handleSubmit}
          className="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4"
        >
          <h1 className="block text-gray-700 text-lg font-bold mb-2">
            Create a New Post
          </h1>
          <div className="mb-4">
            <label
              className="block text-gray-700 text-sm font-bold mb-2"
              htmlFor="title"
            >
              Title
            </label>
            <input
              className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
              id="title"
              type="text"
              placeholder="Enter post title"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              required
            />
          </div>
          <div className="mb-4">
            <label
              className="block text-gray-700 text-sm font-bold mb-2"
              htmlFor="content"
            >
              Content
            </label>
            <textarea
              className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline h-32"
              id="content"
              placeholder="Write your post content here"
              value={content}
              onChange={(e) => setContent(e.target.value)}
              required
            />
          </div>
          <div className="mb-4">
            <label
              className="block text-gray-700 text-sm font-bold mb-2"
              htmlFor="tags"
            >
              Tags
            </label>
            <div className="flex mb-2">
              <input
                className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline mr-2"
                id="tags"
                type="text"
                placeholder="Add a tag"
                value={currentTag}
                onChange={(e) => setCurrentTag(e.target.value)}
              />
              <Button
                type="button"
                onClick={handleAddTag}
                className="border-solid border-2 rounded bg-transparent py-2 px-4 text-sm text-black hover:bg-gray-200 active:bg-gray-300"
              >
                Add Tag
              </Button>
            </div>
            <div className="flex flex-wrap">
              {tags.map((tag) => (
                <span
                  key={tag}
                  className="bg-gray-200 text-gray-700 rounded-full px-3 py-1 text-sm font-semibold mr-2 mb-2"
                >
                  {tag}
                  <button
                    type="button"
                    onClick={() => handleRemoveTag(tag)}
                    className="ml-2 text-gray-500 hover:text-gray-700 focus:outline-none"
                  >
                    &times;
                  </button>
                </span>
              ))}
            </div>
          </div>
          {error && <p className="text-red-500 text-xs italic mb-4">{error}</p>}
          <div className="flex justify-end">
            <Button
              type="submit"
              className="border-solid border-2 rounded bg-transparent py-2 px-4 text-sm text-black hover:bg-sky-100 active:bg-sky-200"
            >
              Create Post
            </Button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default CreateNewPostForm;
