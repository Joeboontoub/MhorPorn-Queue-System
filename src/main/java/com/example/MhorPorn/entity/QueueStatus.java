package com.example.MhorPorn.entity;

public enum QueueStatus {
    WAITING_TRIAGE,  // รอคัดกรองอาการ (สถานะเริ่มต้นเมื่อกดรับคิว)
    WAITING_DOCTOR,  // รอพบแพทย์ (ผ่านการซักประวัติแล้ว)
    IN_PROGRESS,     // กำลังตรวจ (แพทย์เรียกคิวแล้ว)
    COMPLETED,       // ตรวจเสร็จสิ้น / รับยา
    CANCELLED        // ยกเลิกคิว
}