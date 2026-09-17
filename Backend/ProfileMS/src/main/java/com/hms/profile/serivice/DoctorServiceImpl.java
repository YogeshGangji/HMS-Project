package com.hms.profile.serivice;

import com.hms.profile.dto.DoctorDTO;
import com.hms.profile.dto.DoctorDropdown;
import com.hms.profile.entity.Doctor;
import com.hms.profile.exception.HMSException;
import com.hms.profile.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public Long addDoctor(DoctorDTO doctor) throws HMSException {
        if (doctorRepository.findByEmail(doctor.getEmail()).isPresent()) {
            throw new HMSException("DOCTOR_ALREADY_EXISTS");
        }
        if (doctorRepository.findByLicenseNo(doctor.getLicenseNo()).isPresent()) {
            throw new HMSException("DOCTOR_LICENSE_ALREADY_EXISTS");
        }

        return doctorRepository.save(doctor.toEntity()).getId();
    }

    @Override
    public DoctorDTO getDoctorById(Long id) throws HMSException {
        return doctorRepository.findById(id).orElseThrow(()->new HMSException("DOCTOR_NOT_FOUND")).toDTO();
    }

    @Override
    public DoctorDTO updateProfile(DoctorDTO doctorDTO) throws HMSException {
        doctorRepository.findById(doctorDTO.getId()).orElseThrow(() -> new  HMSException("DOCTOR_NOT_FOUND"));

        return doctorRepository.save(doctorDTO.toEntity()).toDTO();
    }

    @Override
    public Boolean docterExistsById(Long id) throws HMSException {

        System.out.println("id is " + id);
        if(id != null) {
            return doctorRepository.existsById(id);
        }
        else
        {
            throw new HMSException("INVALID_ID");
        }
    }

    @Override
    public List<DoctorDropdown> getDoctorsDropdown() throws HMSException {
        return doctorRepository.findAllDoctorsDropdown();
    }
}
