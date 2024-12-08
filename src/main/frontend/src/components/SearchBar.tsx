import { Button } from "@headlessui/react";
import { MagnifyingGlassIcon, PlusIcon } from "@heroicons/react/24/solid";
import { useState } from "react";
import { useNavigate } from "react-router-dom";

const SearchBar = ({ onSearchChanged }: any) => {
  const searchChanged = (e: any) => {
    onSearchChanged(e.target.value);
  };

  let navigate: any = useNavigate();
  const routeChange = () => {
    let path = `/post/new`;
    navigate(path);
  };

  return (
    <div className="rounded overflow-hidden shadow-lg bg-white mx-8 mt-4 p-4">
      <div className="flex items-center justify-between">
        <div className="relative flex-grow max-w-lg">
          <input
            onChange={searchChanged}
            type="text"
            placeholder="Search..."
            className="w-full pl-10 pr-4 py-2 rounded-lg border border-gray-300 focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent"
            aria-label="Search"
          />
          <MagnifyingGlassIcon
            className="absolute left-3 top-1/2 transform -translate-y-1/2 h-5 w-5 text-gray-400"
            aria-hidden="true"
          />
        </div>

        <Button
          onClick={routeChange}
          className="flex border-solid	border-2 rounded bg-transparent py-2 px-4 text-sm text-black data-[hover]:bg-sky-1000 data-[active]:bg-sky-700"
        >
          <PlusIcon className="h-5 w-5"></PlusIcon>
          <span>Create Post</span>
        </Button>
      </div>
    </div>
  );
};

export default SearchBar;
