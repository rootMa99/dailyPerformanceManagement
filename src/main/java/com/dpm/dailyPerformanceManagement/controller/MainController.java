package com.dpm.dailyPerformanceManagement.controller;

import com.dpm.dailyPerformanceManagement.domain.ActionPlan;
import com.dpm.dailyPerformanceManagement.domain.Files;
import com.dpm.dailyPerformanceManagement.models.ActionPlanModel;
import com.dpm.dailyPerformanceManagement.models.DataRest;
import com.dpm.dailyPerformanceManagement.models.KpiNameRest;
import com.dpm.dailyPerformanceManagement.models.KpiRest;
import com.dpm.dailyPerformanceManagement.services.ActionPlanService;
import com.dpm.dailyPerformanceManagement.services.DataByDateService;
import com.dpm.dailyPerformanceManagement.services.KpiNamesService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/dpm")
@AllArgsConstructor
public class MainController {
    DataByDateService dataByDateService;
    KpiNamesService kpiNamesService;
    ActionPlanService aps;

    @GetMapping(path = "/delivery")
    public List<DataRest> getDelivery(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate) {
        return dataByDateService.getDelivriesDateBetween(startDate, endDate);
    }

    @GetMapping(path = "/inventory")
    public List<DataRest> getInventory(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate) {
        return dataByDateService.getInventoryDateBetween(startDate, endDate);
    }

    @GetMapping(path = "/kaizen")
    public List<DataRest> getKaizen(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate) {
        return dataByDateService.getKaizenDateBetween(startDate, endDate);
    }

    @GetMapping(path = "/productivity")
    public List<DataRest> getProductivity(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate) {
        return dataByDateService.getProductivityDateBetween(startDate, endDate);
    }

    @GetMapping(path = "/quality")
    public List<DataRest> getQuality(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate) {
        return dataByDateService.getQualityDateBetween(startDate, endDate);
    }

    @GetMapping(path = "/safety")
    public List<DataRest> getSafety(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate) {
        return dataByDateService.getSafetyDateBetween(startDate, endDate);
    }

    @GetMapping(path = "/skills")
    public List<DataRest> getSkkills(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate) {
        return dataByDateService.getSkillsDateBetween(startDate, endDate);
    }

    @PostMapping("/kpio")
    public void addFileToProject(@RequestParam String kpiOwn, @RequestParam String name, @RequestParam String coName, @RequestParam(value = "file") MultipartFile file) throws IOException {
        dataByDateService.addKpiOwner(kpiOwn, name, coName, file);
    }

    @PostMapping("/kpio/owner")
    public void updateKpiOwner(@RequestParam String kpiOwn, @RequestParam String name, @RequestParam String coName) {
        dataByDateService.updateKpiOwn(kpiOwn, name, coName);
    }

    @GetMapping("/downloadFile/{fileId:.+}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileId, HttpServletRequest request) throws FileNotFoundException {
        Files fileEntity = dataByDateService.getFileByFileId(fileId);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(fileEntity.getFileType())).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileEntity.getFileName() + "\"").body(new ByteArrayResource(fileEntity.getData()));
    }

    @GetMapping("/owners")
    public List<KpiRest> getAllOwners() {
        return dataByDateService.getAllKpiOwner();
    }

    @GetMapping("/kpiNames")
    public List<KpiNameRest> updateKpiOwner(@RequestParam String kpiName) {
        return kpiNamesService.kpiNamesList(kpiName);
    }
    @GetMapping("/aec")
    @CrossOrigin(origins = "*")
    public List<ActionPlanModel> getApByDateBetween(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate){
        return aps.apm(startDate, endDate);
    }
    @PostMapping("/uacp")
    public ActionPlanModel updateActionPlan(@RequestBody ActionPlanModel actionPlanModel, @RequestParam Long id){
        return aps.updateActionPlan(actionPlanModel, id);
    }
}
