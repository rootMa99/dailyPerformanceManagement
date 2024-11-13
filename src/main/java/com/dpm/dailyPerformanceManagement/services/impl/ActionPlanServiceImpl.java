package com.dpm.dailyPerformanceManagement.services.impl;

import com.dpm.dailyPerformanceManagement.domain.ActionPlan;
import com.dpm.dailyPerformanceManagement.models.ActionPlanModel;
import com.dpm.dailyPerformanceManagement.repositories.ActionPlanRepo;
import com.dpm.dailyPerformanceManagement.services.ActionPlanService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ActionPlanServiceImpl implements ActionPlanService {

    ActionPlanRepo apr;

    @Override
    public List<ActionPlanModel> apm(Date startDate, Date endDate) {
        List<ActionPlan> aprs=apr.findAllByOpenDateBetween(startDate, endDate);
        List<ActionPlanModel> apms=new ArrayList<>();
        for (ActionPlan ap:aprs){
            ActionPlanModel acp = getActionPlanModel(ap);
            apms.add(acp);
        }
        return apms;
    }
    @Override
    public ActionPlanModel updateActionPlan(ActionPlanModel ap, Long id) {
        Optional<ActionPlan> optionalAcp = apr.findById(id);
        if (optionalAcp.isPresent()) {
            ActionPlan acp = optionalAcp.get();
            acp.setCb(ap.getCb());
            acp.setCai(ap.getCai());
            acp.setRci(ap.getRci());
            acp.setEpd(ap.getEpd());
            acp.setLpa(ap.getLpa());
            acp.setCav(ap.getCav());
            acp.setPcu(ap.getPcu());
            acp.setSwoi(ap.getSwoi());
            acp.setLl(ap.getLl());
            acp = apr.save(acp);
            ActionPlanModel apm = getActionPlanModel(acp);
            return apm;
        } else {
            throw new RuntimeException("ActionPlan with id " + id + " not found.");
        }
    }

    private static ActionPlanModel getActionPlanModel(ActionPlan acp) {
        ActionPlanModel apm=new ActionPlanModel();
        apm.setId(acp.getId());
        apm.setResp(acp.getResp());
        apm.setCauses(acp.getCauses());
        apm.setContermeasures(acp.getContermeasures());
        apm.setResp(acp.getResp());
        apm.setDueDate(acp.getDueDate());
        apm.setOpenDate(acp.getOpenDate());
        apm.setStatus(acp.getStatus());
        apm.setIssueDescription(acp.getIssueDescription());
        apm.setCb(acp.getCb());
        apm.setCai(acp.getCai());
        apm.setRci(acp.getRci());
        apm.setEpd(acp.getEpd());
        apm.setLpa(acp.getLpa());
        apm.setCav(acp.getCav());
        apm.setPcu(acp.getPcu());
        apm.setSwoi(acp.getSwoi());
        apm.setLl(acp.getLl());
        return apm;
    }
}
