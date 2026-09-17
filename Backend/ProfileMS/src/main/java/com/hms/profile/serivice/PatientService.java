package com.hms.profile.serivice;

import com.hms.profile.dto.PatientDTO;
import com.hms.profile.entity.Doctor;
import com.hms.profile.entity.Patient;
import com.hms.profile.exception.HMSException;

public interface PatientService {

    public Long addPatient(PatientDTO patient) throws HMSException;
    public PatientDTO getPatientById(Long id) throws HMSException;

    PatientDTO updateProfile(PatientDTO patientDTO) throws HMSException;

    Boolean patientExistsById(Long id) throws HMSException;
}
