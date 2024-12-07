import { CalendarIcon, CheckIcon } from "@heroicons/react/24/solid";
import React from "react";

const UserProfileCard = ({ profile }: any) => {
  const formatDate = (dateString: string) => {
    const date = new Date(dateString);
    return date.toLocaleDateString("en-US", {
      year: "numeric",
      month: "long",
      day: "numeric",
    });
  };

  if (!profile) return <></>;
  return (
    <div className="m-4 bg-white shadow-lg rounded-lg overflow-hidden">
      <div className="flex flex-col items-center pt-8 pb-4">
        <img
          src="/profile-avatar.png"
          alt={`${profile.username}'s avatar`}
          width={100}
          height={100}
          className="rounded-full border-4 border-white shadow-lg"
        />
      </div>
      <div className="px-6 py-4">
        <div className="font-bold text-xl mb-2 text-center">
          {profile.username}
        </div>
        <p className="text-gray-700 text-base mb-4">{profile.description}</p>
        <div className="flex items-center text-sm text-gray-600 mb-2">
          <CalendarIcon className="h-4 w-4 mr-2" />
          <span>Joined {formatDate(profile.dateJoined)}</span>
        </div>
        {profile.bike != null ? (
          <div className="flex items-center text-sm text-gray-600">
            <CheckIcon className="h-4 w-4 mr-2" />
            <span>
              {profile.bike.brand} {profile.bike.model}
            </span>
          </div>
        ) : (
          <></>
        )}
      </div>
    </div>
  );
};

export default UserProfileCard;
