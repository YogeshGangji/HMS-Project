package com.hms.user.client;

import com.hms.user.dto.UserDTO;
import jakarta.ws.rs.POST;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ProfileMS")
public interface Client {

    @PostMapping("/profile/doctor/add")
    public Long addDoctorProfile(@RequestBody UserDTO userDTO);

    @PostMapping("/profile/patient/add")
    public Long addPatientProfile(@RequestBody UserDTO userDTO);
 }
