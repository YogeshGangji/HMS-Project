package com.hms.appointment.service;

import com.hms.appointment.dto.ApRecordDTO;
import com.hms.appointment.entity.ApRecord;
import com.hms.appointment.exception.HMSException;
import com.hms.appointment.repository.ApRercordRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.hms.appointment.utility.SringListConverter.covertListToString;

@Service
@Transactional
public class ApRecordServiceImpl implements ApRecordService {

    @Autowired
    private ApRercordRepository apRepository;

    @Autowired
    private PrescriptionService prescriptionService;


    @Override
    public Long createAppointmentRecord(ApRecordDTO apRecordDTO) throws HMSException
    {
        Optional<ApRecord> existRecord =  apRepository.findByAppointment_Id(apRecordDTO.getAppointmentId());

        if(existRecord.isPresent())
        {
            throw new HMSException("APPOINTMENT_RECORD_EXIST");

        }
        Long id = apRepository.save(apRecordDTO.toEntity()).getId();

        if(apRecordDTO.getPrescription() != null)
        {
            apRecordDTO.getPrescription().setAppointmentId(apRecordDTO.getId());
            prescriptionService.savePrescription(apRecordDTO.getPrescription());
        }
        return id;

    }

    @Override
    public void updateAppointmentRecord(ApRecordDTO request) throws HMSException {
        ApRecord existRecord = apRepository.findById(request.getId()).orElseThrow(() -> new HMSException("APPOINTMENT_RECORD_NOT_FOUND"));


        existRecord.setNotes(request.getNotes());
        existRecord.setDignosis(request.getDignosis());
        existRecord.setFollowupDate(request.getFollowupDate());
        existRecord.setSymptom(covertListToString(request.getSymptom()));
        existRecord.setTests(covertListToString(request.getTests()));
        existRecord.setReferral(request.getReferral());

        apRepository.save(existRecord);

    }

    @Override
    public ApRecordDTO getAppointmentRecordByAppointmentId(Long appointment_id) throws HMSException {
       return apRepository.findByAppointment_Id(appointment_id).orElseThrow(() -> new HMSException("APPOINTMENT_RECORD_NOT_FOUND")).toDto();

    }

    @Override
    public ApRecordDTO getAppointmentRecordById(Long id) throws HMSException {
        return apRepository.findById(id).orElseThrow(() -> new HMSException("APPOINTMENT_RECORD_NOT_FOUND")).toDto();

    }

    @Override
    public ApRecordDTO getAppointmentRecordDetailsByAppointmentId(Long appointmentId) throws HMSException {
        ApRecordDTO apRecordDTO = apRepository.findByAppointment_Id(appointmentId).orElseThrow(() -> new HMSException("APPOINTMENT_RECORD_NOT_FOUND")).toDto();
            apRecordDTO.setPrescription(prescriptionService.getPrescriptionByAppointmentId(appointmentId));
            return apRecordDTO;
    }

}
