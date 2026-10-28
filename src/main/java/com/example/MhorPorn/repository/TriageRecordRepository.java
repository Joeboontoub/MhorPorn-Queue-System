package com.example.MhorPorn.repository;

import com.example.MhorPorn.entity.TriageRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TriageRecordRepository extends JpaRepository<TriageRecord, Long> {
    
    // ฟังก์ชันสำหรับค้นหาผลคัดกรองของคิวหมายเลขนั้นๆ เพื่อให้แพทย์เปิดดู
    TriageRecord findByQueueRecordId(Long queueRecordId);
}