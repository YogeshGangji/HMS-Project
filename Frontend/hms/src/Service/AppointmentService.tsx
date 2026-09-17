import axiosInstance from "../Interceptor/AxiosIntercepter"

const scedultAppointment = async (appointment: any) => {
    return axiosInstance.post("/appointment/schedule", appointment)
        .then((response: any) =>
            response.data
        ).catch((error) => { throw error });

}

const cancelAppointment = async (id: any) => {
    return axiosInstance.put("/appointment/cancel/" + id)
        .then((response: any) => response.data)
        .catch((err) => { throw err })
};


const getAppointment = async (id: any) => {
    return axiosInstance.get("/appointment/get/" + id)
        .then((response: any) => response.data)
        .catch((err) => { throw err })
};

const getAppointmentWithDetails = async (id: any) => {
    return axiosInstance.get("/appointment/get/details/" + id)
        .then((response: any) => response.data)
        .catch((err) => { throw err })
};


const getAppointmentWithDetailsByPatientId = async (id: any) => {
    return axiosInstance.get("/appointment/getAllByPatient/" + id)
        .then((response: any) => response.data)
        .catch((err) => { throw err })
};


const getAppointmentWithDetailsByDoctorId = async (id: any) => {
    return axiosInstance.get("/appointment/getAllByDoctor/" + id)
        .then((response: any) => response.data)
        .catch((err) => { throw err })
};

export { scedultAppointment, cancelAppointment, getAppointment, getAppointmentWithDetails, getAppointmentWithDetailsByPatientId, getAppointmentWithDetailsByDoctorId }