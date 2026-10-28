package com.example.MhorPorn.service;

import com.example.MhorPorn.entity.Patient;
import com.example.MhorPorn.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service // บอก Spring Boot ว่าคลาสนี้คือ Service Layer
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    // ฟังก์ชันสำหรับลงทะเบียนผู้ป่วยใหม่
    public Patient registerPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    // ฟังก์ชันดึงข้อมูลผู้ป่วยทั้งหมด
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    // ฟังก์ชันค้นหาผู้ป่วยด้วยเลขบัตรประชาชน
    public Patient getPatientByIdCard(String idCard) {
        return patientRepository.findByIdCardNumber(idCard);
    }
}