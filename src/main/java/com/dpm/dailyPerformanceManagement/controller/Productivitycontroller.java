package com.dpm.dailyPerformanceManagement.controller;

import com.dpm.dailyPerformanceManagement.models.ActionPlanModel;
import com.dpm.dailyPerformanceManagement.models.ParetoModel;
import com.dpm.dailyPerformanceManagement.models.RequestModel;
import com.dpm.dailyPerformanceManagement.services.ProductivityService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/productivity")
@AllArgsConstructor
public class Productivitycontroller {

    ProductivityService productivityService;

    @PostMapping
    public void addData(@RequestBody RequestModel rm) {
        try {
            productivityService.addProductivityData(rm);
        } catch (Error error) {
            throw new RuntimeException(error);
        }
    }

    @PostMapping("/actionPlan")
    public void addActionPlan(@RequestBody List<ActionPlanModel> actionPlanModel, @RequestParam String name, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        try {
            productivityService.addActionPlan(actionPlanModel, name, date);
        } catch (Error error) {
            throw new RuntimeException(error);
        }
    }

    @PostMapping("/pareto")
    public void addPareto(@RequestBody List<ParetoModel> actionPlanModel, @RequestParam String name,
                          @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        try {
            productivityService.addPareto(actionPlanModel, name, date);
        } catch (Error error) {
            throw new RuntimeException(error);
        }
    }
}
