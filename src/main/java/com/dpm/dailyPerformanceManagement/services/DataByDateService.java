package com.dpm.dailyPerformanceManagement.services;

import com.dpm.dailyPerformanceManagement.models.DataRest;

import java.util.Date;
import java.util.List;

public interface DataByDateService {
    List<DataRest> getDelivriesDateBetween(Date start, Date end);
}
