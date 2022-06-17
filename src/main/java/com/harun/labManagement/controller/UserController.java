package com.harun.labManagement.controller;

import com.harun.labManagement.model.User;
import com.harun.labManagement.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.List;


@Controller
public class UserController {
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private IUserService userService;

    /**
     * > This function is used to logout the user
     *
     * @param model The model is a map that contains the data that will be displayed in the view.
     * @return A string that is the name of the html file that is being returned.
     */
    @GetMapping("/logout")
    public String logout(Model model){
        return "redirect:/";
    }


    @GetMapping("/login")
    public String login(Model model){
        return "redirect:/reports";
    }

    /**
     * This method controls whether login input's are correct if it is correct redirect to home page
     * if it is not redirect to login page again
     *
     * @param id The id of the user
     * @param password The password of the user.
     * @param model This is the model object that is used to pass data from the controller to the view.
     * @return A string that is the name of the html file that is being returned.

    @GetMapping("/login/{id}/{password}")
    public String login(@PathVariable String id,@PathVariable String password,Model model){
        boolean isPresent = userService.isUserPresent(Long.parseLong(id));

        //Is User present
        if(isPresent){
            User user = (User) userService.getSingleUser(Long.parseLong(id));

            //Is password is correct
            if(user.getUserPassword().trim().equals(password.trim())){
                return "redirect:/reports";
            }
        }

        //If user is not present or password is wrong then return index with error message
        model.addAttribute("wrongLogin",true);
        return "index";
    }*/

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
    public String addUser(Model model){
            return "addUser";
    }

    /**
     * It adds a new user to the database if the user is a manager and the user is not already present in the database
     *
     * @param id ID of the user
     * @param password Password of the user
     * @param name The name of the parameter in the request.
     * @param surname Surname of the user
     * @param role If the user is a manager or not
     * @param model This is the model object that is used to pass data from the controller to the view.
     * @return A string
     */
    @GetMapping("/register/{id}/{password}/{name}/{surname}/{role}")
    public String register(@PathVariable String id,@PathVariable String password,@PathVariable String name,@PathVariable String surname,@PathVariable boolean role,Model model){

        Long idL = Long.parseLong(id);
        try{
            //If ID length is not 7
            if(id.trim().length() != 7) {
                model.addAttribute("lengthValidation", false);
                model.addAttribute("status",false);
            }

            //If a user exist with same id already then don't add and throw error
            else if(!userService.isUserPresent(idL)){
                String roleString;

                if(role)
                    roleString = "ROLE_MANAGER";
                else
                    roleString = "ROLE_LABORANT";

                userService.addUser(new User(idL,name,surname,encoder.encode(password.trim()),roleString));
                model.addAttribute("status",true);
            }

            else
                throw new IllegalArgumentException();

        }catch (IllegalArgumentException e){
            model.addAttribute("status",false);
        }
        return "addUser";
    }
}
