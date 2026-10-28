package com.example.MhorPorn.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "triage_records")
@NoArgsConstructor
@AllArgsConstructor
public class TriageRecord {

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	public String getBloodPressure() {
		return bloodPressure;
	}

	public void setBloodPressure(String bloodPressure) {
		this.bloodPressure = bloodPressure;
	}

	public String getSymptoms() {
		return symptoms;
	}

	public void setSymptoms(String symptoms) {
		this.symptoms = symptoms;
	}

	public TriageLevel getTriageLevel() {
		return triageLevel;
	}

	public void setTriageLevel(TriageLevel triageLevel) {
		this.triageLevel = triageLevel;
	}

	public QueueRecord getQueueRecord() {
		return queueRecord;
	}

	public void setQueueRecord(QueueRecord queueRecord) {
		this.queueRecord = queueRecord;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double weight; // น้ำหนัก (kg)
    private Double height; // ส่วนสูง (cm)
    private Double temperature; // อุณหภูมิร่างกาย (องศาเซลเซียส)
    
    @Column(name = "blood_pressure")
    private String bloodPressure; // ความดันโลหิต เช่น "120/80"

    @Column(columnDefinition = "TEXT")
    private String symptoms; // อาการสำคัญที่มาพบแพทย์

    @Enumerated(EnumType.STRING)
    @Column(name = "triage_level", nullable = false)
    private TriageLevel triageLevel; // ระดับความฉุกเฉิน

    // การเชื่อมความสัมพันธ์แบบ 1 ต่อ 1 (1 คิว มีผลคัดกรองได้ 1 ใบ)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "queue_record_id", nullable = false)
    private QueueRecord queueRecord;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}