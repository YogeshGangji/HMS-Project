package com.hms.appointment.controller;

import com.hms.appointment.dto.AppointmentDTO;
import com.hms.appointment.dto.AppointmentDetailsDto;
import com.hms.appointment.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/appointment")
@Validated
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/schedule")
    public ResponseEntity<Long> sceduleAppointment(@RequestBody AppointmentDTO appointmentDTO)
    {
//        System.out.println("========== APPOINTMENT CONTROLLER ==========");
//        System.out.println(appointmentDTO);
        return new ResponseEntity<>(appointmentService.sceduleAppointment(appointmentDTO), HttpStatus.CREATED);
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<String> cancelAppointment(@PathVariable("id") long appointmentId)
    {
        appointmentService.cancelAppointment(appointmentId);
        return new ResponseEntity<>("Appointment cancelled", HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<AppointmentDTO> getAppointmentById(@PathVariable("id") long appointmentId)
    {
        return new ResponseEntity<>(appointmentService.getAppointmentDetails(appointmentId), HttpStatus.OK);
    }

    @GetMapping("/get/details/{id}")
    public ResponseEntity<AppointmentDetailsDto> getAppointmentDetailsById(@PathVariable Long id)
    {
        return new ResponseEntity<>(appointmentService.getAppointmentDetailsWithName(id),HttpStatus.OK);
    }

    @GetMapping("/getAllByPatient/{patientId}")
    public ResponseEntity<List<AppointmentDetailsDto>> getAllAppointmentsDetailsByPatientId(@PathVariable("patientId") Long patientId)
    {
        return new ResponseEntity<>(appointmentService.getAppointmentDetailsWithPatientId(patientId),HttpStatus.OK);

    }

    @GetMapping("/getAllByDoctor/{doctorId}")
    public ResponseEntity<List<AppointmentDetailsDto>> getAllAppointmentsDetailsByDoctorId(@PathVariable("doctorId") Long doctorId)
    {
        return new ResponseEntity<>(appointmentService.getAppointmentDetailsWithDoctorId(doctorId),HttpStatus.OK);

    }

    @GetMapping("/test")
    public String test() {
        System.out.println("🔥🔥🔥 APPOINTMENT CONTROLLER HIT 🔥🔥🔥");
        return "Appointment MS is working";
    }
}
