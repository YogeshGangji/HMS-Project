package com.hms.appointment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDetailsDto {
    private Long id;
    private Long patientId;
    private String patientName;
    private String patientEmail;
    private String patientPhone;
    private Long doctorId;
    private String doctorName;
    private LocalDateTime appointmentTime;
    private Status status;
    private String reason;
    private String notes;


    public AppointmentDetailsDto(
            Long id,
            Long patientId,
            Long doctorId,
            LocalDateTime appointmentTime,
            String reason,
            Status status,
            String notes
    ) {
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentTime = appointmentTime;
        this.reason = reason;
        this.status = status;
        this.notes = notes;
    }

}

