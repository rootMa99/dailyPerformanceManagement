package com.dpm.dailyPerformanceManagement.services.impl;


import com.dpm.dailyPerformanceManagement.domain.Delivery;
import com.dpm.dailyPerformanceManagement.models.DataRest;
import com.dpm.dailyPerformanceManagement.repositories.DeliveryRepo;
import com.dpm.dailyPerformanceManagement.services.DataByDateService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
@AllArgsConstructor
public class DataByDateServiceImpl implements DataByDateService {
    DeliveryRepo deliveryRepo;


    @Override
    public List<DataRest> getDelivriesDateBetween(Date start, Date end){

        List<Delivery> ds = deliveryRepo.findAllByDbdDateDpmBetween(start, end);
        System.out.println(ds);
        return null;
    }

}
