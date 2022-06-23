package com.harun.labManagement.service;

import com.harun.labManagement.model.Report;

import java.util.Collection;
import java.util.List;

public interface IReportService {
    List<Report> getAllReports();

    List<Report> getAllReportsAscendingDate();

    List<Report> getAllReportsDescendingDate();

    Report getReportByID(Long reportId);

    Report getReportByUserName(Long reportId);

    Report getReportByPatientName(Long reportId);

    boolean isReportPresent(Long reportId);

    Long addReport(Report report);

    Long updateReport(Long reportId,Report report);

    Long removeReport(Long reportId);
}
