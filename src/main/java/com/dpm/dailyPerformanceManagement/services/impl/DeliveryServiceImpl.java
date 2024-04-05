package com.dpm.dailyPerformanceManagement.services.impl;

import com.dpm.dailyPerformanceManagement.domain.DataByDate;
import com.dpm.dailyPerformanceManagement.domain.Delivery;
import com.dpm.dailyPerformanceManagement.models.RequestModel;
import com.dpm.dailyPerformanceManagement.repositories.DataByDateRepo;
import com.dpm.dailyPerformanceManagement.repositories.DeliveryRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class DeliveryServiceImpl {
    DeliveryRepo deliveryRepo;
    DataByDateRepo dataByDateRepo;

    public void addDeliveryData(RequestModel rm){
        DataByDate dbd= dataByDateRepo.findByDateDpm(rm.getDate());
        if (dbd==null){
            List<Delivery> ds=new ArrayList<>();
            ds.add(extractedDelivery(rm));
            DataByDate dataByDate=new DataByDate();
            dataByDate.setDateDpm(rm.getDate());
            dataByDate.setDeliveries(ds);
            dataByDateRepo.save(dataByDate);
        }else {
            if (!dbd.getDeliveries().isEmpty()){

                dbd.getDeliveries().removeIf(delivery -> delivery.getName().equals(rm.getName()));
            }
        }
    }

    private Delivery extractedDelivery(RequestModel rm) {
        Delivery d=new Delivery();
        d.setTargetValue(rm.getTarget());
        d.setRealValue(rm.getReal());
        d.setName(rm.getName());
        return d;
    }

}
