package com.harun.labManagement.controller;

import com.harun.labManagement.model.User;
import com.harun.labManagement.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class UserController {
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private IUserService userService;


    /**
     * If the user is logged in and is a manager, then get all users and return the users page
     *
     * @param model The model is a Map that is used to store the data that will be displayed on the view page.
     * @return A list of all users
     */
    @GetMapping("/users")
    public String allUsers(Model model){
        List<User> users = (List<User>) userService.getAllUsers();
        model.addAttribute("usersList", users);
        return "users";
    }

    /**
     * If the user is logged in and is a manager, then get the user with the given id and return the user page
     *
     * @param id the id of the user
     * @param model The model is a Map (a collection of key/value pairs) that stores data. The model is passed to the view
     * template when the controller method is called. The model is used to set values that can be used in the view
     * template.
     * @return A string that is the name of the html file that is being returned.
     */
    @GetMapping("/user/{id}")
    public String user(@PathVariable String id, Model model){
        User result = (User) userService.getSingleUser(Long.parseLong(id));
        model.addAttribute("user",result);
        return "user";
    }

    /**
     * If the user is logged in and is a manager, then return the addUser page
     *
     * @param model The model is a Map that is used to store the data that will be displayed on the view page.
     * @return A string that is the name of the html file that is being returned.
     */
    @GetMapping("/addUser")
    public String addUserPage(Model model){
        model.addAttribute("user",new User());
        return "addUser";
    }


    /**
     * It adds a user to the database
     *
     * @param user This is the object that is created by Spring MVC. It is created by matching the form fields with the
     * attributes of the User class.
     * @param isManager This is a boolean parameter that is used to determine whether the user is a manager or a laborant.
     * @param model This is the model object that is used to pass data from the controller to the view.
     * @return A String
     */
    @PostMapping("/addUser")
    public String addUser(@ModelAttribute User user, @RequestParam("manager") boolean isManager, Model model){
        System.out.println(user.getUserId());
        System.out.println(user.getUserName());
        System.out.println(user.getUserSurname());
        System.out.println(user.getUserPassword());
        System.out.println(isManager);

        try{
            //If ID length is not 7
            if(user.getUserId().toString().length() != 7) {
                model.addAttribute("status",false);
                model.addAttribute("statusMessage","ID Length must be 7!");
            }

            //If a user exist with same id already then don't add and throw error
            else if(!userService.isUserPresent(user.getUserId())){
                String roleString;

                if(isManager)
                    roleString = "ROLE_MANAGER";
                else
                    roleString = "ROLE_LABORANT";

                userService.addUser(new User(user.getUserId(),user.getUserName().trim(),user.getUserSurname().trim(),encoder.encode(user.getUserPassword().trim()),roleString));
                model.addAttribute("status",true);
                model.addAttribute("statusMessage","User has been added successfully!");
            }

            else
                throw new IllegalArgumentException();

        }catch (IllegalArgumentException e){
            model.addAttribute("status",false);
            model.addAttribute("statusMessage","User has not been added. Error!");
        }
        return "addUser";
    }
}
