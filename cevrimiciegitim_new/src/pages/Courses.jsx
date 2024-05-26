import React, { useState } from "react";
import Navbar from "../components/common/Navbar";
import { useLocation } from "react-router-dom";
import CourseCard from "../components/common/CourseCard";
import OnlineCourse from "../assets/online-course.png";
import InstructorPhoto from "../assets/team-5.jpeg";
import { useEffect } from "react";
import { paginateCourses } from "../api/sign";

const Courses = () => {
  const [courses, setCourses] = useState([]);
  const location = useLocation();
  const stateCategory = location.state.category;

  const [activeCategory, setActiveCategory] = useState(stateCategory);

  useEffect(() => {
    if (activeCategory === "All Courses") {
      setActiveCategory("");
    }
    (async () => {
      const response = await paginateCourses({
        page: 1,
        limit: 100,
        category: activeCategory,
        token: null,
      });
      setCourses(
        response.map((course) => {
          return {
            title: course.title,
            rating: Math.floor(Math.random() * 4) + 1,
            instructor: course.creator.name,
            category: course.category,
            lessons: Math.floor(Math.random() * 4) + 1,
            studentCount: Math.floor(Math.random() * 100),
          };
        })
      );
    })();
  }, [activeCategory]);

  const categories = [
    "All Courses",
    "Art & Design",
    "Marketing",
    "Music",
    "Computer Science",
    "History & Archeologic",
    "Business Administration",
    "Web Management",
    "Information Software",
    "UI/UX Design Courses",
    "Software Engineering",
    "Health & Fitness",
    "Graphic Design",

    "Cheaper",
    "Most Viewed ",
    "Latest",
  ];

  const [courseCount, setCourseCount] = useState(8);

  return (
    <>
      <Navbar />
      <div className="mt-32 w-full mb-5 text-center">
        <p className="text-2xl font-semibold ">COURSES </p>
      </div>
      <div className=" grid grid-cols-8 text-sm items-center gap-y-2 mx-32 mb-12">
        {categories.map((category, index) => (
          <button
            className={` hover:bg-blue-600 flex justify-center text-white py-1 mx-1 rounded-md transition duration-300 ${
              activeCategory === category
                ? "bg-blue-600"
                : "bg-category-primary"
            }`}
            key={index}
            onClick={() => setActiveCategory(category)}
          >
            <p className="text-white cursor-pointer">{category}</p>
          </button>
        ))}
      </div>
      <div
        style={{ cursor: "pointer" }}
        className="grid gap-y-12 mx-28 grid-cols-4 justify-items-center" // Added justify-items-center to center the items
      >
        {courses.map((course, index) => (
          <>
            {index < courseCount && (
              <CourseCard
                key={index}
                image={OnlineCourse}
                InstructorPhoto={InstructorPhoto}
                course={course}
              />
            )}
          </>
        ))}
      </div>

      <div className="flex mt-12 mb-8 justify-center">
        <button
          onClick={() => setCourseCount(courseCount + 4)}
          className="hover:bg-blue-600 bg-blue-500 text-white px-4 py-2 rounded-md"
        >
          Load More
        </button>
      </div>
    </>
  );
};

export default Courses;
