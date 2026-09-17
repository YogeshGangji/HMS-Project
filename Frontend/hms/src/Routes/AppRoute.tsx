import React from "react";
import Sidebar from "../Components/Doctor/Sidebar/Sidebar";
import Header from "../Components/Header/Header";
import { BrowserRouter, Route, Router, Routes } from "react-router-dom";
import AdminDashboard from "../Layout/AdminDashboard";
import Random from "../Components/Random";
import LoginPage from "../Pages/LoginPage";
import RegisterPage from "../Pages/RegisterPage";
import PublicRoute from "./PublicRoute";
import ProtectedRoute from "./ProtectedRoute";
import PatientDashboard from "../Layout/PatientDashboard";
import PatientProfilePage from "../Pages/Patient/PatientProfilePage";
import DoctorDashboard from "../Layout/DoctorDashboard";
import DoctorProfilePage from "../Pages/Doctor/DoctorProfilePage";
import PatientAppointmentPage from "../Pages/Patient/PatientAppointmentPage";
import Appointment from "../Components/Doctor/Appointment/Appointment";
import DoctorAppointmentPage from "../Pages/Doctor/DoctorAppointmentPage";
import DoctorAppointmentDetailPage from "../Pages/Doctor/DoctorAppointmentDetailPage";

const AppRoute = () => {

    return (

        <BrowserRouter>
            <Routes>
                <Route path="/login" element={<PublicRoute><LoginPage /></PublicRoute>}></Route>
                <Route path="/register" element={<PublicRoute><RegisterPage /></PublicRoute>}></Route>
                <Route path="/" element={<ProtectedRoute><AdminDashboard /></ProtectedRoute>} >
                    <Route path="/dashboard" element={<Random />} />
                    <Route path="/pharmacy" element={<Random />} />
                    <Route path="/patients" element={<Random />} />
                    <Route path="/doctors" element={<Random />} />
                    <Route path="/appointments" element={<Random />} />
                </Route>
                <Route path="/doctor" element={<ProtectedRoute><DoctorDashboard /></ProtectedRoute>} >
                    <Route path="profile" element={<DoctorProfilePage />} />
                    <Route path="dashboard" element={<Random />} />
                    <Route path="patients" element={<Random />} />
                    <Route path="pharmacy" element={<Random />} />
                    <Route path="appointments" element={<DoctorAppointmentPage />} />
                    <Route path="appointments/:id" element={<DoctorAppointmentDetailPage />} />
                </Route>
                <Route path="/patient" element={<ProtectedRoute><PatientDashboard /></ProtectedRoute>} >
                    <Route path="dashboard" element={<Random />} />
                    <Route path="profile" element={<PatientProfilePage />} />
                    <Route path="appointments" element={<PatientAppointmentPage />} />
                    <Route path="book" element={<Random />} />
                </Route>
            </Routes>
        </ BrowserRouter >

    )



}

export default AppRoute