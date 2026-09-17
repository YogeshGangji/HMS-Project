package com.hms.appointment.dto;

import com.hms.appointment.entity.Appointment;
import com.hms.appointment.entity.Prescription;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionDTO {

    private Long id;
    private Long patientId;
    private Long doctorId;

    private Long appointmentId;

    private LocalDate prescriptionDate;
    private String notes;
    private List<MedicineDTO> medicines;

    public Prescription toEntity() {
        Prescription prescription = new Prescription();
        prescription.setId(id);
        prescription.setPatientId(patientId);
        prescription.setDoctorId(doctorId);
        prescription.setPrescriptionDate(prescriptionDate);
        prescription.setNotes(notes);
        return prescription;
    }
}
