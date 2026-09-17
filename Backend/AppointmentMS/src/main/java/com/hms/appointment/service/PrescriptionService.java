package com.hms.appointment.service;

import com.hms.appointment.dto.PrescriptionDTO;
import com.hms.appointment.entity.Prescription;
import com.hms.appointment.exception.HMSException;

public interface PrescriptionService {

    public Long savePrescription(PrescriptionDTO prescription) throws HMSException;
    public PrescriptionDTO getPrescriptionById(Long id) throws HMSException;
    public PrescriptionDTO getPrescriptionByAppointmentId(Long id) throws HMSException;

}
