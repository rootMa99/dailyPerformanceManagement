package com.dpm.dailyPerformanceManagement.controller;


import com.dpm.dailyPerformanceManagement.domain.ActionPlan;
import com.dpm.dailyPerformanceManagement.models.ActionPlanModel;
import com.dpm.dailyPerformanceManagement.models.ParetoModel;
import com.dpm.dailyPerformanceManagement.models.RequestModel;
import com.dpm.dailyPerformanceManagement.services.KaizenService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/kaizen")
@AllArgsConstructor
public class KaizenController {
    KaizenService kaizenService;

    @PostMapping
    public void addData(@RequestBody RequestModel rm) {
        try {
            kaizenService.addKaizenData(rm);
        } catch (Error error) {
            throw new RuntimeException(error);
        }
    }

    @PostMapping(path = "/uploadData")
    public void saveDataToDataBase(MultipartFile file) throws IllegalAccessException {
        kaizenService.addDataViaExcel(file);
    }


    @PostMapping("/actionPlan")
    public ActionPlanModel addActionPlan(@RequestBody ActionPlanModel actionPlanModel, @RequestParam String name,
                                         @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        try {
            ActionPlan ap = kaizenService.addActionPlan(actionPlanModel, name, date);
            ActionPlanModel actionPlanModel1 = new ActionPlanModel();
            actionPlanModel1.setId(ap.getId());
            actionPlanModel1.setResp(ap.getResp());
            actionPlanModel1.setStatus(ap.getStatus());
            actionPlanModel1.setContermeasures(ap.getContermeasures());
            actionPlanModel1.setCauses(ap.getCauses());
            actionPlanModel1.setDueDate(ap.getDueDate());
            actionPlanModel1.setOpenDate(ap.getOpenDate());
            actionPlanModel1.setIssueDescription(ap.getIssueDescription());
            return actionPlanModel1;
        } catch (Error error) {
            throw new RuntimeException(error);
        }
    }

    @PostMapping("/pareto")
    public void addPareto(@RequestBody List<ParetoModel> actionPlanModel, @RequestParam String name,
                          @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        try {
            kaizenService.addPareto(actionPlanModel, name, date);
        } catch (Error error) {
            throw new RuntimeException(error);
        }
    }
}
