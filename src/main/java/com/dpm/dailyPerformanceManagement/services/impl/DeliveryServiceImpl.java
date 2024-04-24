package com.dpm.dailyPerformanceManagement.services.impl;

import com.dpm.dailyPerformanceManagement.domain.*;
import com.dpm.dailyPerformanceManagement.models.ActionPlanModel;
import com.dpm.dailyPerformanceManagement.models.ParetoModel;
import com.dpm.dailyPerformanceManagement.models.RequestModel;
import com.dpm.dailyPerformanceManagement.repositories.*;
import com.dpm.dailyPerformanceManagement.services.DeliveryService;
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
public class DeliveryServiceImpl implements DeliveryService {
    DeliveryRepo deliveryRepo;
    DataByDateRepo dataByDateRepo;
    ActionPlanRepo actionPlanRepo;
    DKpiNamesRepo dKpiNamesRepo;
    ParetoRepo paretoRepo;


    @Override
    public void addDeliveryData(RequestModel rm) {
        DKpiNames dKpiNames = dKpiNamesRepo.findByKpiName(rm.getName());
        if (dKpiNames == null) {
            DKpiNames dk = new DKpiNames();
            dk.setAlias(rm.getAlias());
            if (rm.getName() == null) {
                dk.setKpiName(rm.getAlias());
            } else {
                dk.setKpiName(rm.getName());
            }
            dk.setType(rm.getType());
            dKpiNamesRepo.save(dk);
        }
        DataByDate dbd = dataByDateRepo.findByDateDpm(rm.getDate());
        if (dbd == null) {
            Delivery d = extractedDelivery(rm);
            DataByDate dataByDate = new DataByDate();
            dataByDate.setDateDpm(rm.getDate());
            dataByDate = dataByDateRepo.save(dataByDate);
            d.setDbd(dataByDate);
            deliveryRepo.save(d);
        } else {
            if (!dbd.getDeliveries().isEmpty()) {
                Optional<Delivery> deliveryWithNameAp = dbd.getDeliveries().stream().filter(delivery -> delivery.getName().equals(rm.getName())).findFirst();

                if (deliveryWithNameAp.isPresent()) {
                    Delivery delivery = deliveryWithNameAp.get();
                    delivery.setRealValue(rm.getReal());
                    delivery.setTargetValue(rm.getTarget());
                    deliveryRepo.save(delivery);
                } else {
                    Delivery d = extractedDelivery(rm);
                    d.setDbd(dbd);
                    deliveryRepo.save(d);
                }
            } else {
                Delivery d = extractedDelivery(rm);
                d.setDbd(dbd);
                deliveryRepo.save(d);
            }
        }
    }

    private Delivery extractedDelivery(RequestModel rm) {
        Delivery d = new Delivery();
        d.setTargetValue(rm.getTarget());
        d.setRealValue(rm.getReal());
        d.setName(rm.getName());
        d.setType(rm.getType());
        return d;
    }

    @Override
    public ActionPlan addActionPlan(ActionPlanModel ap, String name, Date date) {
        DataByDate dbd = dataByDateRepo.findByDateDpm(date);
        ActionPlan apr=new ActionPlan();
        if (!dbd.getDeliveries().isEmpty()) {
            Optional<Delivery> deliveryWithNameAp = dbd.getDeliveries().stream().filter(delivery -> delivery.getName().equals(name)).findFirst();
            if (deliveryWithNameAp.isPresent()) {
                Delivery delivery = deliveryWithNameAp.get();

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
                    acp.setDelivery(delivery);
                apr= actionPlanRepo.save(acp);
            }
        }
        return apr;
    }

    @Override
    public void addPareto(List<ParetoModel> pms, String name, Date date) {
        DataByDate dbd = dataByDateRepo.findByDateDpm(date);
        if (!dbd.getDeliveries().isEmpty()) {
            Optional<Delivery> deliveryWithNameAp = dbd.getDeliveries().stream().filter(delivery -> delivery.getName().equals(name)).findFirst();
            if (deliveryWithNameAp.isPresent()) {
                Delivery delivery = deliveryWithNameAp.get();
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
                        p.setDelivery(delivery);
                        pmsPrime.add(p);
                    } else {
                        fp.setMotif(pm.getMotif());
                        fp.setPercentage(pm.getPercentage());
                        fp.setDelivery(delivery);
                        pmsPrime.add(fp);
                    }
                }
                paretoRepo.saveAll(pmsPrime);
            }
        }
    }

    @Override
    public void addDataViaExcel(MultipartFile file){
        System.out.println("action started");
        if (UploadDataViaExcel.isValidFormat(file)) {
            System.out.println("action tested");
            try{
                List<RequestModel> requestModels=UploadDataViaExcel.getDataFromExcel(file.getInputStream());
                for (RequestModel rm: requestModels){
                    System.out.println(rm);
                    DKpiNames pk=dKpiNamesRepo.findByAlias(rm.getAlias());
                    if (pk!=null){
                        rm.setName(pk.getKpiName());
                        addDeliveryData(rm);
                    }
                }
            }catch (IOException e){
                throw new RuntimeException(e);
            }
        }
    }
}
