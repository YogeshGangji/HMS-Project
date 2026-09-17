package com.hms.appointment.controller;

import com.hms.appointment.dto.ApRecordDTO;
import com.hms.appointment.exception.HMSException;
import com.hms.appointment.service.ApRecordService;
import jakarta.ws.rs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("appointment/report/")
@Validated
public class ApRecordController {

    @Autowired
    private ApRecordService apRecordService;


    @PostMapping("/create")
    public ResponseEntity<Long> createAppointmentReport(@RequestBody ApRecordDTO apRecordDTO) throws HMSException
    {
            return new ResponseEntity<>(apRecordService.createAppointmentRecord(apRecordDTO), HttpStatus.CREATED);
    }

    @PutMapping ("/update")
    public ResponseEntity<String> updateAppointmentReport(@RequestBody ApRecordDTO apRecordDTO)
    {
        apRecordService.updateAppointmentRecord(apRecordDTO);
        return new ResponseEntity<>("Appointment Record Updated Successfully!!", HttpStatus.OK);
    }

        @GetMapping("/getByAppointmentId/{id}")
        public ResponseEntity<ApRecordDTO> getAppointmentRecordByAppointmentId(@PathVariable("id") Long id) throws HMSException {
            return new ResponseEntity<>(apRecordService.getAppointmentRecordByAppointmentId(id), HttpStatus.OK);
        }

    @GetMapping("/getDetailsByAppointmentId/{id}")
    public ResponseEntity<ApRecordDTO> getAppointmentRecordDetailsByAppointmentId(@PathVariable("id") Long id) throws HMSException {
        return new ResponseEntity<>(apRecordService.getAppointmentRecordDetailsByAppointmentId(id), HttpStatus.OK);
    }

        @GetMapping("/getById/{id}")
    public ResponseEntity<ApRecordDTO> getAppointmentRecordById(@PathVariable("id") Long id) throws HMSException {
        return new ResponseEntity<>(apRecordService.getAppointmentRecordById(id), HttpStatus.OK);
        }

}
