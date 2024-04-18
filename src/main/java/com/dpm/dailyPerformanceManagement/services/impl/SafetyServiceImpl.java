package com.dpm.dailyPerformanceManagement.services.impl;

import com.dpm.dailyPerformanceManagement.domain.*;
import com.dpm.dailyPerformanceManagement.models.ActionPlanModel;
import com.dpm.dailyPerformanceManagement.models.ParetoModel;
import com.dpm.dailyPerformanceManagement.models.RequestModel;
import com.dpm.dailyPerformanceManagement.repositories.*;
import com.dpm.dailyPerformanceManagement.services.SafetyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SafetyServiceImpl implements SafetyService {
    DataByDateRepo dataByDateRepo;
    ActionPlanRepo actionPlanRepo;
    SafetyRepo safetyRepo;
    SfKpiNamesRepo sfKpiNamesRepo;
    ParetoRepo paretoRepo;

    @Override
    public void addSafetyData(RequestModel rm) {
        SfKpiNames dKpiNames = sfKpiNamesRepo.findByKpiName(rm.getName());
        if (dKpiNames == null) {
            SfKpiNames dk = new SfKpiNames();
            dk.setAlias(rm.getAlias());
            if (rm.getName() == null) {
                dk.setKpiName(rm.getAlias());
            } else {
                dk.setKpiName(rm.getName());
            }
            dk.setType(rm.getType());
            sfKpiNamesRepo.save(dk);
        }
        DataByDate dbd = dataByDateRepo.findByDateDpm(rm.getDate());
        if (dbd == null) {
            Safety d = extractedDelivery(rm);
            DataByDate dataByDate = new DataByDate();
            dataByDate.setDateDpm(rm.getDate());
            dataByDate = dataByDateRepo.save(dataByDate);
            d.setDbd(dataByDate);
            safetyRepo.save(d);
        } else {
            if (!dbd.getSafeties().isEmpty()) {
                Optional<Safety> deliveryWithNameAp = dbd.getSafeties().stream().filter(delivery -> delivery.getName().equals(rm.getName())).findFirst();

                if (deliveryWithNameAp.isPresent()) {
                    Safety delivery = deliveryWithNameAp.get();
                    delivery.setRealValue(rm.getReal());
                    delivery.setTargetValue(rm.getTarget());
                    safetyRepo.save(delivery);
                } else {
                    Safety d = extractedDelivery(rm);
                    d.setDbd(dbd);
                    safetyRepo.save(d);
                }
            } else {
                Safety d = extractedDelivery(rm);
                d.setDbd(dbd);
                safetyRepo.save(d);
            }
        }
    }

    private Safety extractedDelivery(RequestModel rm) {
        Safety d = new Safety();
        d.setTargetValue(rm.getTarget());
        d.setRealValue(rm.getReal());
        d.setName(rm.getName());
        d.setType(rm.getType());
        return d;
    }


    @Override
    public void addActionPlan(List<ActionPlanModel> apm, String name, Date date) {
        DataByDate dbd = dataByDateRepo.findByDateDpm(date);
        if (!dbd.getSafeties().isEmpty()) {
            Optional<Safety> deliveryWithNameAp = dbd.getSafeties().stream().filter(delivery -> delivery.getName().equals(name)).findFirst();

            if (deliveryWithNameAp.isPresent()) {
                Safety delivery = deliveryWithNameAp.get();
                List<ActionPlan> aps = new ArrayList<>();
                for (ActionPlanModel ap : apm) {
                    ActionPlan acp = new ActionPlan();
                    acp.setResp(ap.getResp());
                    acp.setCauses(ap.getCauses());
                    acp.setContermeasures(ap.getContermeasures());
                    acp.setResp(ap.getResp());
                    acp.setDueDate(ap.getDueDate());
                    acp.setStatus(ap.getStatus());
                    acp.setIssueDescription(ap.getIssueDescription());
                    acp.setSafety(delivery);
                    aps.add(acp);
                }
                actionPlanRepo.saveAll(aps);
            }
        }
    }

@Override
    public void addPareto(List<ParetoModel> pms, String name, Date date) {
        DataByDate dbd = dataByDateRepo.findByDateDpm(date);
        if (!dbd.getSafeties().isEmpty()) {
            Optional<Safety> deliveryWithNameAp = dbd.getSafeties().stream().filter(delivery -> delivery.getName().equals(name)).findFirst();
            if (deliveryWithNameAp.isPresent()) {
                Safety delivery = deliveryWithNameAp.get();
                List<Pareto> pmsPrime = new ArrayList<>();
                for (ParetoModel pm : pms) {
                    System.out.println( pm.getPercentage() +" / "+ pm.getMotif());
                    if (pm.getMotif().isEmpty()){
                        continue;
                    }
                    Pareto fp = paretoRepo.findByMotif(pm.getMotif());
                    System.out.println(fp +" / "+ pm.getPercentage() +" / "+ pm.getMotif());
                    if (fp == null) {
                        Pareto p = new Pareto();
                        p.setMotif(pm.getMotif());
                        p.setPercentage(pm.getPercentage());
                        p.setSafety(delivery);
                        pmsPrime.add(p);
                    } else {
                        fp.setMotif(pm.getMotif());
                        fp.setPercentage(pm.getPercentage());
                        fp.setSafety(delivery);
                        pmsPrime.add(fp);
                    }
                }
                paretoRepo.saveAll(pmsPrime);
            }
        }
    }

}
