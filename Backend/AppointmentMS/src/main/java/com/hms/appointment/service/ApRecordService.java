package com.hms.appointment.service;

import com.hms.appointment.dto.ApRecordDTO;
import com.hms.appointment.exception.HMSException;

public interface ApRecordService {

    public Long createAppointmentRecord(ApRecordDTO apRecordDTO) throws HMSException;

    public void updateAppointmentRecord(ApRecordDTO apRecordDTO) throws HMSException;

    public ApRecordDTO getAppointmentRecordByAppointmentId(Long appointmentRecordId) throws HMSException;

    public ApRecordDTO getAppointmentRecordById(Long appointmentRecordId) throws HMSException;

    public ApRecordDTO getAppointmentRecordDetailsByAppointmentId(Long appointmentId) throws HMSException;
}
