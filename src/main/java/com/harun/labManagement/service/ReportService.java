package com.harun.labManagement.service;

import com.harun.labManagement.model.Report;
import com.harun.labManagement.model.User;
import com.harun.labManagement.repository.ReportRepository;
import com.harun.labManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService implements IReportService{

    @Autowired
    private ReportRepository repository;

    @Override
    public List<Report> getAllReports() {
        return (List<Report>) repository.findAll();
    }

    @Override
    public Report getReportByID(Long reportId) {
        return null;
    }

    @Override
    public Report getReportByUserName(Long reportId) {
        return null;
    }

    @Override
    public Report getReportByPatientName(Long reportId) {
        return null;
    }

    @Override
    public boolean isReportPresent(Long reportId) {
        return repository.existsById(reportId);
    }

    @Override
    public Long addReport(Report report) {
        Long id = repository.save(report).getId();
        return id;
    }

    @Override
    public Long updateReport(Long reportId, Report report) {
        return null;
    }

    @Override
    public Long removeReport(Long reportId) {
        return null;
    }
}
