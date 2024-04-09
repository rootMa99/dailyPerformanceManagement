package com.dpm.dailyPerformanceManagement.services.impl;

import com.dpm.dailyPerformanceManagement.domain.*;
import com.dpm.dailyPerformanceManagement.models.ActionPlanModel;
import com.dpm.dailyPerformanceManagement.models.RequestModel;
import com.dpm.dailyPerformanceManagement.repositories.ActionPlanRepo;
import com.dpm.dailyPerformanceManagement.repositories.DataByDateRepo;
import com.dpm.dailyPerformanceManagement.repositories.PKpiNamesRepo;
import com.dpm.dailyPerformanceManagement.repositories.ProductivityRepo;
import com.dpm.dailyPerformanceManagement.services.ProductivityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductivityServiceImpl implements ProductivityService {
    ProductivityRepo productivityRepo;
    DataByDateRepo dataByDateRepo;
    ActionPlanRepo actionPlanRepo;
    PKpiNamesRepo pKpiNamesRepo;
    @Override
    public void addProductivityData(RequestModel rm) {
        PkpiNames dKpiNames=pKpiNamesRepo.findByKpiName(rm.getName());
        if (dKpiNames==null){
            PkpiNames dk=new PkpiNames();
            dk.setKpiName(rm.getName());
            if (rm.getAlias()==null){
                dk.setAlias(rm.getName());
            }else {
                dk.setAlias(rm.getAlias());
            }
            dk.setType(rm.getType());
            pKpiNamesRepo.save(dk);
        }
        DataByDate dbd = dataByDateRepo.findByDateDpm(rm.getDate());
        if (dbd == null) {
            Productivity d = extractedDelivery(rm);
            DataByDate dataByDate = new DataByDate();
            dataByDate.setDateDpm(rm.getDate());
            dataByDate = dataByDateRepo.save(dataByDate);
            d.setDbd(dataByDate);
            productivityRepo.save(d);
        } else {
            if (!dbd.getProductivities().isEmpty()) {
                Optional<Productivity> deliveryWithNameAp =
                        dbd.getProductivities().stream().filter(delivery -> delivery.getName().equals(rm.getName())).findFirst();

                if (deliveryWithNameAp.isPresent()) {
                    Productivity delivery = deliveryWithNameAp.get();
                    delivery.setRealValue(rm.getReal());
                    delivery.setTargetValue(rm.getTarget());
                    productivityRepo.save(delivery);
                } else {
                    Productivity d = extractedDelivery(rm);
                    d.setDbd(dbd);
                    productivityRepo.save(d);
                }
            } else {
                Productivity d = extractedDelivery(rm);
                d.setDbd(dbd);
                productivityRepo.save(d);
            }
        }
    }

    private Productivity extractedDelivery(RequestModel rm) {
        Productivity d = new Productivity();
        d.setTargetValue(rm.getTarget());
        d.setRealValue(rm.getReal());
        d.setName(rm.getName());
        d.setType(rm.getType());
        return d;
    }

    @Override
    public void addActionPlan(List<ActionPlanModel> apm, String name, Date date) {
        DataByDate dbd = dataByDateRepo.findByDateDpm(date);
        if (!dbd.getProductivities().isEmpty()) {
            Optional<Productivity> deliveryWithNameAp =
                    dbd.getProductivities().stream().filter(delivery -> delivery.getName().equals(name)).findFirst();

            if (deliveryWithNameAp.isPresent()) {
                Productivity delivery = deliveryWithNameAp.get();
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
                    acp.setProductivity(delivery);
                    aps.add(acp);
                }
                actionPlanRepo.saveAll(aps);
            }
        }
    }

}
