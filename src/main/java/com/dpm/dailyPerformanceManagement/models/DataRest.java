package com.dpm.dailyPerformanceManagement.models;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class DataRest {
    private Date dDate;
    private double real;
    private double target;
    private String name;
    private String type;
    private List<ActionPlanModel> apm;
    private List<ParetoModel> pms;
}
