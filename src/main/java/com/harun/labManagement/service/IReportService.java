package com.harun.labManagement.service;

import com.harun.labManagement.model.Report;

import java.util.Collection;
import java.util.List;

public interface IReportService {
    List<Report> getAllReports();

    List<Report> getAllReportsAscendingDate();

    List<Report> getAllReportsDescendingDate();

    List<Report> getAllReportsByPatientTC(Long patientTC);

    List<Report> getAllReportsByPatientNS(String name,String surname);

    List<Report> getAllReportsByLaborantNS(String name,String surname);

    List<Report> getAllReportsByPatientTCOrderedAsc(Long patientTC);

    List<Report> getAllReportsByPatientNSOrderedAsc(String name,String surname);

    List<Report> getAllReportsByLaborantNSOrderedAsc(String name,String surname);

    List<Report> getAllReportsByPatientTCOrderedDesc(Long patientTC);

    List<Report> getAllReportsByPatientNSOrderedDesc(String name,String surname);

    List<Report> getAllReportsByLaborantNSOrderedDesc(String name,String surname);

    Report getReportByID(Long reportId);

    Report getReportByUserName(Long reportId);

    Report getReportByPatientName(Long reportId);

    boolean isReportPresent(Long reportId);

    Long addReport(Report report);

    Long updateReport(Long reportId,Report report);

    Long removeReport(Long reportId);
}
