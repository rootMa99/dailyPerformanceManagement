package com.dpm.dailyPerformanceManagement.services;

import com.dpm.dailyPerformanceManagement.models.KpiNameRest;

import java.util.List;

public interface KpiNamesService {
    List<KpiNameRest> kpiNamesList(String name);
}
