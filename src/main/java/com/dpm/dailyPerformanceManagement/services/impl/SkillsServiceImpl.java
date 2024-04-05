package com.dpm.dailyPerformanceManagement.services.impl;

import com.dpm.dailyPerformanceManagement.domain.*;
import com.dpm.dailyPerformanceManagement.models.ActionPlanModel;
import com.dpm.dailyPerformanceManagement.models.RequestModel;
import com.dpm.dailyPerformanceManagement.repositories.ActionPlanRepo;
import com.dpm.dailyPerformanceManagement.repositories.DataByDateRepo;
import com.dpm.dailyPerformanceManagement.repositories.SkKpiNamesRepo;
import com.dpm.dailyPerformanceManagement.repositories.SkillsRepo;
import com.dpm.dailyPerformanceManagement.services.SkillsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SkillsServiceImpl implements SkillsService {
    DataByDateRepo dataByDateRepo;
    ActionPlanRepo actionPlanRepo;
    SkillsRepo skillsRepo;
    SkKpiNamesRepo skKpiNamesRepo;
    @Override
    public void addSkillsData(RequestModel rm) {
        SkKpiNames dKpiNames=skKpiNamesRepo.findByKpiName(rm.getName());
        if (dKpiNames==null){
            SkKpiNames dk=new SkKpiNames();
            dk.setKpiName(rm.getName());
            skKpiNamesRepo.save(dk);
        }
        DataByDate dbd = dataByDateRepo.findByDateDpm(rm.getDate());
        if (dbd == null) {
            Skills d = extractedDelivery(rm);
            DataByDate dataByDate = new DataByDate();
            dataByDate.setDateDpm(rm.getDate());
            dataByDate = dataByDateRepo.save(dataByDate);
            d.setDbd(dataByDate);
            skillsRepo.save(d);
        } else {
            if (!dbd.getSkillsList().isEmpty()) {
                Optional<Skills> deliveryWithNameAp =
                        dbd.getSkillsList().stream().filter(delivery -> delivery.getName().equals(rm.getName())).findFirst();

                if (deliveryWithNameAp.isPresent()) {
                    Skills delivery = deliveryWithNameAp.get();
                    delivery.setRealValue(rm.getReal());
                    delivery.setTargetValue(rm.getTarget());
                    skillsRepo.save(delivery);
                } else {
                    Skills d = extractedDelivery(rm);
                    d.setDbd(dbd);
                    skillsRepo.save(d);
                }
            } else {
                Skills d = extractedDelivery(rm);
                d.setDbd(dbd);
                skillsRepo.save(d);
            }
        }
    }

    private Skills extractedDelivery(RequestModel rm) {
        Skills d = new Skills();
        d.setTargetValue(rm.getTarget());
        d.setRealValue(rm.getReal());
        d.setName(rm.getName());
        return d;
    }


    @Override
    public void addActionPlan(List<ActionPlanModel> apm, String name, Date date) {
        DataByDate dbd = dataByDateRepo.findByDateDpm(date);
        if (!dbd.getSkillsList().isEmpty()) {
            Optional<Skills> deliveryWithNameAp =
                    dbd.getSkillsList().stream().filter(delivery -> delivery.getName().equals(name)).findFirst();

            if (deliveryWithNameAp.isPresent()) {
                Skills delivery = deliveryWithNameAp.get();
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
                    acp.setSkills(delivery);
                    aps.add(acp);
                }
                actionPlanRepo.saveAll(aps);
            }
        }
    }

}
