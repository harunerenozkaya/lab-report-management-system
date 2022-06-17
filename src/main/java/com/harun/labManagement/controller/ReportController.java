package com.harun.labManagement.controller;

import ch.qos.logback.core.util.TimeUtil;
import com.harun.labManagement.model.Report;
import com.harun.labManagement.service.IReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;

@Controller
public class ReportController {

    @Autowired
    private IReportService reportService;


    @GetMapping("/reports")
    public String allReports(Model model){
        List<Report> reports = (List<Report>) reportService.getAllReports();
        model.addAttribute("reportList",reports);
        model.addAttribute("role","ROLE_MANAGER");
        return "reports";
    }

    @GetMapping("/addReportPage")
    public String addReportPage(Model model){
        return "addReport";
    }

    @GetMapping("/addReport/{id}/{patientName}/{patientSurname}/{patientTC}/{diagnosisTitle}/{diagnosisDetail}")
    public String addReport(@PathVariable String id, @PathVariable String patientName, @PathVariable String patientSurname, @PathVariable String patientTC, @PathVariable String diagnosisTitle , @PathVariable String diagnosisDetail, Model model){

        Long reportID = Long.parseLong(id);

        try{
            //If ID length is not 7
            if(id.trim().length() != 7) {
                model.addAttribute("lengthValidation", false);
                model.addAttribute("status",false);
            }

            //If a report exist with same id already then don't add and throw error
            else if(!reportService.isReportPresent(reportID)){
                reportService.addReport(new Report(reportID,LocalDate.now(),patientName,patientSurname,Long.parseLong(patientTC),diagnosisTitle,diagnosisDetail, 0L,"23","23"));
                model.addAttribute("status",true);
            }

            else
                throw new IllegalArgumentException();

        }catch (IllegalArgumentException e){
            model.addAttribute("status",false);
        }

        return "addReport";

    }
}
