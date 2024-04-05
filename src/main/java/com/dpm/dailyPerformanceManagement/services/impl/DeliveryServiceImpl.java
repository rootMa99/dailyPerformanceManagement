package com.dpm.dailyPerformanceManagement.services.impl;

import com.dpm.dailyPerformanceManagement.domain.DataByDate;
import com.dpm.dailyPerformanceManagement.domain.Delivery;
import com.dpm.dailyPerformanceManagement.models.RequestModel;
import com.dpm.dailyPerformanceManagement.repositories.DataByDateRepo;
import com.dpm.dailyPerformanceManagement.repositories.DeliveryRepo;
import com.dpm.dailyPerformanceManagement.services.DeliveryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {
    DeliveryRepo deliveryRepo;
    DataByDateRepo dataByDateRepo;
    @Override
    public void addDeliveryData(RequestModel rm){
        DataByDate dbd= dataByDateRepo.findByDateDpm(rm.getDate());
        if (dbd==null){
            Delivery d= extractedDelivery(rm);
            DataByDate dataByDate=new DataByDate();
            dataByDate.setDateDpm(rm.getDate());
            dataByDate= dataByDateRepo.save(dataByDate);
            d.setDbd(dataByDate);
            deliveryRepo.save(d);
        }else {
            if (!dbd.getDeliveries().isEmpty()){
                Optional<Delivery> deliveryWithNameAp = dbd.getDeliveries().stream()
                        .filter(delivery -> delivery.getName().equals(rm.getName()))
                        .findFirst();

                if (deliveryWithNameAp.isPresent()) {
                    Delivery delivery = deliveryWithNameAp.get();
                    delivery.setRealValue(rm.getReal());
                    delivery.setTargetValue(rm.getTarget());
                    deliveryRepo.save(delivery);
                } else {
                    Delivery d= extractedDelivery(rm);
                    d.setDbd(dbd);
                    deliveryRepo.save(d);
                }
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
