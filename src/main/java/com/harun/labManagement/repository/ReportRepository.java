package com.harun.labManagement.repository;

import com.harun.labManagement.model.Report;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends CrudRepository<Report,Long> {
}
