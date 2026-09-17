package com.hms.appointment.service;

import com.hms.appointment.dto.DoctorDTO;
import com.hms.appointment.dto.PatientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ApiService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    public Mono<Boolean> patientProfileExists(Long id)
    {
       return webClientBuilder.build()
                .get()
                .uri("http://localhost:9100/profile/patient/exist/" + id)
                .retrieve()
                .bodyToMono(Boolean.class);
    }

    public Mono<Boolean> doctorProfileExists(Long id)
    {
        return webClientBuilder
                .build()
                .get()
                .uri("http://localhost:9100/profile/doctor/exist/" + id)
                .retrieve()
                .bodyToMono(Boolean.class);
    }

    public Mono<PatientDTO> getPatientById(Long id)
    {
        return webClientBuilder
                .build()
                .get()
                .uri("http://localhost:9100/profile/patient/get/" + id)
                .retrieve()
                .bodyToMono(PatientDTO.class);
    }

    public Mono<DoctorDTO> getDoctorById(Long id)
    {
        return webClientBuilder
                .build()
                .get()
                .uri("http://localhost:9100/profile/doctor/get/" + id)
                .retrieve()
                .bodyToMono(DoctorDTO.class);
    }


}

