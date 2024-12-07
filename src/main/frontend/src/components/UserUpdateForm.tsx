import {
  Button,
  Description,
  Field,
  Label,
  Select,
  Textarea,
} from "@headlessui/react";
import { useEffect, useState } from "react";
import { AuthenticationService } from "../services/AuthenticationService";
import { useNavigate } from "react-router-dom";
import { clsx } from "clsx";
import { ChevronDownIcon } from "@heroicons/react/24/solid";
import { BikeService } from "../services/BikeService";
import { UserService } from "../services/UserService";
import { UserDataModel } from "../models/UserDataModel";

const UserUpdateForm = () => {
  const [error, setError] = useState("");
  const [allBikeBrands, setAllBikeBrands] = useState<[] | null>(null);
  const [allBikeModels, setAllBikeModels] = useState<[] | null>(null);
  const [bikeBrand, setBikeBrand] = useState<String | null>(null);
  const [bikeModel, setBikeModel] = useState<any>(null);
  const [description, setDescription] = useState<string>("");
  const [userData, setUserData] = useState<UserDataModel>();

  let navigate: any = useNavigate();
  const routeChange = () => {
    let path = `/user/profile/${AuthenticationService.getUserData().username}`;
    navigate(path);
  };

  const handleSubmit = (e: any) => {
    e.preventDefault();

    if (!validateDetails()) return;

    const userId: number = AuthenticationService.getUserData().id;
    const requestData = {
      description: description,
      bikeModelEntity: bikeModel,
    };

    UserService.updateUserData(userId, requestData)
      .then(() => {
        routeChange();
      })
      .catch((e: any) => {
        if (e.status == 500) {
          setError("Something went wrong.");
        }
      });
  };

  const handleBikeBrandChange = (e: any) => {
    setBikeBrand(e.target.value);
    setError("");
  };

  const handleBikeModelChange = (e: any) => {
    let bikeId = e.target.value;
    let bike = allBikeModels?.find((b: any) => b.id == bikeId);

    setError("");
    setBikeModel(bike);
  };

  const validateDetails = () => {
    // Validate bike model picked
    if (
      (!bikeModel || bikeModel == null || bikeModel == "none") &&
      bikeBrand &&
      bikeBrand != "none"
    ) {
      setError("Please choose the bike model from the list.");
      return false;
    }
    return true;
  };

  useEffect(() => {
    BikeService.getBikeBrands().then((response) =>
      setAllBikeBrands(response.sort())
    );

    const userId: number = AuthenticationService.getUserData().id;
    UserService.getUserDataById(userId).then((data) => {
      setUserData(data);
      setDescription(data.description);
      if (!data.bike) return;
      setBikeModel(data.bike);
      setBikeBrand(data.bike.brand);
    });
  }, []);

  useEffect(() => {
    if (bikeBrand == null) return;
    BikeService.getBikeModels(bikeBrand).then((response) => {
      setAllBikeModels(response.sort());
    });
  }, [bikeBrand]);

  return (
    <div className="flex flex-row justify-center items-center">
      <div className="m-4 w-full max-w-lg">
        <form className="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4">
          <h1 className="block text-gray-700 text-xl font-bold mb-2">
            Update your information
          </h1>
          <Field>
            <Label className="text-sm/6 font-medium text-black">
              Description
            </Label>
            <Description className="text-sm/6 text-black/50">
              This will be shown under your user profile.
              <br />
            </Description>
            <Textarea
              defaultValue={userData?.description}
              onChange={(e) => setDescription(e.target.value)}
              className={clsx(
                "mt-3 block w-full resize-none rounded-lg border outline-2 bg-white/5 py-1.5 px-3 text-sm/6 text-black",
                "focus:outline-none data-[focus]:outline-2 data-[focus]:-outline-offset-2 data-[focus]:outline-black/25"
              )}
              rows={3}
            />
          </Field>
          <Field className="pt-4">
            <Label className="text-sm/6 font-medium text-black">
              What do you ride?
            </Label>
            <Description className="text-sm/6 text-black/50">
              This will be visible to other users on your profile.
            </Description>
            <div className="flex flex-row">
              <div className="basis-1/2 mr-2">
                <div className="relative">
                  <Select
                    onChange={handleBikeBrandChange}
                    className={clsx(
                      "mt-3 block w-full appearance-none rounded-lg border bg-black/5 py-1.5 px-3 text-sm/6 text-black",
                      "focus:outline-none data-[focus]:outline-2 data-[focus]:-outline-offset-2 data-[focus]:outline-white/25",
                      // Make the text of each option black on Windows
                      "*:text-black"
                    )}
                  >
                    {bikeModel ? (
                      <option selected={true}>{bikeModel.brand}</option>
                    ) : (
                      <></>
                    )}
                    <option value="none">none</option>
                    {allBikeBrands?.map((element) => {
                      return (
                        <option key={element} value={element}>
                          {element}
                        </option>
                      );
                    })}
                  </Select>
                  <ChevronDownIcon
                    className="group pointer-events-none absolute top-2.5 right-2.5 size-4 fill-black/60"
                    aria-hidden="true"
                  />
                </div>
              </div>
              <div className="basis-1/2 ml-2">
                <div className="relative">
                  <Select
                    disabled={!bikeBrand || bikeBrand == "none" ? true : false}
                    onChange={handleBikeModelChange}
                    className={clsx(
                      "mt-3 block w-full appearance-none rounded-lg border bg-black/5 py-1.5 px-3 text-sm/6 text-black",
                      "focus:outline-none data-[focus]:outline-2 data-[focus]:-outline-offset-2 data-[focus]:outline-white/25",
                      "*:text-black",
                      "data-[disabled]:bg-gray-100"
                    )}
                  >
                    {bikeModel ? (
                      <option selected={true}>{bikeModel.model}</option>
                    ) : (
                      <></>
                    )}
                    <option value="none">none</option>
                    {allBikeModels?.map((element: any) => {
                      return (
                        <option key={element.id} value={element.id}>
                          {element.model}
                        </option>
                      );
                    })}
                  </Select>
                  <ChevronDownIcon
                    className="group pointer-events-none absolute top-2.5 right-2.5 size-4 fill-black/60"
                    aria-hidden="true"
                  />
                </div>
              </div>
            </div>
          </Field>
          {error ? (
            <p className="text-red-500 pt-2 text-xs italic">{error}</p>
          ) : (
            <></>
          )}

          <div className="flex justify-end pt-4">
            <Button
              type="submit"
              onClick={handleSubmit}
              className="border-solid	border-2 rounded bg-transparent py-2 px-4 text-sm text-black data-[hover]:bg-sky-1000 data-[active]:bg-sky-700"
            >
              Apply
            </Button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default UserUpdateForm;
