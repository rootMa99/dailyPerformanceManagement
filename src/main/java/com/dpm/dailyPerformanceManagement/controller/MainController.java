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

    @GetMapping(path = "/deliveryDataBetween")
    public List<DataRest> getDataBetween(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                                         @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate) {
        return dataByDateService.getDelivriesDateBetween(startDate, endDate);
    }


}
