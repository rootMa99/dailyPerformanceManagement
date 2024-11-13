package com.dpm.dailyPerformanceManagement.models;

import lombok.Data;

import java.util.Date;
@Data
public class ActionPlanModel {
    private Long id;
    private String issueDescription;
    private String causes;
    private String contermeasures;
    private String resp;
    private Date openDate;
    private Date dueDate;
    private String status;
    private String cb;
    private String rci;
    private String cai;
    private String epd;
    private String lpa;
    private String cav;
    private String pcu;
    private String swoi;
    private String ll;
}
