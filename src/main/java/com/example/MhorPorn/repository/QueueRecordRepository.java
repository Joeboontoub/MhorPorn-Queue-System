package com.example.MhorPorn.repository;

import com.example.MhorPorn.entity.QueueRecord;
import com.example.MhorPorn.entity.QueueStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QueueRecordRepository extends JpaRepository<QueueRecord, Long> {
    
    // ฟังก์ชันสำหรับดึงประวัติการจองคิวทั้งหมดของคนไข้คนนี้
    List<QueueRecord> findByPatientId(Long patientId);
    
    // ฟังก์ชันสำหรับดึงคิวทั้งหมดที่อยู่ในสถานะที่กำหนด (เช่น หาคิวที่รอตรวจทั้งหมด)
    List<QueueRecord> findByStatus(QueueStatus status);
}