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

    @Query(value = "SELECT * FROM reports r WHERE r.patient_tc =:patientTC" ,nativeQuery = true)
    public List<Report> getAllReportsByPatientTC(Long patientTC);

    @Query(value = "SELECT * FROM reports r WHERE r.patient_name =:name AND r.patient_surname =:surname" ,nativeQuery = true)
    public List<Report> getAllReportsByPatientNS(String name, String surname);

    @Query(value = "SELECT * FROM reports r WHERE r.user_name =:name AND r.user_surname =:surname" ,nativeQuery = true)
    public List<Report> getAllReportsByLaborantNS(String name, String surname);

    @Query(value = "SELECT * FROM reports r WHERE r.patient_tc =:patientTC ORDER BY report_date ASC" ,nativeQuery = true)
    public List<Report> getAllReportsByPatientTCOrderedAsc(Long patientTC);

    @Query(value = "SELECT * FROM reports r WHERE r.patient_name =:name AND r.patient_surname =:surname ORDER BY report_date ASC" ,nativeQuery = true)
    public List<Report> getAllReportsByPatientNSOrderedAsc(String name, String surname);

    @Query(value = "SELECT * FROM reports r WHERE r.user_name =:name AND r.user_surname =:surname ORDER BY report_date ASC" ,nativeQuery = true)
    public List<Report> getAllReportsByLaborantNSOrderedAsc(String name, String surname);

    @Query(value = "SELECT * FROM reports r WHERE r.patient_tc =:patientTC ORDER BY report_date DESC;" ,nativeQuery = true)
    public List<Report> getAllReportsByPatientTCOrderedDesc(Long patientTC);

    @Query(value = "SELECT * FROM reports r WHERE r.patient_name =:name AND r.patient_surname =:surname ORDER BY report_date DESC;" ,nativeQuery = true)
    public List<Report> getAllReportsByPatientNSOrderedDesc(String name, String surname);

    @Query(value = "SELECT * FROM reports r WHERE r.user_name =:name AND r.user_surname =:surname ORDER BY report_date DESC;" ,nativeQuery = true)
    public List<Report> getAllReportsByLaborantNSOrderedDesc(String name, String surname);

    @Query(value = "SELECT COUNT(*) FROM reports r WHERE r.user_id =:userID",nativeQuery = true)
    public int getReportCountByLaborantID(Long userID);
}
