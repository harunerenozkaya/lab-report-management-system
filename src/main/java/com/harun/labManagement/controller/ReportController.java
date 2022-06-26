package com.harun.labManagement.controller;


import com.harun.labManagement.model.Report;
import com.harun.labManagement.model.User;
import com.harun.labManagement.service.IReportService;
import com.harun.labManagement.service.IUserService;
import com.harun.labManagement.util.FileUtil;
import com.harun.labManagement.util.IDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
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
     * It gets all reports from database by parameters and returns them to the view
     *
     * @param orderType It is used to sort the reports.
     * @param searchType It is the type of search.
     * @param searchInput The input that the user has entered in the search bar.
     * @param model The model is a Map that is used to store the data that will be displayed on the view page.
     * @return A list of reports.
     */
    @GetMapping("/reports")
    public String allReports(@RequestParam(value = "orderType", required = false) String orderType,@RequestParam(value = "searchType", required = false) String searchType,@RequestParam(value = "searchInput", required = false) String searchInput,Model model){
        List<Report> reports = null;

        /*
        * Build search name surname
        */
        int nameSurnameCount = 0;
        StringBuilder name = new StringBuilder();
        String surname = "";

        if(searchInput != null){
            String[] nameArr = searchInput.trim().split("\\s+");

            for(int i=0; i<nameArr.length; i++){
                if(i == nameArr.length-1)
                    surname = nameArr[i];
                else
                    name.append(nameArr[i] + " ");
            }
        }

        //Get currentUser
        User currentUser = getCurrentUser();

        //Get all reports
        if(orderType == null && searchType == null){
            reports = (List<Report>) reportService.getAllReports();
        }
        //Get all reports ordered
        else if(orderType != null && searchType == null){
            if(orderType.equals("asc")){
                reports = (List<Report>) reportService.getAllReportsAscendingDate();
            }else if( orderType.equals("desc")){
                reports = (List<Report>) reportService.getAllReportsDescendingDate();
            }
        }
        //Get searching reports (not ordered)
        else if(orderType == null && searchType != null){

            switch (searchType){
                case "patientTC":
                    reports = (List<Report>) reportService.getAllReportsByPatientTC(Long.parseLong(searchInput));
                    break;
                case "patientNS":
                    reports = (List<Report>) reportService.getAllReportsByPatientNS(name.toString(),surname);
                    break;
                case "laborantNS":
                    reports = (List<Report>) reportService.getAllReportsByLaborantNS(name.toString(),surname);
                    break;
            }
        }
        //Get searching reports (ordered)
        else{
            if(orderType.equals("asc")){

                switch (searchType){
                    case "patientTC":
                        reports = (List<Report>) reportService.getAllReportsByPatientTCOrderedAsc(Long.parseLong(searchInput));
                        break;
                    case "patientNS":
                        reports = (List<Report>) reportService.getAllReportsByPatientNSOrderedAsc(name.toString(),surname);
                        break;
                    case "laborantNS":
                        reports = (List<Report>) reportService.getAllReportsByLaborantNSOrderedAsc(name.toString(),surname);
                        break;
                }
            }else if( orderType.equals("desc")){
                switch (searchType){
                    case "patientTC":
                        reports = (List<Report>) reportService.getAllReportsByPatientTCOrderedDesc(Long.parseLong(searchInput));
                        break;
                    case "patientNS":
                        reports = (List<Report>) reportService.getAllReportsByPatientNSOrderedDesc(name.toString(),surname);
                        break;
                    case "laborantNS":
                        reports = (List<Report>) reportService.getAllReportsByLaborantNSOrderedDesc(name.toString(),surname);
                        break;
                }
            }
        }

        model.addAttribute("reports",reports);
        model.addAttribute("role",currentUser.getRole());

        return "reports";
    }

    /**
     * This function is used to redirect to add report page
     *
     * @param model The model is a Map of model objects which will be passed to the view page.
     * @return A string that is the name of the html file that is being returned.
     */
    @GetMapping("/addReport")
    public String addReportPage(Model model){
        Report newReport = new Report();
        newReport.setId(IDGenerator.generateID(7));

        model.addAttribute("report",newReport);

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
                    FileUtil.saveFile("reportPhotos/",report.getId().toString() + ".png",multipartFile);

                //Add report
                reportService.addReport(new Report(report.getId(), LocalDate.now(),report.getPatientName().trim(),report.getPatientSurname().trim(),report.getPatientTC(),report.getDiagnosisTitle().trim(),report.getDiagnosisDetail().trim(), currentUser.getUserId(), currentUser.getUserName().trim(), currentUser.getUserSurname().trim()));
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
     * It gets the report with the given id and returns the report page
     *
     * @param reportId The id of the report that we want to see.
     * @param model The model is a Map that is used to store the data that needs to be displayed on the view page.
     * @return A report page with the report's information.
     */
    @GetMapping("/report/{reportId}")
    public String report(@PathVariable String reportId,Model model){
        Long reportIdL = Long.parseLong(reportId);

        //Get report
        Report report = reportService.getReportByID(reportIdL);

        model.addAttribute("report",report);

        //Controls the whether report photo is exist , if exist show downloadn button
        if(FileUtil.isFileExist("reportPhotos/",reportId+".png"))
            model.addAttribute("isImage",true);

        return "report";
    }

    /**
     * It takes a reportId, gets the image from the database, and writes it to the response
     *
     * @param reportId The id of the report you want to download the image for.
     * @param response The response object that will be used to send the image to the client.
     */
    @GetMapping("/downloadImage/{reportId}")
    public void downloadReportImage(@PathVariable String reportId, HttpServletResponse response) throws IOException {
        response.setContentType("image/png");
        response.getOutputStream().write(FileUtil.getFile("reportPhotos/",reportId + ".png"));
        response.getOutputStream().close();
    }

    /**
     * The function takes in a reportId and a model, and then deletes the report with the given reportId, and then
     * redirects the user to the reports page
     *
     * @param reportId The id of the report to be deleted.
     * @param model This is the model object that is used to pass data from the controller to the view.
     * @return A string that redirects to the reports page.
     */
    @GetMapping("/deleteReport/{reportId}")
    public String deleteReport(@PathVariable String reportId,Model model) throws IOException {
        //Delete report from database
        reportService.removeReport(Long.parseLong(reportId));

        //Delete image
        FileUtil.deleteFile("reportPhotos/",reportId + ".png");

        return "redirect:/reports";
    }

    /**
     * This function shows the editReport page
     *
     * @param reportId The id of the report to be edited.
     * @param model This is the model that will be passed to the view.
     * @return The editReport.html page is being returned.
     */
    @GetMapping("/editReport/{reportId}")
    public String editReport(@PathVariable String reportId,Model model){
        Long reportIdL = Long.parseLong(reportId);

        //Get report
        Report report = reportService.getReportByID(reportIdL);

        model.addAttribute("report",report);

        return "editReport";
    }

    /**
     * This function is used to edit a report
     *
     * @param report The report object that is sent from the form.
     * @param multipartFile The file that is uploaded.
     * @param model The model is an object containing all model attributes.
     * @return a String.
     */
    @PostMapping("/editReport")
    public String editReport(@ModelAttribute Report report, @RequestParam("diagnosis-img") MultipartFile multipartFile, Model model){

        try{

            //If TC length is not 11
            if (String.valueOf(report.getPatientTC()).length() != 11) {
                model.addAttribute("status",false);
                model.addAttribute("statusMessage","Patient TC length must be 11 !");
            }

            //If a report exist with same id already then it can be editable
            else if(reportService.isReportPresent(report.getId())){
                //Get laborant or manager who add this report
                User currentUser = getCurrentUser();

                //Delete old image
                FileUtil.deleteFile("reportPhotos/",report.getId().toString() + ".png");

                //Save image if it is not empty
                if(!multipartFile.isEmpty())
                    FileUtil.saveFile("reportPhotos/",report.getId().toString() + ".png",multipartFile);

                //Edit report
                reportService.updateReport(report.getId(),new Report(report.getId(), LocalDate.now(),report.getPatientName().trim(),report.getPatientSurname().trim(),report.getPatientTC(),report.getDiagnosisTitle().trim(),report.getDiagnosisDetail().trim(), currentUser.getUserId(), currentUser.getUserName().trim(), currentUser.getUserSurname().trim()));
                model.addAttribute("status",true);
                model.addAttribute("statusMessage","Report has been edited successfully");
            }

        } catch (IOException e) {
            model.addAttribute("status",false);
            model.addAttribute("statusMessage","There is a error about photo!");
            throw new RuntimeException(e);
        }

        return "editReport";
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
