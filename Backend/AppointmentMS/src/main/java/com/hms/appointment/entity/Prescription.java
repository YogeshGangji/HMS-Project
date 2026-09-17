package com.hms.appointment.entity;

import com.hms.appointment.dto.PrescriptionDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long patientId;
    private Long doctorId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    private LocalDate prescriptionDate;
    private String notes;

    public Prescription(Long id)
    {
        this.id = id;
    }

    public PrescriptionDTO toDTO()
    {
        PrescriptionDTO prescriptionDTO = new PrescriptionDTO();
        prescriptionDTO.setId(id);
        prescriptionDTO.setPatientId(patientId);
        prescriptionDTO.setDoctorId(doctorId);
        prescriptionDTO.setAppointmentId(appointment.getId());
        prescriptionDTO.setPrescriptionDate(prescriptionDate);
        prescriptionDTO.setNotes(notes);
        return prescriptionDTO;
    }
}
