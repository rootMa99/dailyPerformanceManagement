package com.dpm.dailyPerformanceManagement.services;

import com.dpm.dailyPerformanceManagement.models.DataRest;
import com.dpm.dailyPerformanceManagement.models.KpiRest;

import java.util.Date;
import java.util.List;

public interface DataByDateService {
    List<DataRest> getDelivriesDateBetween(Date start, Date end);

    List<DataRest> getInventoryDateBetween(Date start, Date end);

    List<DataRest> getKaizenDateBetween(Date start, Date end);

    List<DataRest> getProductivityDateBetween(Date start, Date end);

    List<DataRest> getQualityDateBetween(Date start, Date end);

    List<DataRest> getSafetyDateBetween(Date start, Date end);

    List<DataRest> getSkillsDateBetween(Date start, Date end);

    List<KpiRest> getAllKpiOwner();
}
