package com.hms.profile.controller;

import com.hms.profile.dto.PatientDTO;
import com.hms.profile.exception.HMSException;
import com.hms.profile.serivice.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile/patient")
@Validated
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping("/add")
    public ResponseEntity<Long> createPatient(@RequestBody PatientDTO patientDTO) throws HMSException {

        return new ResponseEntity<>(patientService.addPatient(patientDTO), HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<PatientDTO> getPatientById(@PathVariable Long id) throws HMSException {
        return new ResponseEntity<>(patientService.getPatientById(id), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<PatientDTO> updatePatient(@RequestBody PatientDTO patientDTO) throws HMSException {
        return new ResponseEntity<>(patientService.updateProfile(patientDTO),HttpStatus.OK);
    }
    @GetMapping("/exist/{id}")
    public ResponseEntity<Boolean> existPatientById(@PathVariable Long id) throws HMSException {
        return new ResponseEntity<>(patientService.patientExistsById(id), HttpStatus.OK);
    }

}
