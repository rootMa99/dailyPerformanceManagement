package com.dpm.dailyPerformanceManagement.models;

import lombok.Data;

import java.util.Date;
@Data
public class ActionPlanModel {
    private String issueDescription;
    private String causes;
    private String contermeasures;
    private String resp;
    private Date dueDate;
    private String status;
}
