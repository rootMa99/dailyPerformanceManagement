package com.dpm.dailyPerformanceManagement.controller;

import com.dpm.dailyPerformanceManagement.models.RequestModel;
import com.dpm.dailyPerformanceManagement.services.InventoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/inventory")
@AllArgsConstructor
public class InventoryController {
    InventoryService inventoryService;

    @PostMapping
    public void addData(@RequestBody RequestModel rm) {
        try {
            inventoryService.addInventoryData(rm);
        } catch (Error error) {
            throw new RuntimeException(error);
        }
    }



}
