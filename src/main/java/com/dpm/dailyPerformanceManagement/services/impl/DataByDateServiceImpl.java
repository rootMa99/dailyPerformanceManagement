package com.dpm.dailyPerformanceManagement.services.impl;


import com.dpm.dailyPerformanceManagement.domain.*;
import com.dpm.dailyPerformanceManagement.models.ActionPlanModel;
import com.dpm.dailyPerformanceManagement.models.DataRest;
import com.dpm.dailyPerformanceManagement.models.KpiRest;
import com.dpm.dailyPerformanceManagement.models.Utils;
import com.dpm.dailyPerformanceManagement.repositories.*;
import com.dpm.dailyPerformanceManagement.services.DataByDateService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
@AllArgsConstructor
public class DataByDateServiceImpl implements DataByDateService {
    Utils utils;
    FilesRepo filesRepo;
    DeliveryRepo deliveryRepo;
    InventoryRepo inventoryRepo;
    KaizenRepo kaizenRepo;
    ProductivityRepo productivityRepo;
    QualityRepo qualityRepo;
    SafetyRepo safetyRepo;
    SkillsRepo skillsRepo;
    KpiOwnerRepo kpiOwnerRepo;

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
            dr.setType(d.getType());
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
            dr.setType(d.getType());
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
            dr.setType(d.getType());
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
    public List<DataRest> getProductivityDateBetween(Date start, Date end) {
        List<Productivity> ds = productivityRepo.findAllByDbdDateDpmBetween(start, end);
        List<DataRest> drs = new ArrayList<>();
        for (Productivity d : ds) {
            DataRest dr = new DataRest();
            dr.setDDate(d.getDbd().getDateDpm());
            dr.setReal(d.getRealValue());
            dr.setTarget(d.getTargetValue());
            dr.setName(d.getName());
            dr.setType(d.getType());
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
    public List<DataRest> getQualityDateBetween(Date start, Date end) {
        List<Quality> ds = qualityRepo.findAllByDbdDateDpmBetween(start, end);
        List<DataRest> drs = new ArrayList<>();
        for (Quality d : ds) {
            DataRest dr = new DataRest();
            dr.setDDate(d.getDbd().getDateDpm());
            dr.setReal(d.getRealValue());
            dr.setTarget(d.getTargetValue());
            dr.setName(d.getName());
            dr.setType(d.getType());
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
    public List<DataRest> getSafetyDateBetween(Date start, Date end) {
        List<Safety> ds = safetyRepo.findAllByDbdDateDpmBetween(start, end);
        List<DataRest> drs = new ArrayList<>();
        for (Safety d : ds) {
            DataRest dr = new DataRest();
            dr.setDDate(d.getDbd().getDateDpm());
            dr.setReal(d.getRealValue());
            dr.setTarget(d.getTargetValue());
            dr.setName(d.getName());
            dr.setType(d.getType());
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
    public List<DataRest> getSkillsDateBetween(Date start, Date end) {
        List<Skills> ds = skillsRepo.findAllByDbdDateDpmBetween(start, end);
        List<DataRest> drs = new ArrayList<>();
        for (Skills d : ds) {
            DataRest dr = new DataRest();
            dr.setDDate(d.getDbd().getDateDpm());
            dr.setReal(d.getRealValue());
            dr.setTarget(d.getTargetValue());
            dr.setName(d.getName());
            dr.setType(d.getType());
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
    public Files getFileByFileId(String fileId) throws FileNotFoundException {
        Files fe = filesRepo.findByFileId(fileId);
        if (fe == null) {
            throw new FileNotFoundException("File not found with id " + fileId);
        }
        return fe;
    }

    @Override
    public Files uploadFile(MultipartFile file) throws IOException {

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {

            if (fileName.contains("..")) {
                throw new IOException("File Name Contain A Invalid Path Sequence");
            }

            String fileId = utils.generateProjectId(22);
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/dpm").path("/downloadFile/").path(fileId).toUriString();

            return new Files(fileId, fileName, file.getContentType(), file.getBytes(), fileDownloadUri);
        } catch (Exception e) {
            throw new IOException("Could not store file " + fileName + ". Please try again!");
        }
    }

    @Override
    public void addKpiOwner(String kpiOwn, String name, String coName, MultipartFile file) throws IOException {
        Files filesf = filesRepo.findByFileId(kpiOwn);
        if (filesf == null) {
            Files fileEntity = uploadFile(file);
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/dpm").path("/downloadFile/").path(kpiOwn).toUriString();
            fileEntity.setFileDownloadUri(fileDownloadUri);
            fileEntity.setFileName(name);
            fileEntity.setFileId(kpiOwn);
            KpiOwner kpiOwner = new KpiOwner();
            kpiOwner.setKpiOwn(kpiOwn);
            kpiOwner.setName(name);
            kpiOwner.setCoName(coName);
            kpiOwner = kpiOwnerRepo.save(kpiOwner);
            fileEntity.setKpiOwner(kpiOwner);
            filesRepo.save(fileEntity);
        } else {
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/dpm").path("/downloadFile/").path(kpiOwn).toUriString();
            filesf.setFileDownloadUri(fileDownloadUri);
            filesf.setFileName(name);
            filesf.setFileId(kpiOwn);
            KpiOwner kpiOwner = new KpiOwner();
            kpiOwner.setId(filesf.getKpiOwner().getId());
            kpiOwner.setKpiOwn(kpiOwn);
            kpiOwner.setName(name);
            kpiOwner.setCoName(coName);
            kpiOwner = kpiOwnerRepo.save(kpiOwner);
            filesf.setKpiOwner(kpiOwner);
            filesRepo.save(filesf);
        }

    }
    @Override
    public void updateKpiOwn(String kpi, String name, String cn){
        KpiOwner ko=kpiOwnerRepo.findByKpiOwn(kpi);
        if (ko!=null){
            ko.setName(name);
            ko.setCoName(cn);
            kpiOwnerRepo.save(ko);
        }
    }


    @Override
    public List<KpiRest> getAllKpiOwner() {
        List<KpiOwner> kpiOwners = kpiOwnerRepo.findAll();
        List<KpiRest> kpiRests = new ArrayList<>();

        for (KpiOwner ko : kpiOwners) {
            KpiRest kr = new KpiRest();
            kr.setName(ko.getName());
            kr.setUri(ko.getFiles().getFileDownloadUri());
            kr.setKpiOwn(ko.getKpiOwn());
            kr.setCoName(ko.getCoName());
            kpiRests.add(kr);
        }

        return kpiRests;
    }


}
