package com.harun.labManagement.service;

import com.harun.labManagement.model.Report;
import com.harun.labManagement.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ReportService implements IReportService{

    @Autowired
    private ReportRepository repository;

    @Override
    public List<Report> getAllReports() {
        return (List<Report>) repository.findAll();
    }

    @Override
    public List<Report> getAllReportsAscendingDate() {
        return repository.findAllByOrderByReportDateAsc();
    }

    @Override
    public List<Report> getAllReportsDescendingDate() {
        return repository.findAllByOrderByReportDateDesc();
    }

    @Override
    public Report getReportByID(Long reportId) {
        Optional<Report> optional = repository.findById(reportId);

        if(optional.isPresent())
            return optional.get();

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
        if(!repository.existsById(report.getId())){
            repository.save(report);
            return report.getId();
        }
        return null;
    }

    @Override
    public Long updateReport(Long reportId, Report report) {
        if(repository.existsById(reportId)){
            repository.deleteById(reportId);
            repository.save(report);
            return reportId;
        }
        return null;
    }

    @Override
    public Long removeReport(Long reportId) {
        if(repository.existsById(reportId)){
            repository.deleteById(reportId);
            return reportId;
        }

        return null;
    }
}
