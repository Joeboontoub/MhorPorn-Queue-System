package com.example.MhorPorn.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "queue_records")
@NoArgsConstructor
@AllArgsConstructor
public class QueueRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // รหัสอ้างอิงของคิวในระบบฐานข้อมูล

    @Column(name = "queue_number", nullable = false, length = 30)
    private String queueNumber; // หมายเลขคิวที่จะแสดงบนหน้าจอ เช่น A001, B012

    @Enumerated(EnumType.STRING) // บันทึกค่า Enum เป็นตัวอักษรลง Database
    @Column(name = "status", nullable = false)
    private QueueStatus status; // สถานะของคิว (ดึงมาจากไฟล์ QueueStatus ที่เราเพิ่งสร้าง)

    // การเชื่อมความสัมพันธ์ (Relationship)
    // @ManyToOne คือ คิวหลายใบ สามารถเป็นของคนไข้ 1 คนได้
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient; 

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt; // เวลาที่กดออกบัตรคิว

    @Column(name = "updated_at")
    private LocalDateTime updatedAt; // เวลาที่มีการเปลี่ยนสถานะล่าสุด

    // ฟังก์ชันทำงานอัตโนมัติก่อนบันทึกคิวใหม่ลงฐานข้อมูล
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = QueueStatus.WAITING_TRIAGE; // เซ็ตสถานะเริ่มต้นให้เป็น "รอคัดกรอง" อัตโนมัติ
        }
    }

    // ฟังก์ชันทำงานอัตโนมัติเมื่อมีการแก้ไขสถานะคิว
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getQueueNumber() {
		return queueNumber;
	}

	public void setQueueNumber(String queueNumber) {
		this.queueNumber = queueNumber;
	}

	public QueueStatus getStatus() {
		return status;
	}

	public void setStatus(QueueStatus status) {
		this.status = status;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
}