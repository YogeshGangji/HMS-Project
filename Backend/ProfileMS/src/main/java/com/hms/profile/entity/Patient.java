package com.hms.profile.entity;

import com.hms.profile.dto.BloodGroup;
import com.hms.profile.dto.PatientDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(unique = true)
    private String email;
    private LocalDate dob;
    private String  phone;
    private String address;

    @Column(unique = true)
    private String aadharNo;
    private BloodGroup bloodGroup;

    @ElementCollection
    private List<String> allergies = new ArrayList<>();

    @ElementCollection
    private List<String> chronicDisease = new ArrayList<>();

    public PatientDTO toDTo() {
        return new PatientDTO(this.id, this.name, this.email, this.dob, this.phone, this.address, this.aadharNo, this.bloodGroup, this.allergies, this.chronicDisease);
    }

}
