package com.example.MhorPorn.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "patients") // กำหนดชื่อตารางใน MySQL
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // รหัสอ้างอิงผู้ป่วย (รันตัวเลขให้อัตโนมัติ)

    @Column(name = "id_card_number", length = 13, unique = true, nullable = false)
    private String idCardNumber; // เลขบัตรประชาชน 13 หลัก (ห้ามซ้ำ ห้ามว่าง)

    @Column(name = "first_name", nullable = false)
    private String firstName; // ชื่อจริง

    @Column(name = "last_name", nullable = false)
    private String lastName; // นามสกุล

    @Column(name = "phone_number", length = 10)
    private String phoneNumber; // เบอร์โทรศัพท์

    @Column(name = "blood_type", length = 3)
    private String bloodType; // กรุ๊ปเลือด

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdCardNumber() {
		return idCardNumber;
	}

	public void setIdCardNumber(String idCardNumber) {
		this.idCardNumber = idCardNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getBloodType() {
		return bloodType;
	}

	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}

	public String getAllergies() {
		return allergies;
	}

	public void setAllergies(String allergies) {
		this.allergies = allergies;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	@Column(columnDefinition = "TEXT")
    private String allergies; // ประวัติการแพ้ยา (ใช้ TEXT เพราะอาจจะยาว)

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt; // วันเวลาที่ลงทะเบียน

    // ฟังก์ชันนี้จะทำงานอัตโนมัติก่อนบันทึกข้อมูลลงฐานข้อมูล เพื่อสแตมป์เวลาปัจจุบัน
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}