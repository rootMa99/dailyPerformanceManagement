package com.dpm.dailyPerformanceManagement.services.impl;

import com.dpm.dailyPerformanceManagement.domain.*;
import com.dpm.dailyPerformanceManagement.models.ActionPlanModel;
import com.dpm.dailyPerformanceManagement.models.ParetoModel;
import com.dpm.dailyPerformanceManagement.models.RequestModel;
import com.dpm.dailyPerformanceManagement.repositories.*;
import com.dpm.dailyPerformanceManagement.services.SkillsService;
import com.dpm.dailyPerformanceManagement.services.UploadDataViaExcel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    ParetoRepo paretoRepo;

    @Override
    public void addSkillsData(RequestModel rm) {
        SkKpiNames dKpiNames = skKpiNamesRepo.findByKpiName(rm.getName());
        if (dKpiNames == null) {
            SkKpiNames dk = new SkKpiNames();
            dk.setAlias(rm.getAlias());
            if (rm.getName() == null) {
                dk.setKpiName(rm.getAlias());
            } else {
                dk.setKpiName(rm.getName());
            }
            dk.setType(rm.getType());
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
                Optional<Skills> deliveryWithNameAp = dbd.getSkillsList().stream().filter(delivery -> delivery.getName().equals(rm.getName())).findFirst();

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
        if (rm.getName() == null) {
            d.setName(rm.getAlias());

        } else {
            d.setName(rm.getName());
        }
        d.setType(rm.getType());
        return d;
    }


    @Override
    public ActionPlan addActionPlan(ActionPlanModel ap, String name, Date date) {
        DataByDate dbd = dataByDateRepo.findByDateDpm(date);
        ActionPlan apr = new ActionPlan();
        if (!dbd.getSkillsList().isEmpty()) {
            Optional<Skills> deliveryWithNameAp = dbd.getSkillsList().stream().filter(delivery -> delivery.getName().equals(name)).findFirst();

            if (deliveryWithNameAp.isPresent()) {
                Skills delivery = deliveryWithNameAp.get();
                ActionPlan acp = new ActionPlan();
                if (ap.getId() != null) {
                    acp.setId(ap.getId());
                }
                acp.setResp(ap.getResp());
                acp.setCauses(ap.getCauses());
                acp.setContermeasures(ap.getContermeasures());
                acp.setResp(ap.getResp());
                acp.setDueDate(ap.getDueDate());
                acp.setStatus(ap.getStatus());
                acp.setIssueDescription(ap.getIssueDescription());
                acp.setSkills(delivery);
                apr = actionPlanRepo.save(acp);
            }
        }
        return apr;
    }

    @Override
    public void addPareto(List<ParetoModel> pms, String name, Date date) {
        DataByDate dbd = dataByDateRepo.findByDateDpm(date);
        if (!dbd.getSkillsList().isEmpty()) {
            Optional<Skills> deliveryWithNameAp = dbd.getSkillsList().stream().filter(delivery -> delivery.getName().equals(name)).findFirst();
            if (deliveryWithNameAp.isPresent()) {
                Skills delivery = deliveryWithNameAp.get();
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
                        p.setSkills(delivery);
                        pmsPrime.add(p);
                    } else {
                        fp.setMotif(pm.getMotif());
                        fp.setPercentage(pm.getPercentage());
                        fp.setSkills(delivery);
                        pmsPrime.add(fp);
                    }
                }
                paretoRepo.saveAll(pmsPrime);
            }
        }
    }

    @Override
    public void addDataViaExcel(MultipartFile file) {
        System.out.println("action started");
        if (UploadDataViaExcel.isValidFormat(file)) {
            System.out.println("action tested");
            try {
                List<RequestModel> requestModels = UploadDataViaExcel.getDataFromExcel(file.getInputStream());
                for (RequestModel rm : requestModels) {
                    System.out.println(rm);
                    SkKpiNames pk = skKpiNamesRepo.findByAlias(rm.getAlias());
                    if (pk != null) {
                        rm.setName(pk.getKpiName());
                        addSkillsData(rm);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
