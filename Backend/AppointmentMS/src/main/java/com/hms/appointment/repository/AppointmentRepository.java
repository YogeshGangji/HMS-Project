package com.hms.appointment.repository;

import com.hms.appointment.dto.AppointmentDetailsDto;
import com.hms.appointment.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT new com.hms.appointment.dto.AppointmentDetailsDto(a.id, a.patientId, a.doctorId, a.appointmentTime, a.reason, a.status, a.notes) FROM Appointment a WHERE a.patientId = ?1")
    List<AppointmentDetailsDto> findAllByPatientId(Long patientId);


    @Query("SELECT new com.hms.appointment.dto.AppointmentDetailsDto(a.id, a.patientId, a.doctorId, a.appointmentTime, a.reason, a.status, a.notes) FROM Appointment a WHERE a.doctorId = ?1")
    List<AppointmentDetailsDto> findAllByDoctorId(Long doctorId);

}
