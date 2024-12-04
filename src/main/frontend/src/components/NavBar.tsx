import { Button } from "@headlessui/react";
import { Link } from "react-router-dom";

const NavBar = () => {
  return (
    <div>
      <nav className="bg-white border-gray-200 dark:bg-gray-900">
        <div className="flex flex-wrap items-center justify-between mx-auto p-4">
          <Link
            to="/threads"
            className="flex items-center space-x-3 rtl:space-x-reverse"
          >
            <img src="/logo.png" className="h-8" alt="Flowbite Logo" />
            <span className="self-center text-2xl font-semibold whitespace-nowrap dark:text-white">
              BikerHub
            </span>
          </Link>
          <div className="md:block md:w-auto" id="navbar-default">
            <Button className="border-solid	border-2 mx-4 rounded bg-transparent py-2 px-4 text-sm text-white data-[hover]:bg-sky-1000 data-[active]:bg-sky-700">
              Sign up
            </Button>
            <Button className="rounded bg-sky-600 py-2 px-4 text-sm text-white data-[hover]:bg-sky-500 data-[active]:bg-sky-700">
              Sign in
            </Button>
          </div>
        </div>
      </nav>
    </div>
  );
};

export default NavBar;
