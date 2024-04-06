package com.dpm.dailyPerformanceManagement.controller;

import com.dpm.dailyPerformanceManagement.models.DataRest;
import com.dpm.dailyPerformanceManagement.services.DataByDateService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/dpm")
@AllArgsConstructor
public class MainController {
    DataByDateService dataByDateService;

    @GetMapping(path = "/delivery")
    public List<DataRest> getDelivery(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                                         @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate) {
        return dataByDateService.getDelivriesDateBetween(startDate, endDate);
    }
    @GetMapping(path = "/inventory")
    public List<DataRest> getInventory(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                                         @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate) {
        return dataByDateService.getInventoryDateBetween(startDate, endDate);
    }


}
