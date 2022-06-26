package com.harun.labManagement.service;

import com.harun.labManagement.model.User;

import java.util.List;

public interface IUserService {
    List<User> getAllUsers();
    User getSingleUser(Long userId);
    boolean isUserPresent(Long userId);
    Long addUser(User user);
    Long removeUser(Long userId);
    Long updateUser(User user);

}
