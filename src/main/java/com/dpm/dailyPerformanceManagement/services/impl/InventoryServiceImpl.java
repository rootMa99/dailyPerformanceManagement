package com.dpm.dailyPerformanceManagement.services.impl;

import com.dpm.dailyPerformanceManagement.domain.*;
import com.dpm.dailyPerformanceManagement.models.ActionPlanModel;
import com.dpm.dailyPerformanceManagement.models.ParetoModel;
import com.dpm.dailyPerformanceManagement.models.RequestModel;
import com.dpm.dailyPerformanceManagement.repositories.*;
import com.dpm.dailyPerformanceManagement.services.InventoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    InventoryRepo inventoryRepo;
    DataByDateRepo dataByDateRepo;
    ActionPlanRepo actionPlanRepo;
    IKpiNamesRepo iKpiNamesRepo;
    ParetoRepo paretoRepo;

    @Override
    public void addInventoryData(RequestModel rm) {
        IKpiNames dKpiNames = iKpiNamesRepo.findByKpiName(rm.getName());
        if (dKpiNames == null) {
            IKpiNames dk = new IKpiNames();
            dk.setAlias(rm.getAlias());
            if (rm.getName() == null) {
                dk.setKpiName(rm.getAlias());
            } else {
                dk.setKpiName(rm.getName());
            }
            dk.setType(rm.getType());
            iKpiNamesRepo.save(dk);
        }
        DataByDate dbd = dataByDateRepo.findByDateDpm(rm.getDate());
        if (dbd == null) {
            Inventory d = extractedDelivery(rm);
            DataByDate dataByDate = new DataByDate();
            dataByDate.setDateDpm(rm.getDate());
            dataByDate = dataByDateRepo.save(dataByDate);
            d.setDbd(dataByDate);
            inventoryRepo.save(d);
        } else {
            if (!dbd.getInventories().isEmpty()) {
                Optional<Inventory> deliveryWithNameAp = dbd.getInventories().stream().filter(delivery -> delivery.getName().equals(rm.getName())).findFirst();
                if (deliveryWithNameAp.isPresent()) {
                    Inventory delivery = deliveryWithNameAp.get();
                    delivery.setRealValue(rm.getReal());
                    delivery.setTargetValue(rm.getTarget());
                    inventoryRepo.save(delivery);
                } else {
                    Inventory d = extractedDelivery(rm);
                    d.setDbd(dbd);
                    inventoryRepo.save(d);
                }
            } else {
                Inventory d = extractedDelivery(rm);
                d.setDbd(dbd);
                inventoryRepo.save(d);
            }
        }


    }

    private Inventory extractedDelivery(RequestModel rm) {
        Inventory d = new Inventory();
        d.setTargetValue(rm.getTarget());
        d.setRealValue(rm.getReal());
        d.setName(rm.getName());
        d.setType(rm.getType());
        return d;
    }


    @Override
    public void addActionPlan(List<ActionPlanModel> apm, String name, Date date) {
        DataByDate dbd = dataByDateRepo.findByDateDpm(date);
        if (!dbd.getInventories().isEmpty()) {
            Optional<Inventory> deliveryWithNameAp = dbd.getInventories().stream().filter(delivery -> delivery.getName().equals(name)).findFirst();
            if (deliveryWithNameAp.isPresent()) {
                Inventory delivery = deliveryWithNameAp.get();
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
                    acp.setInventory(delivery);
                    aps.add(acp);
                }
                actionPlanRepo.saveAll(aps);
            }
        }
    }

    @Override
    public void addPareto(List<ParetoModel> pms, String name, Date date) {
        DataByDate dbd = dataByDateRepo.findByDateDpm(date);
        if (!dbd.getDeliveries().isEmpty()) {
            Optional<Inventory> deliveryWithNameAp = dbd.getInventories().stream().filter(delivery -> delivery.getName().equals(name)).findFirst();
            if (deliveryWithNameAp.isPresent()) {
                Inventory delivery = deliveryWithNameAp.get();
                List<Pareto> pmsPrime = new ArrayList<>();
                for (ParetoModel pm : pms) {
                    if (pm.getMotif().isEmpty()) {
                        continue;
                    }
                    Pareto fp = paretoRepo.findByMotif(pm.getMotif());
                    if (fp == null) {
                        Pareto p = new Pareto();
                        p.setMotif(pm.getMotif());
                        p.setPercentage(pm.getPercentage());
                        p.setInventory(delivery);
                        pmsPrime.add(p);
                    } else {
                        fp.setMotif(pm.getMotif());
                        fp.setPercentage(pm.getPercentage());
                        fp.setInventory(delivery);
                        pmsPrime.add(fp);
                    }
                }
                paretoRepo.saveAll(pmsPrime);
            }
        }
    }
}
