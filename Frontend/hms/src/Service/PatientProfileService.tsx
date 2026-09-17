import axiosInstance from "../Interceptor/AxiosIntercepter";

const getPaitient = async (id: any) => {
    return axiosInstance.get('/profile/patient/get/' + id)
        .then((response: any) => response.data)
        .catch((error: any) => { throw error; })
}

const updatePatient = async (patient: any) => {
    return axiosInstance.put('/profile/patient/update', patient)
        .then((reponse: any) => reponse.data)
        .catch((err: any) => { throw err; })
}


export { getPaitient, updatePatient };