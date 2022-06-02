package com.harun.labManagement.service;

import com.harun.labManagement.model.Report;

import java.util.List;

public interface IReportService {
    List<Report> getAllReports();

    Report getReportByID(Long reportId);

    Report getReportByUserName(Long reportId);

    Report getReportByPatientName(Long reportId);

    boolean isReportPresent(Long reportId);

    Long addReport(Report report);

    Long updateReport(Long reportId,Report report);

    Long removeReport(Long reportId);
}
