package com.harun.labManagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {
    /**
     * The function takes get requests to index and return index html
     *
     * @param model The model is a Map that is used to store the data that will be displayed on the view page.
     * @return The index.html file
     */
    @GetMapping("/")
    public String index(Model model){
        return "index";
    }
    @GetMapping("/wrongLogin")
    public String indexWrong(Model model){
        model.addAttribute("wrongLogin",true);
        return "index";
    }
}
