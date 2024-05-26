import axios from "axios";
import { API_URL } from "../data/api-url";

export const signUpFunc = async (data) => {
  try {
    const response = await axios.post(`${API_URL}/api/auth/signup`, data, {
      headers: {
        "Content-Type": "application/json",
      },
    });
    console.log("Success");
  } catch (error) {
    if (error.response) {
      // The request was made and the server responded with a status code
      // that falls out of the range of 2xx
      console.error("Error creating student:", error.response.data);
    } else if (error.request) {
      // The request was made but no response was received
      console.error(
        "Error creating student: No response received",
        error.request
      );
    } else {
      // Something happened in setting up the request that triggered an Error
      console.error("Error creating student:", error.message);
    }
    throw error;
  }
};

export const signInFunc = async (data) => {
  try {
    const response = await axios.post(`${API_URL}/api/auth/signin`, data);

    // Assuming the token is in response.data.token
    const token = response.data.token;

    return token;
  } catch (error) {
    console.error("Error creating student:", error);
    throw error;
  }
};

export const getUser = async (token) => {
  try {
    const response = await axios.get(`${API_URL}/api/user`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    const { dateOfBirth, ...rest } = response.data;

    return { dateOfBirth: formatToDate2(dateOfBirth), ...rest };
  } catch (error) {
    console.error("Error fetching user:", error);
    throw error;
  }
};

export const updateUser = async (data) => {
  const { token, ...rest } = data;
  try {
    // DEğişecek
    const response = await axios.put(`${API_URL}/api/user`, rest, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    console.log(response);
    return response;
  } catch (error) {
    console.error("Error creating student:", error);
    throw error;
  }
};

export const createCourse = async (token, data) => {
  try {
    const response = await axios.post(`${API_URL}/api/courses`, data, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response.data;
  } catch (error) {
    console.error("Error creating course:", error);
    throw error;
  }
};

export const updateCourse = async (data) => {
  const { courseId, token, ...rest } = data;

  try {
    const response = await axios.put(
      `${API_URL}/api/courses/${courseId}`,
      rest,
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    );
    console.log("HELLO", response.data);
    return response.data;
  } catch (error) {
    console.error("Error updating course:", error);
    throw error;
  }
};

/* put api/courses/{courseId}

    {
        "title":"title",
        "description":"description",
        "attachment":"attachment",
        "category":"category",
        "price":"price"
        "chapters":["id1","id2"]
}  */

export const createChapter = async (token, data) => {
  // data = courseId, chapterTitle
  try {
    const response = await axios.post(`${API_URL}/api/courses/chapter`, data, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response.data;
  } catch (error) {
    console.error("Error creating student:", error);
    throw error;
  }
};

export const paginateCourses = async (data) => {
  // data = page, limit

  try {
    let x;
    if (data.category) {
      x = { page: data.page, limit: data.limit, category: data.category };
    } else {
      x = { page: data.page, limit: data.limit };
    }
    const response = await axios.post(`${API_URL}/api/courses/search`, x, {
      headers: {
        Authorization: `Bearer ${data.token}`,
      },
    });
    return response.data;
  } catch (error) {
    console.error("Error paginating:", error);
    throw error;
  }
};

////////
/////////////////////
////////
export function formatDate(inputDate) {
  const [day, month, year] = inputDate.split("/");
  const formattedDate = `${year}-${month}-${day}T00:17:25.000+03:00`;
  return formattedDate;
}

function formatToDate2(inputDate) {
  const date = new Date(inputDate);
  const day = String(date.getUTCDate()).padStart(2, "0");
  const month = String(date.getUTCMonth() + 1).padStart(2, "0"); // Months are zero-based
  const year = date.getUTCFullYear();
  return `${day}/${month}/${year}`;
}
