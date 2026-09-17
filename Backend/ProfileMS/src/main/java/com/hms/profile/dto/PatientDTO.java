package com.hms.profile.dto;

import com.hms.profile.entity.Patient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PatientDTO {
    private Long id;
    private String name;
    private String email;
    private LocalDate dob;
    private String  phone;
    private String address;
    private String aadharNo;
    private BloodGroup bloodGroup;
    private List<String> allergies ;
    private List<String> chronicDisease;

    public Patient toEntity() {
        return new Patient(this.id, this.name, this.email, this.dob, this.phone, this.address, this.aadharNo, this.bloodGroup, this.allergies, this.chronicDisease);
    }
}
