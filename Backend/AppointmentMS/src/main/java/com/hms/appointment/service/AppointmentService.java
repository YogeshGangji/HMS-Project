package com.hms.appointment.service;

import com.hms.appointment.dto.AppointmentDTO;
import com.hms.appointment.dto.AppointmentDetailsDto;
import com.hms.appointment.entity.Appointment;
import com.hms.appointment.exception.HMSException;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentService {

    Long sceduleAppointment(AppointmentDTO appointmentDto);

    void cancelAppointment(long appointmentId);

    void completeAppointment(long appointmentId);

    void resceduleAppointment(long appointmentId, LocalDateTime dateTime);

    AppointmentDTO getAppointmentDetails(long appointmentId) throws HMSException;

    AppointmentDetailsDto getAppointmentDetailsWithName(Long appointmentId) throws HMSException;

    List<AppointmentDetailsDto> getAppointmentDetailsWithPatientId(Long patientId) throws HMSException;

    List<AppointmentDetailsDto> getAppointmentDetailsWithDoctorId(Long patientId) throws HMSException;

}
