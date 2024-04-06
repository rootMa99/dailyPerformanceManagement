package com.dpm.dailyPerformanceManagement.services.impl;


import com.dpm.dailyPerformanceManagement.repositories.DataByDateRepo;
import com.dpm.dailyPerformanceManagement.services.DataByDateService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class DataByDateServiceImpl implements DataByDateService {
    DataByDateRepo dataByDateRepo;



}
