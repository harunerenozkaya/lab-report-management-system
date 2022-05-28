package com.harun.labManagement.controller;

import com.harun.labManagement.model.User;
import com.harun.labManagement.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Objects;

@Controller
public class UserController {

    @Autowired
    private IUserService userService;


    @GetMapping("/users")
    public String allUsers(Model model){
        List<User> result = (List<User>) userService.getAllUsers();
        model.addAttribute("usersList",result);
        return "users";
    }

    @GetMapping("/user/{id}")
    public String user(@PathVariable String id, Model model){
        User result = (User) userService.getSingleUser(Long.parseLong(id));
        model.addAttribute("user",result);
        return "user";
    }

    @GetMapping("/addUser/{is_manager}")
    public String addUser(@PathVariable String is_manager,Model model){
        System.out.println(is_manager);
        if(Objects.equals(is_manager, "true"))
            return "addUser";
        else
            return "redirect:/users";
    }
}
