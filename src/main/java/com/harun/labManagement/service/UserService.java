package com.harun.labManagement.service;

import com.harun.labManagement.model.User;
import com.harun.labManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService{

    @Autowired
    private UserRepository repository;

    @Override
    public List<User> getAllUsers() {
        return (List<User>) repository.findAll();
    }

    @Override
    public boolean isUserPresent(Long userId) {
        return repository.existsById(userId);
    }

    @Override
    public User getSingleUser(Long userId) {
        return repository.findById(userId).get();
    }

    @Override
    public Long addUser(User user) {
        Long id = repository.save(user).getUserId();
        return id;
    }
}
