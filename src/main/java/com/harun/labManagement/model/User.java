package com.harun.labManagement.model;

import javax.persistence.*;

@Entity
@Table(name="users")
public class User {
    @Id
    private Long user_id;
    private String user_password;
    private String user_name;
    private String user_surname;
    private String role;


    public User()
    {
    }

    public User(Long user_id,String user_name,String user_surname,String user_password,String role)
    {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_surname = user_surname;
        this.user_password = user_password;
        this.role = role;
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

    public String getUserPassword() {
        return user_password;
    }

    public void setUserPassword(String user_password) {
        this.user_password = user_password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
