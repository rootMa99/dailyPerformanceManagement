package com.dpm.dailyPerformanceManagement.services.impl;

import com.dpm.dailyPerformanceManagement.domain.*;
import com.dpm.dailyPerformanceManagement.models.ActionPlanModel;
import com.dpm.dailyPerformanceManagement.models.RequestModel;
import com.dpm.dailyPerformanceManagement.repositories.ActionPlanRepo;
import com.dpm.dailyPerformanceManagement.repositories.DataByDateRepo;
import com.dpm.dailyPerformanceManagement.repositories.QKpiNamesRepo;
import com.dpm.dailyPerformanceManagement.repositories.QualityRepo;
import com.dpm.dailyPerformanceManagement.services.QualityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class QualityServiceImpl implements QualityService {

    DataByDateRepo dataByDateRepo;
    QualityRepo qualityRepo;
    ActionPlanRepo actionPlanRepo;
    QKpiNamesRepo qKpiNamesRepo;
    @Override
    public void addQualityData(RequestModel rm) {
        QKpiNames dKpiNames=qKpiNamesRepo.findByKpiName(rm.getName());
        if (dKpiNames==null){
            QKpiNames dk=new QKpiNames();
            dk.setKpiName(rm.getName());
            qKpiNamesRepo.save(dk);
        }
        DataByDate dbd = dataByDateRepo.findByDateDpm(rm.getDate());
        if (dbd == null) {
            Quality d = extractedDelivery(rm);
            DataByDate dataByDate = new DataByDate();
            dataByDate.setDateDpm(rm.getDate());
            dataByDate = dataByDateRepo.save(dataByDate);
            d.setDbd(dataByDate);
            qualityRepo.save(d);
        } else {
            if (!dbd.getQualities().isEmpty()) {
                Optional<Quality> deliveryWithNameAp =
                        dbd.getQualities().stream().filter(delivery -> delivery.getName().equals(rm.getName())).findFirst();

                if (deliveryWithNameAp.isPresent()) {
                    Quality delivery = deliveryWithNameAp.get();
                    delivery.setRealValue(rm.getReal());
                    delivery.setTargetValue(rm.getTarget());
                    qualityRepo.save(delivery);
                } else {
                    Quality d = extractedDelivery(rm);
                    d.setDbd(dbd);
                    qualityRepo.save(d);
                }
            } else {
                Quality d = extractedDelivery(rm);
                d.setDbd(dbd);
                qualityRepo.save(d);
            }
        }
    }

    private Quality extractedDelivery(RequestModel rm) {
        Quality d = new Quality();
        d.setTargetValue(rm.getTarget());
        d.setRealValue(rm.getReal());
        d.setName(rm.getName());
        return d;
    }


    @Override
    public void addActionPlan(List<ActionPlanModel> apm, String name, Date date) {
        DataByDate dbd = dataByDateRepo.findByDateDpm(date);
        if (!dbd.getQualities().isEmpty()) {
            Optional<Quality> deliveryWithNameAp =
                    dbd.getQualities().stream().filter(delivery -> delivery.getName().equals(name)).findFirst();

            if (deliveryWithNameAp.isPresent()) {
                Quality delivery = deliveryWithNameAp.get();
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
                    acp.setQuality(delivery);
                    aps.add(acp);
                }
                actionPlanRepo.saveAll(aps);
            }
        }
    }


}
