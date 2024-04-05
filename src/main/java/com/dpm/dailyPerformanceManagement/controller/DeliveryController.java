package com.dpm.dailyPerformanceManagement.controller;

import com.dpm.dailyPerformanceManagement.models.RequestModel;
import com.dpm.dailyPerformanceManagement.services.DeliveryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/delivery")
@AllArgsConstructor
public class DeliveryController {

    DeliveryService deliveryService;
    @PostMapping
    public void addData(@RequestBody RequestModel rm){
        try {
            deliveryService.addDeliveryData(rm);
        }catch (Error error){
            throw new RuntimeException(error);
        }
    }

}
