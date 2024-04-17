package com.dpm.dailyPerformanceManagement.services;

import com.dpm.dailyPerformanceManagement.models.ActionPlanModel;
import com.dpm.dailyPerformanceManagement.models.ParetoModel;
import com.dpm.dailyPerformanceManagement.models.RequestModel;

import java.util.Date;
import java.util.List;

public interface KaizenService {
    void addKaizenData(RequestModel rm);

    void addActionPlan(List<ActionPlanModel> apm, String name, Date date);

    void addPareto(List<ParetoModel> pms, String name, Date date);
}
