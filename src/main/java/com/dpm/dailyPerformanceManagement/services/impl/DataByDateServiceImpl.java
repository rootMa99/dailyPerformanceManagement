package com.dpm.dailyPerformanceManagement.services.impl;


import com.dpm.dailyPerformanceManagement.domain.ActionPlan;
import com.dpm.dailyPerformanceManagement.domain.Delivery;
import com.dpm.dailyPerformanceManagement.domain.Inventory;
import com.dpm.dailyPerformanceManagement.domain.Kaizen;
import com.dpm.dailyPerformanceManagement.models.ActionPlanModel;
import com.dpm.dailyPerformanceManagement.models.DataRest;
import com.dpm.dailyPerformanceManagement.repositories.*;
import com.dpm.dailyPerformanceManagement.services.DataByDateService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
@AllArgsConstructor
public class DataByDateServiceImpl implements DataByDateService {
    DeliveryRepo deliveryRepo;
    InventoryRepo inventoryRepo;
    KaizenRepo kaizenRepo;
    ProductivityRepo productivityRepo;
    QualityRepo qualityRepo;
    SafetyRepo safetyRepo;
    SkillsRepo skillsRepo;

    @Override
    public List<DataRest> getDelivriesDateBetween(Date start, Date end) {
        List<Delivery> ds = deliveryRepo.findAllByDbdDateDpmBetween(start, end);
        List<DataRest> drs = new ArrayList<>();
        for (Delivery d : ds) {
            DataRest dr = new DataRest();
            dr.setDDate(d.getDbd().getDateDpm());
            dr.setReal(d.getRealValue());
            dr.setTarget(d.getTargetValue());
            dr.setName(d.getName());
            List<ActionPlanModel> apms = new ArrayList<>();
            for (ActionPlan ap : d.getActionPlans()) {
                ActionPlanModel apm = new ActionPlanModel();
                apm.setId(ap.getId());
                apm.setIssueDescription(ap.getIssueDescription());
                apm.setCauses(ap.getCauses());
                apm.setContermeasures(ap.getContermeasures());
                apm.setResp(ap.getResp());
                apm.setDueDate(ap.getDueDate());
                apm.setStatus(ap.getStatus());
                apms.add(apm);
            }
            dr.setApm(apms);
            drs.add(dr);
        }
        return drs;
    }

    @Override
    public List<DataRest> getInventoryDateBetween(Date start, Date end) {
        List<Inventory> ds = inventoryRepo.findAllByDbdDateDpmBetween(start, end);
        List<DataRest> drs = new ArrayList<>();
        for (Inventory d : ds) {
            DataRest dr = new DataRest();
            dr.setDDate(d.getDbd().getDateDpm());
            dr.setReal(d.getRealValue());
            dr.setTarget(d.getTargetValue());
            dr.setName(d.getName());
            List<ActionPlanModel> apms = new ArrayList<>();
            for (ActionPlan ap : d.getActionPlans()) {
                ActionPlanModel apm = new ActionPlanModel();
                apm.setId(ap.getId());
                apm.setIssueDescription(ap.getIssueDescription());
                apm.setCauses(ap.getCauses());
                apm.setContermeasures(ap.getContermeasures());
                apm.setResp(ap.getResp());
                apm.setDueDate(ap.getDueDate());
                apm.setStatus(ap.getStatus());
                apms.add(apm);
            }
            dr.setApm(apms);
            drs.add(dr);
        }
        return drs;
    }

    @Override
    public List<DataRest> getKaizenDateBetween(Date start, Date end) {
        List<Kaizen> ds = kaizenRepo.findAllByDbdDateDpmBetween(start, end);
        List<DataRest> drs = new ArrayList<>();
        for (Kaizen d : ds) {
            DataRest dr = new DataRest();
            dr.setDDate(d.getDbd().getDateDpm());
            dr.setReal(d.getRealValue());
            dr.setTarget(d.getTargetValue());
            dr.setName(d.getName());
            List<ActionPlanModel> apms = new ArrayList<>();
            for (ActionPlan ap : d.getActionPlans()) {
                ActionPlanModel apm = new ActionPlanModel();
                apm.setId(ap.getId());
                apm.setIssueDescription(ap.getIssueDescription());
                apm.setCauses(ap.getCauses());
                apm.setContermeasures(ap.getContermeasures());
                apm.setResp(ap.getResp());
                apm.setDueDate(ap.getDueDate());
                apm.setStatus(ap.getStatus());
                apms.add(apm);
            }
            dr.setApm(apms);
            drs.add(dr);
        }
        return drs;
    }



}
