package com.dpm.dailyPerformanceManagement.services.impl;

import com.dpm.dailyPerformanceManagement.domain.DKpiNames;
import com.dpm.dailyPerformanceManagement.repositories.*;
import com.dpm.dailyPerformanceManagement.services.KpiNamesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class KpiNamesServiceImpl implements KpiNamesService {
    DKpiNamesRepo dKpiNamesRepo;
    IKpiNamesRepo iKpiNamesRepo;
    KKpiNamesRepo kKpiNamesRepo;
    PKpiNamesRepo pKpiNamesRepo;
    QKpiNamesRepo qKpiNamesRepo;
    SfKpiNamesRepo sfKpiNamesRepo;
    SkKpiNamesRepo skKpiNamesRepo;

    public List<String> kpiNamesList(String name) {
        List<String> ls = new ArrayList<>();
        if (name.equals("delivery")) {
            List<DKpiNames> d = dKpiNamesRepo.findAll();

            for (DKpiNames dk : d) {
                ls.add(dk.getKpiName());
            }

        }
        return ls;
    }
}
