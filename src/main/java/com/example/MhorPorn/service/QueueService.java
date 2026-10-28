package com.example.MhorPorn.service;

import com.example.MhorPorn.entity.Patient;
import com.example.MhorPorn.entity.QueueRecord;
import com.example.MhorPorn.entity.QueueStatus;
import com.example.MhorPorn.repository.QueueRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class QueueService {

    @Autowired
    private QueueRecordRepository queueRepository;

    @Transactional // ป้องกันข้อมูลสูญหายหรือเซฟไม่สมบูรณ์
    public QueueRecord generateQueue(Patient patient) {
        QueueRecord queue = new QueueRecord();
        queue.setPatient(patient);
        
        String datePrefix = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long queueCount = queueRepository.count() + 1; 
        
        // อัปเกรด: เติมเลข 0 นำหน้าให้เป็น 3 หลัก (เช่น 001, 002)
        String formattedCount = String.format("%03d", queueCount);
        queue.setQueueNumber("Q-" + datePrefix + "-" + formattedCount);
        
        queue.setStatus(QueueStatus.WAITING_TRIAGE); 
        return queueRepository.save(queue);
    }

    public List<QueueRecord> getWaitingTriageQueues() {
        return queueRepository.findByStatus(QueueStatus.WAITING_TRIAGE);
    }

    public List<QueueRecord> getWaitingDoctorQueues() {
        return queueRepository.findByStatus(QueueStatus.WAITING_DOCTOR);
    }
}