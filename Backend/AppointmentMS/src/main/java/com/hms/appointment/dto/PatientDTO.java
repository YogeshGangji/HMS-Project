package com.hms.appointment.dto;

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

}
