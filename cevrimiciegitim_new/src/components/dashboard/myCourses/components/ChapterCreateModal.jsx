import React, { useState } from "react";
import {
  FaEdit,
  FaPlus,
  FaUpload,
  FaDollarSign,
  FaChalkboardTeacher,
  FaFileUpload,
  FaMinus,
  FaTrash,
} from "react-icons/fa";
import NewCourse from "./NewCourse";

const ChapterCreateModal = ({ setIsCreateModal }) => {
  const data = {
    chapterTitle: "This is example Title",
    chapterDescription:
      "This is example Description long text. this is a long text.  is example Description long text. this is a long text. ",
  };

  const [chapterTitle, setChapterTitle] = useState(data.chapterTitle || "");
  const [isTitleEdit, setIsTitleEdit] = useState(false);

  const [chapterDescription, setChapterDescription] = useState(
    data.chapterDescription || ""
  );
  const [isDescriptionEdit, setIsDescriptionEdit] = useState(false);

  const [isVideoEdit, setIsVideoEdit] = useState(false);
  const [video, setVideo] = useState("");

  return (
    <>
      <div className="fixed inset-0 z-50 flex justify-center items-center bg-text-black bg-opacity-80">
        <div className="bg-white w-1/2  rounded-xl">
          <div className="flex flex-col gap-6 mx-6  ">
            <div>
              <div className={style.headerContainer}>
                <div className={`${style.iconContainer} `}>
                  <FaChalkboardTeacher className={style.icon} />{" "}
                </div>
                <p>Customize Your Chapter</p>
              </div>
              <div className={style.sectionContainer}>
                <div className={style.titleContainer}>
                  <p className={style.title}>Chapter Title</p>
                  <button onClick={() => setIsTitleEdit(!isTitleEdit)}>
                    <FaEdit className={style.iconSmall} />
                  </button>
                </div>
                {isTitleEdit ? (
                  <input
                    type="text"
                    placeholder={
                      chapterTitle === "" ? "Enter title" : chapterTitle
                    }
                    value={chapterTitle}
                    onChange={(e) => setChapterTitle(e.target.value)}
                    className={style.input}
                  />
                ) : (
                  <p>{chapterTitle === "" ? "No Title" : chapterTitle}</p>
                )}
                <div className="mt-2 flex justify-end">
                  <button
                    onClick={() => {
                      setIsTitleEdit(!isTitleEdit);
                    }}
                    className="px-2 py-1 bg-custom-orange text-white rounded-md"
                  >
                    Save
                  </button>
                </div>
              </div>
            </div>

            <div className={style.sectionContainer}>
              <div className={style.titleContainer}>
                <p className={style.title}>Chapter Description</p>
                <button
                  onClick={() => {
                    setIsDescriptionEdit(!isDescriptionEdit);
                  }}
                >
                  <FaEdit className={style.iconSmall} />
                </button>
              </div>
              {isDescriptionEdit ? (
                <textarea
                  placeholder={
                    chapterDescription === ""
                      ? "Enter description"
                      : chapterDescription
                  }
                  value={chapterDescription}
                  onChange={(e) => setChapterDescription(e.target.value)}
                  className="bg-white w-full"
                  rows="5"
                />
              ) : (
                <p>
                  {chapterDescription === ""
                    ? "No Description"
                    : chapterDescription}
                </p>
              )}

              <div className="mt-2 flex justify-end">
                <button
                  onClick={() => {
                    setIsDescriptionEdit(!isDescriptionEdit);
                  }}
                  className="px-2 py-1 bg-custom-orange text-white rounded-md"
                >
                  Save
                </button>
              </div>
            </div>
          </div>

          <div className=" mx-6  ">
            <div>
              <div className={style.headerContainer}>
                <div className={style.iconContainer}>
                  <FaUpload className={style.icon} />{" "}
                </div>
                <p>Video</p>
              </div>

              <div className={style.sectionContainer}>
                <div className="flex justify-between">
                  <p className={style.title}>Videos</p>
                  <button onClick={() => setIsVideoEdit(!isVideoEdit)}>
                    <FaEdit className={style.iconSmall} />
                  </button>
                </div>
                <div className="border-2 px-2 py-2 mt-2 mb-2 border-gray rounded-md p-1 relative">
                  <div className=" flex items-center gap-2 cursor-pointer">
                    <FaFileUpload className="text-2xl text-custom-orange" />
                    <p className="text-2xl text-custom-orange">
                      Choose Files or Drag and Drop
                    </p>
                    <input
                      type="file"
                      accept="video/mov, .mov, .mp4, .webm, .ogg"
                      style={{
                        opacity: 0,
                        position: "absolute",
                        width: "100%",
                        height: "100%",
                        top: 0,
                        left: 0,
                        cursor: "pointer",
                      }}
                      onChange={(e) => {
                        setVideo(e.target.files[0]);
                        console.log(e.target.files[0]);
                      }}
                    />
                  </div>
                  <p className="text-sm text-gray-500">
                    PNG, JPG, GIF, SVG, or PDF. Max 15MB
                  </p>
                  {video && (
                    <div className="mt-2">
                      <video
                        className="VideoInput_video"
                        width="100%"
                        controls
                        src={URL.createObjectURL(video)}
                      />
                    </div>
                  )}
                  <button className="bg-custom-orange text-white p-2 rounded-md mt-2">
                    Upload
                  </button>
                </div>
                <div className="mt-2 flex justify-end">
                  <button
                    onClick={() => {
                      alert("Video Saved");
                    }}
                    className="px-2 py-1 bg-custom-orange text-white rounded-md"
                  >
                    Save
                  </button>
                </div>
              </div>
            </div>
          </div>
          <div className="w-full gap-8 flex mt-4">
            <button
              onClick={() => {
                alert("Changes Saved");
                setIsCreateModal(false);
              }}
              className="bg-green-600 my-2 mx-6 w-full text-white px-3 py-3 rounded-bl-xl"
            >
              Update
            </button>
            <button
              onClick={() => {
                alert("Changes Discarded");
                setIsCreateModal(false);
              }}
              className="bg-red-500 mx-6 my-2 w-full text-white px-3 py-3 rounded-br-xl"
            >
              Cancel
            </button>
          </div>
        </div>
      </div>
    </>
  );
};

export default ChapterCreateModal;

const style = {
  icon: "w-8 h-8 text-custom-orange",
  iconSmall: "w-6 h-6 text-custom-orange",
  iconContainer:
    "rounded-full bg-white w-12 h-12 flex justify-center items-center shadow-md",
  headerContainer:
    "mt-6 mb-3 flex gap-2 items-center text-2xl text-custom-orange",
  sectionContainer: "bg-gray-light shadow-lg px-6 py-4 rounded-md",
  header: "text-lg font-semibold",
  headerContainer:
    "flex gap-2 items-center mt-6 mb-3 text-custom-orange text-xl font-medium",
  title: "text-lg font-semibold",
  titleContainer: "flex justify-between mb-2",
  input: "px-2 py-1 rounded-md",
};