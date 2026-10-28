package com.example.MhorPorn.repository;

import com.example.MhorPorn.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    
    // Spring Data JPA เตรียมคำสั่งพื้นฐาน (Save, FindAll, FindById, Delete) ไว้ให้หมดแล้ว
    // เราสามารถเพิ่มฟังก์ชันค้นหาเฉพาะเจาะจงได้ง่ายๆ แค่ตั้งชื่อเมธอดให้ตรงกับชื่อฟิลด์ เช่น:
    
    // ฟังก์ชันสำหรับค้นหาประวัติผู้ป่วยจากเลขบัตรประชาชน
    Patient findByIdCardNumber(String idCardNumber);
}