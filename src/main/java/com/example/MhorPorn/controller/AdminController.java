package com.example.MhorPorn.controller;

import com.example.MhorPorn.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/admin")
    public String showAdminDashboard(Model model) {
        model.addAttribute("stats", adminService.getDashboardStats());
        return "admin-dashboard";
    }
}