package com.dpm.dailyPerformanceManagement.services.impl;

import com.dpm.dailyPerformanceManagement.domain.*;
import com.dpm.dailyPerformanceManagement.models.ActionPlanModel;
import com.dpm.dailyPerformanceManagement.models.ParetoModel;
import com.dpm.dailyPerformanceManagement.models.RequestModel;
import com.dpm.dailyPerformanceManagement.repositories.*;
import com.dpm.dailyPerformanceManagement.services.KaizenService;
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
public class kaizenServiceImpl implements KaizenService {

    KaizenRepo kaizenRepo;
    DataByDateRepo dataByDateRepo;
    ActionPlanRepo actionPlanRepo;
    KKpiNamesRepo kKpiNamesRepo;
    ParetoRepo paretoRepo;

    @Override
    public void addKaizenData(RequestModel rm) {
        KKpiNames dKpiNames = kKpiNamesRepo.findByKpiName(rm.getName());
        if (dKpiNames == null) {
            KKpiNames dk = new KKpiNames();
            dk.setAlias(rm.getAlias());
            if (rm.getName() == null) {
                dk.setKpiName(rm.getAlias());
            } else {
                dk.setKpiName(rm.getName());
            }
            dk.setType(rm.getType());
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
        d.setType(rm.getType());
        return d;
    }

    @Override
    public ActionPlan addActionPlan(ActionPlanModel ap, String name, Date date) {
        DataByDate dbd = dataByDateRepo.findByDateDpm(date);
        ActionPlan apr = new ActionPlan();
        if (!dbd.getKaizens().isEmpty()) {
            Optional<Kaizen> deliveryWithNameAp = dbd.getKaizens().stream().filter(delivery -> delivery.getName().equals(name)).findFirst();

            if (deliveryWithNameAp.isPresent()) {
                Kaizen delivery = deliveryWithNameAp.get();
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
                acp.setKaizen(delivery);
                acp.setOpenDate(ap.getOpenDate());
                apr = actionPlanRepo.save(acp);
            }
        }
        return apr;
    }

    @Override
    public void addPareto(List<ParetoModel> pms, String name, Date date) {
        DataByDate dbd = dataByDateRepo.findByDateDpm(date);
        if (!dbd.getKaizens().isEmpty()) {
            Optional<Kaizen> deliveryWithNameAp = dbd.getKaizens().stream().filter(delivery -> delivery.getName().equals(name)).findFirst();
            if (deliveryWithNameAp.isPresent()) {
                Kaizen delivery = deliveryWithNameAp.get();
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
                        p.setKaizen(delivery);
                        pmsPrime.add(p);
                    } else {
                        fp.setMotif(pm.getMotif());
                        fp.setPercentage(pm.getPercentage());
                        fp.setKaizen(delivery);
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
                    KKpiNames pk = kKpiNamesRepo.findByAlias(rm.getAlias());
                    if (pk != null) {
                        rm.setName(pk.getKpiName());
                        addKaizenData(rm);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
