package com.example.MhorPorn.controller;

import com.example.MhorPorn.entity.Patient;
import com.example.MhorPorn.entity.QueueRecord;
import com.example.MhorPorn.entity.TriageRecord;
import com.example.MhorPorn.repository.QueueRecordRepository;
import com.example.MhorPorn.service.PatientService;
import com.example.MhorPorn.service.QueueService;
import com.example.MhorPorn.service.TriageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class QueueController {
	
	@Autowired
    private org.springframework.messaging.simp.SimpMessagingTemplate messagingTemplate;
    @Autowired
    private QueueService queueService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private TriageService triageService;
    @Autowired
    private QueueRecordRepository queueRepository;
    @Autowired
    private com.example.MhorPorn.repository.TriageRecordRepository triageRepository;

    @GetMapping("/")
    public String showDashboard(Model model) {
        List<QueueRecord> waitingQueues = queueService.getWaitingTriageQueues();
        model.addAttribute("queues", waitingQueues);
        return "dashboard";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "register";
    }

    @PostMapping("/register")
    public String registerPatient(@ModelAttribute Patient patient, org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        Patient savedPatient = patientService.registerPatient(patient);
        queueService.generateQueue(savedPatient);
        redirectAttributes.addFlashAttribute("successMsg", "✅ ลงทะเบียนผู้ป่วยและออกหมายเลขคิวสำเร็จ!");
        messagingTemplate.convertAndSend("/topic/queue-updates", "UPDATE");
        return "redirect:/";
    }

    // --- ส่วนที่เพิ่มใหม่สำหรับระบบคัดกรอง ---

    // เปิดหน้าฟอร์มคัดกรองอาการ โดยอ้างอิงจาก ID ของคิว
    @GetMapping("/triage/{id}")
    public String showTriageForm(@PathVariable("id") Long id, Model model) {
        QueueRecord queue = queueRepository.findById(id).orElseThrow();
        model.addAttribute("queue", queue);
        model.addAttribute("triage", new TriageRecord()); // ส่ง Object ว่างไปรอรับค่าสัญญาณชีพ
        return "triage";
    }

    // รับข้อมูลสัญญาณชีพมาบันทึก
    @PostMapping("/triage/{id}")
    public String saveTriage(@PathVariable("id") Long id, @ModelAttribute TriageRecord triage, org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        triageService.saveTriageAndUpdateQueue(triage, id);
        redirectAttributes.addFlashAttribute("successMsg", "✅ บันทึกสัญญาณชีพและส่งผู้ป่วยเข้าห้องตรวจสำเร็จ!");
        messagingTemplate.convertAndSend("/topic/queue-updates", "UPDATE");
        return "redirect:/"; 
    }
    
    // เปิดหน้าแดชบอร์ดคิวรอตรวจของคุณหมอ
    @GetMapping("/doctor")
    public String showDoctorDashboard(Model model) {
        List<QueueRecord> doctorQueues = queueService.getWaitingDoctorQueues();
        model.addAttribute("queues", doctorQueues);
        return "doctor-dashboard";
    }
 // เปิดหน้าห้องตรวจของแพทย์ เพื่อดูประวัติและข้อมูลคัดกรอง
    @GetMapping("/examine/{id}")
    public String showExamineRoom(@PathVariable("id") Long id, Model model) {
        QueueRecord queue = queueRepository.findById(id).orElseThrow();
        TriageRecord triage = triageRepository.findByQueueRecordId(id); // ดึงข้อมูลสัญญาณชีพที่พยาบาลบันทึกไว้
        
        model.addAttribute("queue", queue);
        model.addAttribute("triage", triage);
        return "examine";
    }

    // เมื่อแพทย์กดปุ่ม "ตรวจเสร็จสิ้น/รับยา"
    @PostMapping("/examine/{id}")
    public String finishExamination(@PathVariable("id") Long id, org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        QueueRecord queue = queueRepository.findById(id).orElseThrow();
        queue.setStatus(com.example.MhorPorn.entity.QueueStatus.COMPLETED);
        queueRepository.save(queue);
        redirectAttributes.addFlashAttribute("successMsg", "✅ ตรวจผู้ป่วยเสร็จสิ้น เรียบร้อยแล้ว!");
        messagingTemplate.convertAndSend("/topic/queue-updates", "UPDATE");
        return "redirect:/doctor"; 
    }
 // --- ส่วนหน้าจอ TV สาธารณะ ---
    @GetMapping("/tv")
    public String showTvDisplay(Model model) {
        // ส่งคิวทั้งหมดไปแสดงผลบนทีวี แบ่งเป็น 2 ช่อง
        model.addAttribute("triageQueues", queueService.getWaitingTriageQueues());
        model.addAttribute("doctorQueues", queueService.getWaitingDoctorQueues());
        return "tv-display";
    }
}