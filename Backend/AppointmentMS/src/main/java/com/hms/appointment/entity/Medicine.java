package com.hms.appointment.entity;

import com.hms.appointment.dto.MedicineDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long medicineId;
    private String name;
    private Integer quantity;
    private String dosage;
    private String frequency;
    private Integer duration;
    private String route;
    private String type;
    private String instructions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prescrition_id")
    private Prescription prescription;

    public MedicineDTO toDTO()
    {
        MedicineDTO medicineDTO = new MedicineDTO();
        medicineDTO.setId(id);
        medicineDTO.setMedicineId(medicineId);
        medicineDTO.setName(name);
        medicineDTO.setQuantity(quantity);
        medicineDTO.setDosage(dosage);
        medicineDTO.setFrequency(frequency);
        medicineDTO.setDuration(duration);
        medicineDTO.setRoute(route);
        medicineDTO.setType(type);
        medicineDTO.setInstructions(instructions);
        medicineDTO.setPrescriptionId(prescription.getId());
        return medicineDTO;

    }
}
