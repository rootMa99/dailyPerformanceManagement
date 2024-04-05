package com.dpm.dailyPerformanceManagement.models;

import lombok.Data;

import java.util.Date;

@Data
public class RequestModel {
    private Date date;
    private double real;
    private double target;
    private String name;
}
