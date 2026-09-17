package com.hms.appointment.service;

import com.hms.appointment.dto.MedicineDTO;
import com.hms.appointment.entity.Medicine;
import com.hms.appointment.exception.HMSException;

import java.util.List;

public interface MedicineService {

    public Long saveMedicine(MedicineDTO medicineDTO) throws HMSException;
    public List<MedicineDTO> saveAllMedicine(List<MedicineDTO> medicineDTO) throws HMSException;
    public List<MedicineDTO> getAllMedicineByPrescriptionId(Long id) throws HMSException;

}
