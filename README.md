<h1 align="center"> Lab Report Management System </h1> <br>

<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [System Requirements](#system-requirements)
- [Build Process](#build-process)
- [Technologies](#technologies)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

<br>


## Introduction

Lab Report Management System is a system which managers and laborants can add ,view ,edit ,delete reports and manage a report process easily. This system runs on web.

<br>

## Features

A few of the things users can do with Lab Report Management System:

* Login With User ID and Password

    <img src = "screenshots/login.png" width=700>

* View All Reports

    <img src = "screenshots/allReports.png" width=700>

* Add New Report

    <img src = "screenshots/addReport.png" width=700>

* View A Report

    <img src = "screenshots/reportDetail.png" width=700>

* Update A Report

    <img src = "screenshots/editReport.png" width=700>

* Delete A Report

    <img src = "screenshots/deleteReport.png" width=700>

* Search Reports via Patient Name , Laborant Name , Patient TC

    <img src = "screenshots/search1.png" width=700>
    <img src = "screenshots/search2.png" width=700>
    <img src = "screenshots/search3.png" width=700>

* Sort Reports by Date

    <img src = "screenshots/sortAscAllReports.png" width=700>

    <img src = "screenshots/sortDescAllReports.png" width=700>

* Attach A Image to Reports

    <img src = "screenshots/attachImage.png" width=700>

* View All System Users (Managers can perform only)

    <img src = "screenshots/allUsers.png" width=700>

* Add New System User (Managers can perform only)

    <img src = "screenshots/addUser.png" width=700>

* View A System User (Managers can perform only)

    <img src = "screenshots/userDetail.png" width=700>

* Update A System User (Managers can perform only)

    <img src = "screenshots/editUser.png" width=700>

* Delete A System User (Managers can perform only)

    <img src = "screenshots/deleteUser.png" width=700>


## System Requirements

- Java 8 (or higher)
- Maven 3.x.x (or higher)
- PostgreSQL 14 (or higher)

## Build Process


- Create a PostgreSQL server

<br/>

- Clone or download the repo

<br/>

- Configure src/main/resource/application.properties
  * `spring.datasource.url = YOUR URL`
  * `spring.datasource.username = YOUR USERNAME`
  * `spring.datasource.password = YOUR PASSWORD`
  
<br/>

- Run the following command in a terminal window (in the complete) directory:
  * `./mvnw spring-boot:run`

<br/>

- Open system
  * For local usage : `localhost:8080`
  
<br/>

- For initial login admin login informations :
  * `username : 1000000`
  * `password : admin`



## Technologies

- Java
- Spring
- Thymeleaf
- Hibernate
- Maven
- PostgreSQL
- Javascript
- Boostrap
- HTML / CSS


