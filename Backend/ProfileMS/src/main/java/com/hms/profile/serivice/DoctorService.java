package com.hms.profile.serivice;

import com.hms.profile.dto.DoctorDTO;
import com.hms.profile.dto.DoctorDropdown;
import com.hms.profile.entity.Doctor;
import com.hms.profile.exception.HMSException;

import java.util.List;

public interface DoctorService {

    public Long  addDoctor(DoctorDTO doctor) throws HMSException;
    public DoctorDTO getDoctorById(Long id) throws HMSException;

    DoctorDTO updateProfile(DoctorDTO doctorDTO) throws HMSException;

    Boolean docterExistsById(Long id) throws HMSException;

    public List<DoctorDropdown> getDoctorsDropdown() throws HMSException;
}
