package com.fbv.lojaonline.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
  
  
    @RequestMapping(value = {"","/","inicio"})
    public String inicio(){
        return "inicio";
    }

}
