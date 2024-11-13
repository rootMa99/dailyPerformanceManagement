package com.dpm.dailyPerformanceManagement.services;


import com.dpm.dailyPerformanceManagement.models.ActionPlanModel;

import java.util.Date;
import java.util.List;

public interface ActionPlanService {
    List<ActionPlanModel> apm(Date startDate, Date endDate);

    ActionPlanModel updateActionPlan(ActionPlanModel ap, Long id);
}
