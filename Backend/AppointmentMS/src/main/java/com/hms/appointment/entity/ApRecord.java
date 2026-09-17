package com.hms.appointment.entity;

import com.hms.appointment.dto.ApRecordDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.hms.appointment.utility.SringListConverter.convertStringToList;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long patientId;
    private Long doctorId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="appointment_id")
    private Appointment appointment;

    private String symptom;
    private String dignosis;
    private String tests;
    private String notes;
    private String referral;
    private LocalDate followupDate;

    @CreationTimestamp
    private LocalDateTime createdDate;
    @UpdateTimestamp
    private LocalDateTime updatedDate;


    public ApRecordDTO toDto()
    {
        return new ApRecordDTO(id,patientId,doctorId,appointment.getId(),convertStringToList(symptom),dignosis,convertStringToList(tests),notes,referral,null,followupDate,createdDate,updatedDate);
    }
}
