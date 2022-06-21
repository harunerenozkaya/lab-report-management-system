package com.harun.labManagement.controller;


import com.harun.labManagement.model.Report;
import com.harun.labManagement.model.User;
import com.harun.labManagement.service.IReportService;
import com.harun.labManagement.service.IUserService;
import com.harun.labManagement.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Controller
public class ReportController {

    @Autowired
    private IReportService reportService;

    @Autowired
    private IUserService userService;


    /**
     * This function is used to get all the reports from the database and display them on the reports page
     *
     * @param model The model is a Map that is used to store the data that will be displayed on the view page.
     * @return A list of all reports
     */
    @GetMapping("/reports")
    public String allReports(Model model){
        //Get currentUser
        User currentUser = getCurrentUser();
        //Get all reports
        List<Report> reports = (List<Report>) reportService.getAllReports();

        model.addAttribute("reportList",reports);
        model.addAttribute("role",currentUser.getRole());

        return "reports";
    }

    /**
     * This function is used to redirect to add report page
     *
     * @param model The model is a Map of model objects which will be passed to the view page.
     * @return A string that is the name of the html file that is being returned.
     */
    @GetMapping("/addReportPage")
    public String addReportPage(Model model){
        model.addAttribute("report",new Report());
        return "addReport";
    }


    /**
     * It adds a report to the database.
     *
     * @param report This is the object that will be used to store the data that is entered into the form.
     * @param multipartFile The file that is uploaded.
     * @param model The model is a Map that is used to store the data that will be displayed on the view page.
     * @return a String.
     */
    @PostMapping("/addReport")
    public String addReport(@ModelAttribute Report report, @RequestParam("diagnosis-img") MultipartFile multipartFile, Model model){

        try{
            //If ID length is not 7
            if(String.valueOf(report.getId()).length() != 7) {
                model.addAttribute("status",false);
                model.addAttribute("statusMessage","Report ID length must be 7 !");
            }
            //If TC length is not 11
            else if (String.valueOf(report.getPatientTC()).length() != 11) {
                model.addAttribute("status",false);
                model.addAttribute("statusMessage","Patient TC length must be 11 !");
            }

            //If a report exist with same id already then don't add and throw error
            else if(!reportService.isReportPresent(report.getId())){
                //Get laborant or manager who add this report
                User currentUser = getCurrentUser();

                //Save image if it is not null
                if(!multipartFile.isEmpty())
                    FileUploadUtil.saveFile("reportPhotos/",report.getId().toString() + ".png",multipartFile);

                //Add report
                reportService.addReport(new Report(report.getId(), LocalDate.now(),report.getPatientName(),report.getPatientSurname(),report.getPatientTC(),report.getDiagnosisTitle(),report.getDiagnosisDetail(), currentUser.getUserId(), currentUser.getUserName(), currentUser.getUserSurname()));
                model.addAttribute("status",true);
                model.addAttribute("statusMessage","Report has been added successfully");
            }

            else
                throw new IllegalArgumentException();

        }catch (IllegalArgumentException e){
            model.addAttribute("status",false);
            model.addAttribute("statusMessage","There is a report with same ID !");
        } catch (IOException e) {
            model.addAttribute("status",false);
            model.addAttribute("statusMessage","There is a error about photo!");
            throw new RuntimeException(e);
        }
        return "addReport";
    }

    /**
     * It gets the current user's username from the security context and then returns the user object from the database
     *
     * @return The current user's username.
     */
    private User getCurrentUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = null;


        if(principal instanceof UserDetails)
            username = ((UserDetails) principal).getUsername();

        else
            username = principal.toString();

        return userService.getSingleUser(Long.parseLong(username));
    }

}
