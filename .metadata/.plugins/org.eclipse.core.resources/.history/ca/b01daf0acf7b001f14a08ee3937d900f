package com.teleconnect.controller;

import com.teleconnect.dto.AdminDashboardResponse;
import com.teleconnect.service.AdminDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminDashboardController {

    @Autowired
    private AdminDashboardService adminDashboardService;

    @GetMapping("/dashboard")
    public AdminDashboardResponse getDashboardStatistics() {
        return adminDashboardService.getDashboardStatistics();
    }
}
