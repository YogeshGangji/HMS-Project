package com.hms.appointment.service;

import com.hms.appointment.dto.PrescriptionDTO;
import com.hms.appointment.exception.HMSException;
import com.hms.appointment.repository.MedicineRepository;
import com.hms.appointment.repository.PrescriptionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PrescriptionServiceImpl implements PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private MedicineService medicineService;

    @Override
    public Long savePrescription(PrescriptionDTO prescription) throws HMSException {
        Long prescriptionId = prescriptionRepository.save(prescription.toEntity()).getId();
        prescription.getMedicines().forEach(medicine -> {
            medicine.setPrescriptionId(prescriptionId);
        });
        medicineService.saveAllMedicine(prescription.getMedicines());
        prescription.setId(prescriptionId);
        return prescriptionId;
    }

    @Override
    public PrescriptionDTO getPrescriptionById(Long id) throws HMSException {
        PrescriptionDTO  prescriptionDTO = prescriptionRepository.findById(id).orElseThrow(()-> new HMSException("PRESCRIPTION_NOT_FOUND")).toDTO();

        prescriptionDTO.setMedicines(medicineService.getAllMedicineByPrescriptionId(prescriptionDTO.getId()));

return prescriptionDTO;
    }

    @Override
    public PrescriptionDTO getPrescriptionByAppointmentId(Long id) throws HMSException {
        PrescriptionDTO  prescriptionDTO = prescriptionRepository
                                            .findByAppointment_Id((id))
                .orElseThrow(() -> new HMSException("PRESCRIPTION_NOT_FOUND"))
                .toDTO();

        prescriptionDTO.setMedicines(medicineService.getAllMedicineByPrescriptionId(prescriptionDTO.getId()));
        return prescriptionDTO;
    }
}
