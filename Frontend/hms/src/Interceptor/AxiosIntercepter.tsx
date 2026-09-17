import axios, { type InternalAxiosRequestConfig } from 'axios'

const axiosInstance = axios.create({
    baseURL: "http://localhost:9000"
})
axiosInstance.interceptors.request.use(
    (config: InternalAxiosRequestConfig) => {
        console.log("Intercepter", config);
        const token = localStorage.getItem('token');
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        config.headers['X-Secret-Key'] = "SECRET";

        return config;
    }
)

export default axiosInstance