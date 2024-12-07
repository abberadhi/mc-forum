import {
  Button,
  Popover,
  PopoverButton,
  PopoverPanel,
} from "@headlessui/react";
import { Link, useNavigate } from "react-router-dom";
import { AuthenticationService } from "../services/AuthenticationService";

const NavBar = () => {
  let navigate = useNavigate();
  const routeChange = (path: any) => {
    navigate(path);
  };

  const logout = () => {
    AuthenticationService.logout();

    routeChange("/signin");
  };

  const redirectEditDetails = () => {
    routeChange("/user/update");
  };

  const redirectUserProfile = () => {
    const userId = AuthenticationService.getUserData().id;

    routeChange(`/user/profile/${userId}`);
  };

  return (
    <div>
      <nav className="bg-white border-gray-200 dark:bg-gray-900">
        <div className="flex flex-wrap items-center justify-between mx-auto p-4">
          <Link
            to="/threads"
            className="flex items-center space-x-3 rtl:space-x-reverse"
          >
            <img src="/logo.png" className="h-8" alt="username logo" />
            <span className="self-center text-2xl font-semibold whitespace-nowrap dark:text-white">
              BikerHub
            </span>
          </Link>
          <div className="md:block md:w-auto" id="navbar-default">
            {!AuthenticationService.isLoggedIn() ? (
              <>
                <Button
                  onClick={() => routeChange("signup")}
                  className="border-solid	border-2 mx-4 rounded bg-transparent py-2 px-4 text-sm text-white data-[hover]:bg-sky-1000 data-[active]:bg-sky-700"
                >
                  Sign up
                </Button>
                <Button
                  onClick={() => routeChange("signin")}
                  className="rounded bg-sky-600 py-2 px-4 text-sm text-white data-[hover]:bg-sky-500 data-[active]:bg-sky-700"
                >
                  Sign in
                </Button>
              </>
            ) : (
              <>
                <div className="flex text-white text-sm">
                  <Popover>
                    <PopoverButton className="flex items-center block text-sm/6 font-semibold text-white/50 focus:outline-none data-[active]:text-white data-[hover]:text-white data-[focus]:outline-1 data-[focus]:outline-white">
                      <img
                        src="/profile-avatar.png"
                        alt={`avatar`}
                        width={30}
                        height={30}
                        className="rounded-full border-4 border-white shadow-lg"
                      />
                      <span className="px-2">
                        {AuthenticationService.getUserData().username}
                      </span>
                    </PopoverButton>
                    <PopoverPanel
                      transition
                      anchor="bottom"
                      className="divide-y bg-gray-900 rounded-xl bg-black/5 text-sm/6 transition duration-200 ease-in-out [--anchor-gap:var(--spacing-5)] data-[closed]:-translate-y-1 data-[closed]:opacity-0"
                    >
                      <div className="p-3">
                        <button
                          className="block rounded-lg py-2 px-3 transition hover:bg-white/5"
                          onClick={redirectUserProfile}
                        >
                          <p className="font-semibold text-white">Profile</p>
                        </button>
                        <button
                          className="block rounded-lg py-2 px-3 transition hover:bg-white/5"
                          onClick={redirectEditDetails}
                        >
                          <p className="font-semibold text-white">
                            Edit details
                          </p>
                        </button>
                        <button
                          className="block rounded-lg py-2 px-3 transition hover:bg-white/5"
                          onClick={logout}
                        >
                          <p className="font-semibold text-white">Log out</p>
                        </button>
                      </div>
                    </PopoverPanel>
                  </Popover>
                </div>
              </>
            )}
          </div>
        </div>
      </nav>
    </div>
  );
};

export default NavBar;
