package com.dpm.dailyPerformanceManagement.controller;

import com.dpm.dailyPerformanceManagement.models.ActionPlanModel;
import com.dpm.dailyPerformanceManagement.models.RequestModel;
import com.dpm.dailyPerformanceManagement.services.SafetyService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/safety")
@AllArgsConstructor
public class SafetyController {

    SafetyService safetyService;

    @PostMapping
    public void addData(@RequestBody RequestModel rm) {
        try {
            safetyService.addSafetyData(rm);
        } catch (Error error) {
            throw new RuntimeException(error);
        }
    }

    @PostMapping("/actionPlan")
    public void addActionPlan(@RequestBody List<ActionPlanModel> actionPlanModel, @RequestParam String name, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        try {
            safetyService.addActionPlan(actionPlanModel, name, date);
        } catch (Error error) {
            throw new RuntimeException(error);
        }
    }

}
