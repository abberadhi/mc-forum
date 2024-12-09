import { Button } from "@headlessui/react";
import { useState } from "react";
import { AuthenticationService } from "../services/AuthenticationService";
import { useNavigate } from "react-router-dom";

const SignUpForm = () => {
  const [error, setError] = useState("");
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState<String | null>(null);

  let navigate: any = useNavigate();
  const routeChange = () => {
    let path = `/user/update`;
    navigate(path);
  };

  const handleLogin = (e: any) => {
    e.preventDefault();

    AuthenticationService.register({ username, password })
      .then(() => {
        routeChange();
      })
      .catch((e: any) => {
        if (e.status == 500) {
          setError("Something went wrong. User might already be registered.");
        }
      });
  };

  return (
    <div className="flex flex-row justify-center items-center">
      <div className="m-4 w-full max-w-xs">
        <form className="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4">
          <h1 className="block text-gray-700 text-lg font-bold mb-2">
            Create an account
          </h1>
          <div className="mb-4">
            <label
              className="block text-gray-700 text-sm font-bold mb-2"
              htmlFor="username"
            >
              Username
            </label>
            <input
              className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
              id="username"
              type="text"
              placeholder="Username"
              onChange={(e) => setUsername(e.target.value)}
            />
          </div>
          <div className="mb-6">
            <label
              className="block text-gray-700 text-sm font-bold mb-2"
              htmlFor="password"
            >
              Password
            </label>
            <input
              className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 mb-3 leading-tight focus:outline-none focus:shadow-outline"
              id="password"
              type="password"
              placeholder="******************"
              onChange={(e) => setPassword(e.target.value)}
            />
            {error ? (
              <p className="text-red-500 text-xs italic">{error}</p>
            ) : (
              <></>
            )}
          </div>
          <div className="flex justify-end">
            <Button
              type="submit"
              onClick={handleLogin}
              className="border-solid	border-2 rounded bg-transparent py-2 px-4 text-sm text-black data-[hover]:bg-sky-1000 data-[active]:bg-sky-700"
            >
              Sign up
            </Button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default SignUpForm;
