package com.hms.appointment.repository;

import com.hms.appointment.entity.ApRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApRercordRepository extends JpaRepository<ApRecord, Long> {

    Optional<ApRecord> findByAppointment_Id(Long appointmentId);
}
