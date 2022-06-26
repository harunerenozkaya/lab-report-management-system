package com.harun.labManagement.controller;

import com.harun.labManagement.model.Report;
import com.harun.labManagement.model.User;
import com.harun.labManagement.service.IReportService;
import com.harun.labManagement.service.IUserService;
import com.harun.labManagement.util.FileUtil;
import com.harun.labManagement.util.IDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;


@Controller
public class UserController {
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private IUserService userService;

    @Autowired
    private IReportService reportService;


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
        model.addAttribute("reportCount",reportService.getReportCountByLaborantID(result.getUserId()));
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
        User newUser = new User();
        newUser.setUserId(IDGenerator.generateID(7));

        model.addAttribute("user",newUser);
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

        try{
            //If a user exist with same id already then don't add and throw error
            if(!userService.isUserPresent(user.getUserId())){
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


    /**
     * The function takes in a userId and a model, and then deletes the user with the given userId from the database
     *
     * @param userId The id of the user to be deleted.
     * @param model This is the model object that is used to pass data from the controller to the view.
     * @return A redirect to the users page.
     */
    @GetMapping("/deleteUser/{userId}")
    public String deleteUser(@PathVariable String userId,Model model) throws IOException {
        //Delete user from database
        userService.removeUser(Long.parseLong(userId));

        return "redirect:/users";
    }


    /**
     * This function takes in a userId, parses it to a Long, gets the user from the database, and adds the user to the
     * model
     *
     * @param userId This is the id of the user we want to edit.
     * @param model This is the model object that is used to pass data from the controller to the view.
     * @return The editUser.html page
     */
    @GetMapping("/editUser/{userId}")
    public String editUser(@PathVariable String userId,Model model){
        Long userIdL = Long.parseLong(userId);

        //Get user
        User user = userService.getSingleUser(userIdL);

        model.addAttribute("user",user);

        return "editUser";
    }


    /**
     * The function takes a user object, a boolean value and a model object as parameters. It then checks if the user is
     * present in the database. If the user is present, it updates the user's information in the database. If the user is
     * not present, it throws an error
     *
     * @param user This is the user object that is being passed from the form.
     * @param isManager boolean value that is true if the user is a manager and false if the user is a laborant.
     * @param model This is the model object that is used to pass data from the controller to the view.
     * @return A string
     */
    @PostMapping("/editUser")
    public String editUser(@ModelAttribute User user, @RequestParam("manager") boolean isManager, Model model){

        try{
            //If a user exist with same id already then it can be edittable
            if(userService.isUserPresent(user.getUserId())){
                String roleString;

                if(isManager)
                    roleString = "ROLE_MANAGER";
                else
                    roleString = "ROLE_LABORANT";

                userService.updateUser(new User(user.getUserId(),user.getUserName().trim(),user.getUserSurname().trim(),encoder.encode(user.getUserPassword().trim()),roleString));
                model.addAttribute("status",true);
                model.addAttribute("statusMessage","User has been edited successfully!");
            }

            else
                throw new IllegalArgumentException();

        } catch (IllegalArgumentException e) {
            model.addAttribute("status",false);
            model.addAttribute("statusMessage","User has not been edited. Error!");
        }

        return "editUser";
    }
}
