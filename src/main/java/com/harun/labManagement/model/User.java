package com.harun.labManagement.model;

import javax.persistence.*;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    private String user_name;
    private String user_surname;
    private boolean is_manager;
    private String user_password;


    public User()
    {
    }

    public User(Long user_id,String user_name,String user_surname,boolean is_manager,String user_password)
    {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_surname = user_surname;
        this.is_manager = is_manager;
        this.user_password = user_password;
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

    public boolean getIsManager() {
        return is_manager;
    }

    public void setIsManager(boolean is_manager){
        this.is_manager = is_manager;
    }

    public String getUserPassword() {
        return user_password;
    }

    public void setUserPassword(String user_password) {
        this.user_password = user_password;
    }
}
