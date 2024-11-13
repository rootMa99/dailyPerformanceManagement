package com.dpm.dailyPerformanceManagement.services.impl;

import com.dpm.dailyPerformanceManagement.domain.*;
import com.dpm.dailyPerformanceManagement.models.ActionPlanModel;
import com.dpm.dailyPerformanceManagement.models.ParetoModel;
import com.dpm.dailyPerformanceManagement.models.RequestModel;
import com.dpm.dailyPerformanceManagement.repositories.*;
import com.dpm.dailyPerformanceManagement.services.ProductivityService;
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
public class ProductivityServiceImpl implements ProductivityService {
    ProductivityRepo productivityRepo;
    DataByDateRepo dataByDateRepo;
    ActionPlanRepo actionPlanRepo;
    PKpiNamesRepo pKpiNamesRepo;
    ParetoRepo paretoRepo;

    @Override
    public void addProductivityData(RequestModel rm) {
        PkpiNames dKpiNames = pKpiNamesRepo.findByKpiName(rm.getName());
        if (dKpiNames == null) {
            PkpiNames dk = new PkpiNames();
            dk.setAlias(rm.getAlias());
            if (rm.getName() == null) {
                dk.setKpiName(rm.getAlias());
            } else {
                dk.setKpiName(rm.getName());
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
                Optional<Productivity> deliveryWithNameAp = dbd.getProductivities().stream().filter(delivery -> delivery.getName().equals(rm.getName())).findFirst();
                System.out.println(deliveryWithNameAp);
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
    public ActionPlan addActionPlan(ActionPlanModel ap, String name, Date date) {
        DataByDate dbd = dataByDateRepo.findByDateDpm(date);
        ActionPlan apr = new ActionPlan();
        if (!dbd.getProductivities().isEmpty()) {
            Optional<Productivity> deliveryWithNameAp = dbd.getProductivities().stream().filter(delivery -> delivery.getName().equals(name)).findFirst();

            if (deliveryWithNameAp.isPresent()) {
                Productivity delivery = deliveryWithNameAp.get();
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
                acp.setProductivity(delivery);
                acp.setOpenDate(ap.getOpenDate());
                apr = actionPlanRepo.save(acp);
            }
        }
        return apr;
    }

    @Override
    public void addPareto(List<ParetoModel> pms, String name, Date date) {
        DataByDate dbd = dataByDateRepo.findByDateDpm(date);
        if (!dbd.getProductivities().isEmpty()) {
            Optional<Productivity> deliveryWithNameAp = dbd.getProductivities().stream().filter(delivery -> delivery.getName().equals(name)).findFirst();
            if (deliveryWithNameAp.isPresent()) {
                Productivity delivery = deliveryWithNameAp.get();
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
                        p.setProductivity(delivery);
                        pmsPrime.add(p);
                    } else {
                        fp.setMotif(pm.getMotif());
                        fp.setPercentage(pm.getPercentage());
                        fp.setProductivity(delivery);
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
                    PkpiNames pk = pKpiNamesRepo.findByAlias(rm.getAlias());
                    if (pk != null) {
                        rm.setName(pk.getKpiName());
                        addProductivityData(rm);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
