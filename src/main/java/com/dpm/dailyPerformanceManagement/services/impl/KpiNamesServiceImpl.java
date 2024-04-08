package com.dpm.dailyPerformanceManagement.services.impl;

import com.dpm.dailyPerformanceManagement.domain.*;
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

    @Override
    public List<String> kpiNamesList(String name) {
        List<String> ls = new ArrayList<>();
        if (name.equals("delivery")) {
            List<DKpiNames> d = dKpiNamesRepo.findAll();
            for (DKpiNames dk : d) {
                ls.add(dk.getKpiName());
            }
        }
        if (name.equals("safety")) {
            List<SfKpiNames> d = sfKpiNamesRepo.findAll();
            for (SfKpiNames dk : d) {
                ls.add(dk.getKpiName());
            }
        }
        if (name.equals("skills")) {
            List<SkKpiNames> d = skKpiNamesRepo.findAll();
            for (SkKpiNames dk : d) {
                ls.add(dk.getKpiName());
            }
        }
        if (name.equals("quality")) {
            List<QKpiNames> d = qKpiNamesRepo.findAll();
            for (QKpiNames dk : d) {
                ls.add(dk.getKpiName());
            }
        }
        if (name.equals("kaizen")) {
            List<KKpiNames> d = kKpiNamesRepo.findAll();
            for (KKpiNames dk : d) {
                ls.add(dk.getKpiName());
            }
        }
        if (name.equals("inventory")) {
            List<IKpiNames> d = iKpiNamesRepo.findAll();
            for (IKpiNames dk : d) {
                ls.add(dk.getKpiName());
            }
        }
        if (name.equals("productivity")) {
            List<PkpiNames> d = pKpiNamesRepo.findAll();
            for (PkpiNames dk : d) {
                ls.add(dk.getKpiName());
            }
        }
        return ls;
    }
}
