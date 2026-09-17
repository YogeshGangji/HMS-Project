package com.hms.appointment.service;

import com.hms.appointment.clients.ProfileClient;
import com.hms.appointment.dto.*;
import com.hms.appointment.entity.Appointment;
import com.hms.appointment.exception.HMSException;
import com.hms.appointment.repository.AppointmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private ApiService  apiService;

    @Autowired
    private ProfileClient profileClient;

    @Override
    public Long sceduleAppointment(AppointmentDTO appointmentDto) {
//        Boolean docterExists = apiService.doctorProfileExists(appointmentDto.getDoctorId()).block();
        Boolean docterExists = profileClient.docterExists(appointmentDto.getDoctorId());
        System.out.println("doctor exists: " + docterExists);

        if(!docterExists || docterExists == null)
        {
            throw new HMSException("DOCTOR_NOT_FOUND");
        }
//        Boolean patientExists = apiService.patientProfileExists(appointmentDto.getPatientId()).block();
        Boolean patientExists = profileClient.patientExists(appointmentDto.getPatientId());
        if(!patientExists || patientExists == null)
        {
            throw new HMSException("PATIENT_NOT_FOUND");
        }
        appointmentDto.setStatus(Status.SCHEDULED);
        return  appointmentRepository.save(appointmentDto.toEntity()).getId();
    }

    @Override
    public void cancelAppointment(long appointmentId) {

        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(()-> new HMSException("APPOINTMENT_NOT_FOUND"));

        if(appointment.getStatus().equals(Status.CANCELLED))
        {
            throw new HMSException("APPOINTMENT_ALREADY_CANCELLED");
        }
        appointment.setStatus(Status.CANCELLED);
        appointmentRepository.save(appointment);

    }

    @Override
    public void completeAppointment(long appointmentId) {

    }

    @Override
    public void resceduleAppointment(long appointmentId, LocalDateTime dateTime) {



    }

    @Override
    public AppointmentDTO getAppointmentDetails(long appointmentId) throws HMSException {
        return appointmentRepository.findById(appointmentId).orElseThrow(()-> new HMSException("APPOINTMENT_NOT_FOUND")).toDto();
    }

    @Override
    public AppointmentDetailsDto getAppointmentDetailsWithName(Long appointmentId) throws HMSException {
        AppointmentDTO appointmentDTO =  appointmentRepository.findById(appointmentId).orElseThrow(()-> new HMSException("APPOINTMENT_NOT_FOUND")).toDto();
        DoctorDTO doctorDTO = profileClient.getDoctorById(appointmentDTO.getDoctorId());
        PatientDTO patientDTO = profileClient.getPatientById(appointmentDTO.getPatientId());

        AppointmentDetailsDto appointmentDetailsDto = new AppointmentDetailsDto();

        appointmentDetailsDto.setId(appointmentDTO.getId());
        appointmentDetailsDto.setPatientId(patientDTO.getId());
        appointmentDetailsDto.setPatientName(patientDTO.getName());
        appointmentDetailsDto.setPatientEmail(patientDTO.getEmail());
        appointmentDetailsDto.setPatientPhone(patientDTO.getPhone());
        appointmentDetailsDto.setDoctorId(doctorDTO.getId());
        appointmentDetailsDto.setDoctorName(doctorDTO.getName());
        appointmentDetailsDto.setAppointmentTime(appointmentDTO.getAppointmentTime());
        appointmentDetailsDto.setStatus(appointmentDTO.getStatus());
        appointmentDetailsDto.setReason(appointmentDTO.getReason());
        appointmentDetailsDto.setNotes(appointmentDTO.getNotes());
        return appointmentDetailsDto;
    }

    @Override
    public List<AppointmentDetailsDto> getAppointmentDetailsWithPatientId(Long patientId) throws HMSException {
        return appointmentRepository.findAllByPatientId(patientId)
                .stream()
                .map( appointment->{
                    DoctorDTO doctorDTO = profileClient.getDoctorById(appointment.getDoctorId());

                    appointment.setDoctorName(doctorDTO.getName());
                    return appointment;
                }).toList();
    }

    @Override
    public List<AppointmentDetailsDto> getAppointmentDetailsWithDoctorId(Long patientId) throws HMSException {
        return appointmentRepository.findAllByDoctorId(patientId)
                .stream()
                .map( appointment->{
                    PatientDTO patientDTO = profileClient.getPatientById(appointment.getPatientId());

                    appointment.setPatientName(patientDTO.getName());
                    appointment.setPatientEmail(patientDTO.getEmail());
                    appointment.setPatientPhone(patientDTO.getPhone());

                    return appointment;
                }).toList();

    }
}
