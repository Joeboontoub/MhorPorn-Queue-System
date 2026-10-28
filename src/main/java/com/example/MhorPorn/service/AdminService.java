package com.example.MhorPorn.service;

import com.example.MhorPorn.entity.TriageLevel;
import com.example.MhorPorn.repository.QueueRecordRepository;
import com.example.MhorPorn.repository.TriageRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AdminService {

    @Autowired
    private QueueRecordRepository queueRepo;

    @Autowired
    private TriageRecordRepository triageRepo;

    public Map<String, Long> getDashboardStats() {
        Map<String, Long> stats = new HashMap<>();
        
        // นับคิวทั้งหมด
        stats.put("totalQueues", queueRepo.count());
        
        // ดึงผลคัดกรองทั้งหมดมานับแยกตามสี
        var triages = triageRepo.findAll();
        stats.put("redCount", triages.stream().filter(t -> t.getTriageLevel() == TriageLevel.RED).count());
        stats.put("yellowCount", triages.stream().filter(t -> t.getTriageLevel() == TriageLevel.YELLOW).count());
        stats.put("greenCount", triages.stream().filter(t -> t.getTriageLevel() == TriageLevel.GREEN).count());
        stats.put("whiteCount", triages.stream().filter(t -> t.getTriageLevel() == TriageLevel.WHITE).count());
        
        return stats;
    }
}