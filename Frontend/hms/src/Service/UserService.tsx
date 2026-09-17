import axios from "axios";
import axiosInstance from "../Interceptor/AxiosIntercepter";

const registerUser = async (user: any) => {
    console.log("data is coming as", user);

    return axiosInstance.post("/user/register", user)
        .then((response: any) => response.data)
        .catch((error: any) => { throw error })

}

const loginUser = async (user: any) => {
    console.log("data is coming as for login", user);

    return axiosInstance.post("/user/login", user)
        .then((response: any) => response.data)
        .catch((error: any) => { throw error })

}

export { registerUser, loginUser };