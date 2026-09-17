
import axiosInstance from "../Interceptor/AxiosIntercepter";

const getDoctor = async (id: any) => {
    return axiosInstance.get('/profile/doctor/get/' + id)
        .then((response: any) => response.data)
        .catch((error: any) => { throw error; })
}

const updateDoctor = async (doctor: any) => {
    return axiosInstance.put('/profile/doctor/update', doctor)
        .then((reponse: any) => reponse.data)
        .catch((err: any) => { throw err; })
}

const getDoctorDropdown = async () =>{
    return axiosInstance.get('/profile/doctor/dropdown')
    .then((response:any) => response.data)
    .catch((error) => {throw error;})
}

export { getDoctor, updateDoctor, getDoctorDropdown };