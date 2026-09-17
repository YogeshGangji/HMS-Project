package com.hms.profile.serivice;

import com.hms.profile.dto.PatientDTO;
import com.hms.profile.entity.Patient;
import com.hms.profile.exception.HMSException;
import com.hms.profile.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientServiceImpl implements PatientService {
    @Autowired
    private PatientRepository patientRepository;

    @Override
    public Long addPatient(PatientDTO patient) throws HMSException {
        if(patient.getEmail() != null && patientRepository.findByEmail(patient.getEmail()).isPresent())
        {
            throw new HMSException("PATIENT_ALREADY_EXISTS");
        }
        if(patient.getAadharNo() != null && patientRepository.findByAadharNo(patient.getAadharNo()).isPresent())
        {
            throw new HMSException("PATIENT_AADHARNO_ALREADY_EXISTS");
        }

      return patientRepository
              .save(patient.toEntity())
              .getId();

    }

    @Override
    public PatientDTO getPatientById(Long id) throws HMSException {
      return patientRepository
              .findById(id)
              .orElseThrow(()->new HMSException("PATIENT_NOT_FOUND"))
              .toDTo();
    }

    @Override
    public PatientDTO updateProfile(PatientDTO patientDTO) throws HMSException {
        patientRepository.findById(patientDTO.getId()).orElseThrow(() -> new HMSException("PATIENT_NOT_FOUND"));

        return patientRepository.save(patientDTO.toEntity()).toDTo();

    }

    @Override
    public Boolean patientExistsById(Long id) throws HMSException {
        return patientRepository.existsById(id);
    }
}
