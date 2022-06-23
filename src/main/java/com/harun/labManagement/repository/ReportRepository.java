package com.harun.labManagement.repository;

import com.harun.labManagement.model.Report;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ReportRepository extends CrudRepository<Report,Long> {

    @Query(value = "SELECT * FROM reports ORDER BY report_date ASC;" ,nativeQuery = true)
    public List<Report> findAllByOrderByReportDateAsc();
    @Query(value = "SELECT * FROM reports ORDER BY report_date DESC;" ,nativeQuery = true)
    public List<Report> findAllByOrderByReportDateDesc();
}
