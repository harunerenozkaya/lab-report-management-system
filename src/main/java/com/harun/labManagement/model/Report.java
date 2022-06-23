package com.harun.labManagement.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="reports")
public class Report {
    @Id
    @Column(name = "report_id")
    private Long report_id;
    @Column(name = "report_date")
    private LocalDate report_date;
    @Column(name = "patient_name")
    private String patient_name;
    @Column(name = "patient_surname")
    private String patient_surname;
    @Column(name = "patient_tc")
    private Long patient_tc;
    @Column(name = "diagnosis_title")
    private String diagnosis_title;
    @Column(name = "diagnosis_detail")
    private String diagnosis_detail;
    @Column(name = "user_id")
    private Long user_id;
    @Column(name = "user_name")
    private String user_name;
    @Column(name = "user_surname")
    private String user_surname;


    public Report(){

    }

    public Report(Long report_id, LocalDate report_date, String patient_name, String patient_surname, Long patient_tc, String diagnosis_title, String diagnosis_detail, Long user_id, String user_name, String user_surname){
        this.report_id = report_id;
        this.report_date = report_date;
        this.patient_name = patient_name;
        this.patient_surname = patient_surname;
        this.patient_tc = patient_tc;
        this.diagnosis_title = diagnosis_title;
        this.diagnosis_detail = diagnosis_detail;
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_surname = user_surname;
    }

    public Long getId() {
        return report_id;
    }

    public void setId(Long report_id) {
        this.report_id = report_id;
    }

    public LocalDate getReportDate() {
        return report_date;
    }

    public void setReportDate(LocalDate report_date) {
        this.report_date = report_date;
    }

    public String getPatientName() {
        return patient_name;
    }

    public void setPatientName(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getPatientSurname() {
        return patient_surname;
    }

    public void setPatientSurname(String patient_surname) {
        this.patient_surname = patient_surname;
    }

    public Long getPatientTC() {
        return patient_tc;
    }

    public void setPatientTC(Long patient_tc) {
        this.patient_tc = patient_tc;
    }

    public String getDiagnosisTitle() {
        return diagnosis_title;
    }

    public void setDiagnosisTitle(String diagnosis_title) {
        this.diagnosis_title = diagnosis_title;
    }

    public String getDiagnosisDetail() {
        return diagnosis_detail;
    }

    public void setDiagnosisDetail(String diagnosis_detail) {
        this.diagnosis_detail = diagnosis_detail;
    }

    public Long getUserId() {
        return user_id;
    }

    public void setUserId(Long user_id) {
        this.user_id = user_id;
    }

    public String getUserName() {
        return user_name;
    }

    public void setUserName(String user_name) {
        this.user_name = user_name;
    }

    public String getUserSurname() {
        return user_surname;
    }

    public void setUserSurname(String user_surname) {
        this.user_surname = user_surname;
    }

}
