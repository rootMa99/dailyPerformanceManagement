package com.dpm.dailyPerformanceManagement.services;

import com.dpm.dailyPerformanceManagement.domain.Files;
import com.dpm.dailyPerformanceManagement.models.DataRest;
import com.dpm.dailyPerformanceManagement.models.KpiRest;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface DataByDateService {
    List<DataRest> getDelivriesDateBetween(Date start, Date end);

    List<DataRest> getInventoryDateBetween(Date start, Date end);

    List<DataRest> getKaizenDateBetween(Date start, Date end);

    List<DataRest> getProductivityDateBetween(Date start, Date end);

    List<DataRest> getQualityDateBetween(Date start, Date end);

    List<DataRest> getSafetyDateBetween(Date start, Date end);

    List<DataRest> getSkillsDateBetween(Date start, Date end);

    Files getFileByFileId(String fileId) throws FileNotFoundException;

    Files uploadFile(MultipartFile file) throws IOException;

    void addKpiOwner(String kpiOwn, String name, String coName, MultipartFile file) throws IOException;

    List<KpiRest> getAllKpiOwner();
}
