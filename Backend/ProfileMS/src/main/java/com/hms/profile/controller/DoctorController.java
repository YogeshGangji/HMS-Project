package com.hms.profile.controller;

import com.hms.profile.dto.DoctorDTO;
import com.hms.profile.dto.DoctorDropdown;
import com.hms.profile.dto.PatientDTO;
import com.hms.profile.exception.HMSException;
import com.hms.profile.serivice.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile/doctor")
@Validated
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping("/add")
    public ResponseEntity<Long> addDoctor(@RequestBody DoctorDTO doctorDTO) throws HMSException {
        return new ResponseEntity<>(doctorService.addDoctor(doctorDTO), HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<DoctorDTO> getDoctor(@PathVariable Long id) throws HMSException {
        return new ResponseEntity<>(doctorService.getDoctorById(id), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<DoctorDTO> updatePatient(@RequestBody DoctorDTO doctorDTO) throws HMSException {
        return new ResponseEntity<>(doctorService.updateProfile(doctorDTO),HttpStatus.OK);
    }

    @GetMapping("/exist/{id}")
    public ResponseEntity<Boolean> existPaitentById(@PathVariable Long id) throws HMSException {
        return new ResponseEntity<>(doctorService.docterExistsById(id), HttpStatus.OK);
    }

    @GetMapping("/dropdown")
    public ResponseEntity<List<DoctorDropdown>> getDoctorsDropdown() throws HMSException {
        return new ResponseEntity<>(doctorService.getDoctorsDropdown(),HttpStatus.OK);
    }
}
