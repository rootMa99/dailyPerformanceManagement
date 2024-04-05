package com.dpm.dailyPerformanceManagement.services;

import com.dpm.dailyPerformanceManagement.models.ActionPlanModel;
import com.dpm.dailyPerformanceManagement.models.RequestModel;

import java.util.Date;
import java.util.List;

public interface SafetyService {
    void addSafetyData(RequestModel rm);

    void addActionPlan(List<ActionPlanModel> apm, String name, Date date);
}
