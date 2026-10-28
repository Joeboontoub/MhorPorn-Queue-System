package com.example.MhorPorn.controller;

import com.example.MhorPorn.entity.QueueRecord;
import com.example.MhorPorn.service.AdminService;
import com.example.MhorPorn.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1") // กำหนดเส้นทางหลักสำหรับ API เวอร์ชัน 1
public class ApiQueueController {

    @Autowired
    private QueueService queueService;

    @Autowired
    private AdminService adminService;

    // 1. API สำหรับดึงรายชื่อคิวที่กำลังรอพบแพทย์ (ให้คนไข้เปิดดูผ่านแอปมือถือจากที่บ้านได้)
    @GetMapping("/queues/waiting")
    public List<QueueRecord> getWaitingDoctorQueues() {
        return queueService.getWaitingDoctorQueues();
    }

    // 2. API สำหรับดึงสถิติของโรงพยาบาล (ให้ผู้อำนวยการเปิดดู Dashboard ผ่านแอปมือถือ)
    @GetMapping("/stats")
    public Map<String, Long> getHospitalStats() {
        return adminService.getDashboardStats();
    }
}