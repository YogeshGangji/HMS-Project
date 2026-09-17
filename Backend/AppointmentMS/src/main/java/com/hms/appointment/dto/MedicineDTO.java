package com.hms.appointment.dto;

import com.hms.appointment.entity.Medicine;
import com.hms.appointment.entity.Prescription;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicineDTO {

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
    private Long prescriptionId;

    public Medicine toEntity() {
        Medicine medicine = new Medicine();
        medicine.setId(id);
        medicine.setMedicineId(medicineId);
        medicine.setName(name);
        medicine.setQuantity(quantity);
        medicine.setDosage(dosage);
        medicine.setFrequency(frequency);
        medicine.setDuration(duration);
        medicine.setRoute(route);
        medicine.setType(type);
        medicine.setInstructions(instructions);
        medicine.setPrescription(new Prescription(prescriptionId));

        return medicine;
    }

}
