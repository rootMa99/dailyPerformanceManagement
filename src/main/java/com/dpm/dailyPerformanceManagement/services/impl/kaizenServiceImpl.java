package com.dpm.dailyPerformanceManagement.services.impl;

import com.dpm.dailyPerformanceManagement.domain.*;
import com.dpm.dailyPerformanceManagement.models.ActionPlanModel;
import com.dpm.dailyPerformanceManagement.models.RequestModel;
import com.dpm.dailyPerformanceManagement.repositories.ActionPlanRepo;
import com.dpm.dailyPerformanceManagement.repositories.DataByDateRepo;
import com.dpm.dailyPerformanceManagement.repositories.KKpiNamesRepo;
import com.dpm.dailyPerformanceManagement.repositories.KaizenRepo;
import com.dpm.dailyPerformanceManagement.services.KaizenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class kaizenServiceImpl implements KaizenService {

    KaizenRepo kaizenRepo;
    DataByDateRepo dataByDateRepo;
    ActionPlanRepo actionPlanRepo;
    KKpiNamesRepo kKpiNamesRepo;
    @Override
    public void addKaizenData(RequestModel rm) {
        KKpiNames dKpiNames=kKpiNamesRepo.findByKpiName(rm.getName());
        if (dKpiNames==null){
            KKpiNames dk=new KKpiNames();
            dk.setKpiName(rm.getName());
            kKpiNamesRepo.save(dk);
        }
        DataByDate dbd = dataByDateRepo.findByDateDpm(rm.getDate());
        if (dbd == null) {
            Kaizen d = extractedDelivery(rm);
            DataByDate dataByDate = new DataByDate();
            dataByDate.setDateDpm(rm.getDate());
            dataByDate = dataByDateRepo.save(dataByDate);
            d.setDbd(dataByDate);
            kaizenRepo.save(d);
        } else {
            if (!dbd.getKaizens().isEmpty()) {
                Optional<Kaizen> deliveryWithNameAp = dbd.getKaizens().stream().filter(delivery -> delivery.getName().equals(rm.getName())).findFirst();

                if (deliveryWithNameAp.isPresent()) {
                    Kaizen delivery = deliveryWithNameAp.get();
                    delivery.setRealValue(rm.getReal());
                    delivery.setTargetValue(rm.getTarget());
                    kaizenRepo.save(delivery);
                } else {
                    Kaizen d = extractedDelivery(rm);
                    d.setDbd(dbd);
                    kaizenRepo.save(d);
                }
            } else {
                Kaizen d = extractedDelivery(rm);
                d.setDbd(dbd);
                kaizenRepo.save(d);
            }
        }
    }

    private Kaizen extractedDelivery(RequestModel rm) {
        Kaizen d = new Kaizen();
        d.setTargetValue(rm.getTarget());
        d.setRealValue(rm.getReal());
        d.setName(rm.getName());
        return d;
    }

    @Override
    public void addActionPlan(List<ActionPlanModel> apm, String name, Date date) {
        DataByDate dbd = dataByDateRepo.findByDateDpm(date);
        if (!dbd.getKaizens().isEmpty()) {
            Optional<Kaizen> deliveryWithNameAp = dbd.getKaizens().stream().filter(delivery -> delivery.getName().equals(name)).findFirst();

            if (deliveryWithNameAp.isPresent()) {
                Kaizen delivery = deliveryWithNameAp.get();
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
                    acp.setKaizen(delivery);
                    aps.add(acp);
                }
                actionPlanRepo.saveAll(aps);
            }
        }
    }

}
