package com.dpm.dailyPerformanceManagement.models;

import lombok.Data;

@Data
public class KpiNameRest {
    private String kpiName;
    private String alias;
    private String type;
}
