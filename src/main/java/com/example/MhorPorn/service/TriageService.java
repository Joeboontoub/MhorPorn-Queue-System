package com.example.MhorPorn.service;

import com.example.MhorPorn.entity.QueueRecord;
import com.example.MhorPorn.entity.QueueStatus;
import com.example.MhorPorn.entity.TriageRecord;
import com.example.MhorPorn.repository.QueueRecordRepository;
import com.example.MhorPorn.repository.TriageRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TriageService {

    @Autowired
    private TriageRecordRepository triageRepository;

    @Autowired
    private QueueRecordRepository queueRepository;
    

    @Transactional
    public void saveTriageAndUpdateQueue(TriageRecord triage, Long queueId) {
        QueueRecord queue = queueRepository.findById(queueId).orElseThrow();
        triage.setQueueRecord(queue);
        triageRepository.save(triage);
        
        queue.setStatus(QueueStatus.WAITING_DOCTOR);
        queueRepository.save(queue);
    }
}