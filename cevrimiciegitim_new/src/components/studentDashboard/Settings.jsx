import React, { useState, useEffect } from "react";
import { getUser } from "../../api/sign";
import { updateUser } from "../../api/sign";
import { formatDate } from "../../api/sign";

const Settings = () => {
  const [userData, setUserData] = useState([]);
  const token = localStorage.getItem("token");

  const fetchData = async () => {
    const data1 = await getUser(token);
    setUserData({
      firstName: data1.name.split(" ")[0],
      lastName: data1.name.split(" ")[1],
      dateOfBirth: data1.dateOfBirth,
      gender: data1.gender,
      location: data1.location,
      phoneNumber: data1.phoneNumber,
      email: data1.email,
      profession: "Engineer",
      photoLink: data1.photo,
      coverImage:
        "https://images.unsplash.com/photo-1451187580459-43490279c0fa?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w0NzEyNjZ8MHwxfHNlYXJjaHw5fHxjb3ZlcnxlfDB8fHwxNzEwNzQxNzY0fDA&ixlib=rb-4.0.3&q=80&w=1080",
    });
  };

  useEffect(() => {
    fetchData();
    setUserData([{ gender: "Male" }]);
  }, []);

  const handleUserDataChange = (field, value) => {
    setUserData((prevUserData) => ({
      ...prevUserData,
      [field]: value,
    }));
  };

  const handleFileUpload = (event, field) => {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onloadend = () => {
        handleUserDataChange(field, reader.result);
      };
      reader.readAsDataURL(file);
    }
  };

  return (
    <>
      {userData.length === 0 ? (
        <></>
      ) : (
        <div className="mt-28 w-full">
          <div className="mx-12">
            <h2 className="text-2xl font-semibold mb-4 text-center">
              Profile Settings
            </h2>
          </div>
          <div className="mb-6 mt-8 mx-24 ">
            <div className="grid grid-cols-2 gap-x-48 px-6 py-4 bg-gray-200 rounded-lg mb-10 shadow-xl items-center justify-items-center">
              {/* Profession */}
              <div className="mb-6 w-full ">
                <label className="block mb-1">Profession</label>
                <input
                  type="text"
                  className="w-full px-4 py-2 rounded-lg border-gray-300 focus:outline-none focus:border-blue-500"
                  value={userData.profession || ""}
                  onChange={(e) =>
                    handleUserDataChange("profession", e.target.value)
                  }
                />
              </div>

              <div className="mb-6 w-full">
                <label htmlFor="firstName" className="block mb-1">
                  First Name
                </label>
                <input
                  type="text"
                  id="firstName"
                  className="w-full px-4 py-2 rounded-lg border-gray-300 focus:outline-none focus:border-blue-500"
                  value={userData.firstName || ""}
                  onChange={(e) =>
                    handleUserDataChange("firstName", e.target.value)
                  }
                />
              </div>
              {/* Last Name */}
              <div className="mb-6 w-full">
                <label htmlFor="lastName" className="block mb-1">
                  Last Name
                </label>
                <input
                  type="text"
                  id="lastName"
                  className="w-full px-4 py-2 rounded-lg border-gray-300 focus:outline-none focus:border-blue-500"
                  value={userData.lastName || ""}
                  onChange={(e) =>
                    handleUserDataChange("lastName", e.target.value)
                  }
                />
              </div>
              {/* Date of Birth */}
              <div className="mb-6 w-full">
                <label htmlFor="dateOfBirth" className="block mb-1">
                  Date of Birth
                </label>
                <input
                  type="text"
                  id="dateOfBirth"
                  className="w-full px-4 py-2 rounded-lg border-gray-300 focus:outline-none focus:border-blue-500"
                  value={userData.dateOfBirth || ""}
                  onChange={(e) =>
                    handleUserDataChange("dateOfBirth", e.target.value)
                  }
                />
              </div>
              {/* Gender */}
              <div className="mb-6 w-full">
                <label htmlFor="gender" className="block mb-1">
                  Gender
                </label>
                <select
                  id="gender"
                  className="w-full px-4 py-2 rounded-lg border-gray-300 focus:outline-none focus:border-blue-500"
                  value={userData.gender || ""}
                  onChange={(e) =>
                    handleUserDataChange("gender", e.target.value)
                  }
                >
                  <option value="MALE">Male</option>
                  <option value="FEMALE">Female</option>
                </select>
              </div>
              {/* Location */}
              <div className="mb-6 w-full">
                <label htmlFor="location" className="block mb-1">
                  Location
                </label>
                <input
                  type="text"
                  id="location"
                  className=" w-full px-4 py-2 rounded-lg border-gray-300 focus:outline-none focus:border-blue-500"
                  value={userData.location || ""}
                  onChange={(e) =>
                    handleUserDataChange("location", e.target.value)
                  }
                />
              </div>
              {/* Phone Number */}
              <div className="mb-6 w-full ">
                <label htmlFor="phoneNumber" className="block mb-1">
                  Phone Number
                </label>
                <input
                  type="text"
                  id="phoneNumber"
                  className=" w-full px-4 py-2 rounded-lg border-gray-300 focus:outline-none focus:border-blue-500"
                  value={userData.phoneNumber || ""}
                  onChange={(e) =>
                    handleUserDataChange("phoneNumber", e.target.value)
                  }
                />
              </div>
              {/* Email */}
              <div className="mb-6 w-full">
                <label htmlFor="email" className="block mb-1">
                  Email
                </label>
                <input
                  type="email"
                  id="email"
                  className=" w-full px-4 py-2 rounded-lg border-gray-300 focus:outline-none focus:border-blue-500"
                  value={userData.email || ""}
                  onChange={(e) =>
                    handleUserDataChange("email", e.target.value)
                  }
                />
              </div>
            </div>

            <div className="flex justify-around gap-32 mx-16">
              <div className="text-center ">
                <label
                  htmlFor="profileImage"
                  className="block mb-2 text-lg font-medium"
                >
                  Profile Image
                </label>
                <div className="relative h-fit">
                  <input
                    type="file"
                    id="profileImage"
                    className="hidden"
                    accept="image/*"
                    onChange={(e) => handleFileUpload(e, "profileImage")}
                  />
                  {userData.profileImage && (
                    <img
                      src={userData.profileImage || ""}
                      alt="Profile"
                      className="block mt-2 rounded-lg"
                    />
                  )}
                  <label
                    htmlFor="profileImage"
                    className="absolute bottom-0 right-0 cursor-pointer bg-blue-600 px-6 py-2 text-lg rounded-br-lg text-text-white hover:bg-blue-800 transition"
                  >
                    Choose File
                  </label>
                </div>
              </div>
              {/* Cover Image */}
              <div className="mb-6 text-center">
                <label
                  htmlFor="coverImage"
                  className="block mb-2 text-lg font-medium"
                >
                  Cover Image
                </label>
                <div className="relative h-fit">
                  <input
                    type="file"
                    id="coverImage"
                    className="hidden"
                    accept="image/*"
                    onChange={(e) => handleFileUpload(e, "coverImage")}
                  />
                  {userData.coverImage && (
                    <img
                      src={userData.coverImage || ""}
                      alt="Cover"
                      className="block mt-2 rounded-lg"
                    />
                  )}
                  <label
                    htmlFor="coverImage"
                    className="absolute bottom-0 right-0 cursor-pointer bg-blue-600 px-6 py-2 text-lg rounded-br-lg text-text-white hover:bg-blue-800 transition"
                  >
                    Choose File
                  </label>
                </div>
              </div>
            </div>

            {/* Save Changes Button */}
            <div className="text-center ">
              <button
                onClick={() => {
                  updateUser({
                    token: token,
                    name: userData.firstName + " " + userData.lastName,
                    dateOfBirth: formatDate(userData.dateOfBirth),
                    gender: (userData.gender || "MALE").toUpperCase(),
                    location: userData.location,
                    phoneNumber: userData.phoneNumber,
                    email: userData.email,
                  });
                }}
                className="bg-orange text-white px-4 py-2 rounded-lg hover:bg-blue-600 focus:outline-none focus:bg-blue-600"
              >
                Save Changes
              </button>
            </div>
          </div>
        </div>
      )}
    </>
  );
};

export default Settings;
