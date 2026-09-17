package com.hms.appointment.service;

import com.hms.appointment.dto.MedicineDTO;
import com.hms.appointment.entity.Medicine;
import com.hms.appointment.exception.HMSException;
import com.hms.appointment.repository.MedicineRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MedicineServiceImpl implements MedicineService {

    @Autowired
    private MedicineRepository medicineRepository;

    @Override
    public Long saveMedicine(MedicineDTO medicineDTO) throws HMSException {
        return medicineRepository.save(medicineDTO.toEntity()).getId();
    }

    @Override
    public List<MedicineDTO> saveAllMedicine(List<MedicineDTO> request) throws HMSException {
       return  ((List<Medicine>) medicineRepository.saveAll(request.stream().map(MedicineDTO::toEntity).toList()))
                .stream().map(Medicine::toDTO).toList();

    }

    @Override
    public List<MedicineDTO> getAllMedicineByPrescriptionId(Long id) throws HMSException {
        return medicineRepository.findAllByPrescription_Id(id).stream().map(Medicine::toDTO).toList();
    }
}
