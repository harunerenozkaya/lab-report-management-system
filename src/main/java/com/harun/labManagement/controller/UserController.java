package com.harun.labManagement.controller;

import com.harun.labManagement.model.User;
import com.harun.labManagement.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import java.util.List;


@Controller
public class UserController {
    private boolean isLogin = false;
    private User loginedUser = null;

    @Autowired
    private IUserService userService;

    /**
     * > This function is used to logout the user
     *
     * @param model The model is a map that contains the data that will be displayed in the view.
     * @return A string that is the name of the html file that is being returned.
     */
    @GetMapping("/unlogin")
    public String unlogin(Model model){
        isLogin = false;
        loginedUser = null;
        return "redirect:/";
    }

    /**
     * This method controls whether login input's are correct if it is correct redirect to home page
     * if it is not redirect to login page again
     *
     * @param id The id of the user
     * @param password The password of the user.
     * @param model This is the model object that is used to pass data from the controller to the view.
     * @return A string that is the name of the html file that is being returned.
     */
    @GetMapping("/login/{id}/{password}")
    public String login(@PathVariable String id,@PathVariable String password,Model model){
        boolean isPresent = userService.isUserPresent(Long.parseLong(id));

        if(isPresent){
            User user = (User) userService.getSingleUser(Long.parseLong(id));

            if(user.getUserPassword().trim().equals(password.trim())){
                isLogin = true;
                loginedUser = user;
                return "redirect:/users";
            }
            else
                return "redirect:/";
        }
        else
            return "redirect:/";

    }

    /**
     * If the user is logged in and is a manager, then get all users and return the users page
     *
     * @param model The model is a Map that is used to store the data that will be displayed on the view page.
     * @return A list of all users
     */
    @GetMapping("/users")
    public String allUsers(Model model){
        if(isLogin && loginedUser.getIsManager()){
            List<User> result = (List<User>) userService.getAllUsers();
            model.addAttribute("usersList",result);
            return "users";
        }
        return "redirect:/";
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
        if(isLogin && loginedUser.getIsManager()){
            User result = (User) userService.getSingleUser(Long.parseLong(id));
            model.addAttribute("user",result);
            return "user";
        }
        return "redirect:/";
    }

    /**
     * If the user is logged in and is a manager, then return the addUser page
     *
     * @param model The model is a Map that is used to store the data that will be displayed on the view page.
     * @return A string that is the name of the html file that is being returned.
     */
    @GetMapping("/addUser")
    public String addUser(Model model){
        if(isLogin && loginedUser.getIsManager()){
            return "addUser";
        }
        return "redirect:/";
    }

    @GetMapping("/register/{id}/{password}/{name}/{surname}/{isManager}")
    public String register(@PathVariable String id,@PathVariable String password,@PathVariable String name,@PathVariable String surname,@PathVariable boolean isManager,Model model){

        if(isLogin && loginedUser.getIsManager()){
            Long idL = Long.parseLong(id);
            try{
                //If ID length is not 7
                if(id.trim().length() != 7) {
                    model.addAttribute("lengthValidation", false);
                    model.addAttribute("status",false);
                }

                //If a user exist with same id already then don't add and throw error
                else if(!userService.isUserPresent(idL)){
                    userService.addUser(new User(idL,name,surname,isManager,password));
                    model.addAttribute("status",true);
                }

                else
                    throw new IllegalArgumentException();

            }catch (IllegalArgumentException e){
                model.addAttribute("status",false);
            }
            return "addUser";
        }
        return "redirect:/";
    }
}
